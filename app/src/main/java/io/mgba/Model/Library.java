package io.mgba.Model;

import com.annimon.stream.Stream;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import dagger.Lazy;
import io.mgba.Constants;
import io.mgba.Data.Database.Game;
import io.mgba.Data.Remote.DTOs.GameJSON;
import io.mgba.Data.Remote.Interfaces.IRequest;
import io.mgba.Model.IO.Decoder;
import io.mgba.Model.IO.FilesManager;
import io.mgba.Model.Interfaces.IDatabase;
import io.mgba.Model.Interfaces.IFilesManager;
import io.mgba.Model.Interfaces.ILibrary;
import io.mgba.Utils.IDependencyInjector;
import io.mgba.Utils.IDeviceManager;
import io.mgba.mgba;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class Library implements ILibrary {
    @Inject IDatabase database;
    @Inject IFilesManager filesService;
    @Inject IDeviceManager deviceManager;
    @Inject Lazy<IRequest> webManager;

    public Library(@NonNull IDependencyInjector dependencyInjector) {
        dependencyInjector.inject(this);
    }

    public Library(@NonNull IDatabase database, @NonNull IFilesManager filesService,
                   @NonNull IDeviceManager deviceManager, IRequest webManager) {
        this.database = database;
        this.filesService = filesService;
        this.deviceManager = deviceManager;
        this.webManager = () -> webManager;
    }

    @Override
    public Single<List<Game>> prepareGames(int platform) {
        Single<List<Game>> ret =  Single.create(subscriber -> {

            if(platform == Constants.PLATFORM_GBC
                    || platform == Constants.PLATFORM_GBA
                    || platform == Constants.PLATFORM_FAVS){

                List<Game> games = database.getGamesForPlatform(platform);

                subscriber.onSuccess(games);

            }else{
                subscriber.onSuccess(new LinkedList<>());
            }
        });

        return ret.doOnError(mgba::report);
    }

    @Override
    public Single<List<Game>> query(String query) {
        Single<List<Game>> ret = Single.create(subscriber -> {

            if(query == null || query.length() == 0){
                subscriber.onSuccess(new LinkedList<>());
                return;
            }

            List<Game> games = database.queryForGames(query);

            subscriber.onSuccess(games);
        });

        return ret.doOnError(mgba::report);
    }

    @Override
    public Single<List<Game>> reloadGames(int... platform) {
        Single<List<Game>> ret = Single.create(subscriber -> {
            List<Game> games = database.getGames();
            removeGamesFromDatabase(games);

            final List<Game> updatedList = processNewGames(games);

            games.addAll(updatedList);

            Collections.sort(games, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            subscriber.onSuccess(filter(platform, games));
        });

        return ret.doOnError(mgba::report);

    }

    @Override
    public Single<List<Game>> reloadGames(String path, int... platform) {
        filesService.setCurrentDirectory(path);
        return reloadGames(platform);
    }

    private void removeGamesFromDatabase(List<Game> games){
        Stream.of(games)
                .filter(g -> !g.getFile().exists())
                .forEach(g -> {
                    games.remove(g);
                    database.delete(g);}
                );
    }

    private List<Game> processNewGames(List<Game> games){
        return Stream.of(filesService.getGameList())
                .map(file -> new Game(file.getAbsolutePath(), getPlatform(file)))
                .filter(file -> games.size() == 0 || Stream.of(games).anyMatch(game -> !game.getFile().equals(file.getFile())))
                .map(game -> {
                    Stream.of(games).filter(otherGame -> otherGame.equals(game)).forEach(games::remove);

                    if (calculateMD5(game)) {
                        if(deviceManager.isConnectedToWeb())
                            searchWeb(game);
                        storeInDatabase(game);
                    }

                    return game;
                }).toList();
    }

    private List<Game> filter(int[] platform, List<Game> games){
        return Stream.of(games)
                     .filter(g -> {
                         for (int i = 0; i < platform.length; i++) {
                             if(platform[i] == g.getPlatform())
                                 return true;
                         }

                        return false;
                     })
                     .toList();
    }

    private int getPlatform(File file) {
        final String fileExtension = FilesManager.getFileExtension(file);

        if(Constants.PLATFORM_GBA_EXT.contains(fileExtension))
            return Constants.PLATFORM_GBA;

        return Constants.PLATFORM_GBC;
    }

    private void storeInDatabase(Game game){
        database.insert(game);
    }

    private void searchWeb(Game game){
        try {
            final GameJSON json = webManager.get()
                                      .getGameInformation(game.getMD5(), deviceManager.getDeviceLanguage())
                                      .execute()
                                      .body();

            if(json != null)
                copyInformation(game, json);
        } catch (Exception e) {
            mgba.report(e);
        }
    }

    private boolean calculateMD5(Game game) {
        String md5 = Decoder.getFileMD5ToString(game.getFile());
        game.setMD5(md5);

        return md5 != null;
    }

    private void copyInformation(Game game, GameJSON json){
        game.setName(json.getName());
        game.setDescription(json.getDescription());
        game.setDeveloper(json.getDeveloper());
        game.setGenre(json.getGenre());
        game.setReleased(json.getReleased());
        game.setCoverURL(json.getCover());
    }
}
