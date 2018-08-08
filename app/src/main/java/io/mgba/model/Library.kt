package io.mgba.model

import com.annimon.stream.Stream
import java.io.File
import java.util.LinkedList
import javax.inject.Inject
import dagger.Lazy
import io.mgba.Constants
import io.mgba.data.database.Game
import io.mgba.data.remote.dtos.GameJSON
import io.mgba.data.remote.interfaces.IRequest
import io.mgba.model.io.Decoder
import io.mgba.model.io.FilesManager
import io.mgba.model.interfaces.IDatabase
import io.mgba.model.interfaces.IFilesManager
import io.mgba.model.interfaces.ILibrary
import io.mgba.utilities.IDependencyInjector
import io.mgba.utilities.IDeviceManager
import io.mgba.mgba
import io.reactivex.Single
import io.reactivex.annotations.NonNull

class Library : ILibrary {
    @Inject internal lateinit var database: IDatabase
    @Inject internal lateinit var filesService: IFilesManager
    @Inject internal lateinit var deviceManager: IDeviceManager
    @Inject internal lateinit var webManager: Lazy<IRequest>

    constructor(@NonNull dependencyInjector: IDependencyInjector) {
        dependencyInjector.inject(this)
    }

    constructor(@NonNull database: IDatabase, @NonNull filesService: IFilesManager,
                @NonNull deviceManager: IDeviceManager, webManager: IRequest) {
        this.database = database
        this.filesService = filesService
        this.deviceManager = deviceManager
        this.webManager = Lazy{ webManager }
    }

    override fun prepareGames(platform: Int): Single<List<Game>> {
        val ret = Single.create<List<Game>> { subscriber ->

            if (platform == Constants.PLATFORM_GBC
                    || platform == Constants.PLATFORM_GBA
                    || platform == Constants.PLATFORM_FAVS) {

                val games = database.getGamesForPlatform(platform)

                subscriber.onSuccess(games)

            } else {
                subscriber.onSuccess(LinkedList())
            }
        }

        return ret.doOnError { mgba.report(it) }
    }

    override fun query(query: String): Single<List<Game>> {
        val ret = Single.create<List<Game>> Single@{ subscriber ->

            if (query.isEmpty()) {
                subscriber.onSuccess(LinkedList())
                return@Single
            }

            val games = database.queryForGames(query)

            subscriber.onSuccess(games)
        }

        return ret.doOnError { mgba.report(it) }
    }

    override fun reloadGames(vararg platform: Int): Single<List<Game>> {
        val ret = Single.create<List<Game>> { subscriber ->
            val games = database.games.toMutableList()
            removeGamesFromDatabase(games)

            val updatedList = processNewGames(games)

            games.addAll(updatedList)
            games.sortWith(Comparator { o1, o2 -> o1.getName()!!.compareTo(o2.getName()!!) })
            subscriber.onSuccess(filter(platform, games))
        }

        return ret.doOnError { mgba.report(it) }

    }

    override fun reloadGames(path: String, vararg platform: Int): Single<List<Game>> {
        filesService.currentDirectory = path
        return reloadGames(*platform)
    }

    private fun removeGamesFromDatabase(games: MutableList<Game>) {
        Stream.of(games)
                .filter { g -> !g.file!!.exists() }
                .forEach { g ->
                    games.remove(g)
                    database.delete(g)
                }
    }

    private fun processNewGames(games: MutableList<Game>): List<Game> {
        return Stream.of(filesService.gameList)
                .map { file -> Game(file.absolutePath, getPlatform(file)) }
                .filter { file -> games.isEmpty() || Stream.of(games).anyMatch { game -> game.file != file.file } }
                .map { game ->
                    Stream.of(games).filter { otherGame -> otherGame == game }
                                    .forEach { games.remove(it) }

                    if (calculateMD5(game)) {
                        if (deviceManager.isConnectedToWeb)
                            searchWeb(game)
                        storeInDatabase(game)
                    }

                    game
                }.toList()
    }

    private fun filter(platform: IntArray, games: List<Game>): List<Game> {
        return Stream.of(games)
                .filter Stream@{ g ->
                    for (i in platform.indices) {
                        if (platform[i] == g.platform)
                            return@Stream true
                    }
                    false
                }
                .toList()
    }

    private fun getPlatform(file: File): Int {
        val fileExtension = FilesManager.getFileExtension(file)

        return if (Constants.PLATFORM_GBA_EXT.contains(fileExtension)) Constants.PLATFORM_GBA else Constants.PLATFORM_GBC

    }

    private fun storeInDatabase(game: Game) {
        database.insert(game)
    }

    private fun searchWeb(game: Game) {
        try {
            val json = webManager.get()
                    .getGameInformation(game.mD5!!, deviceManager.deviceLanguage)
                    .execute()
                    .body()

            if (json != null)
                copyInformation(game, json)
        } catch (e: Exception) {
            mgba.report(e)
        }

    }

    private fun calculateMD5(game: Game): Boolean {
        val md5 = Decoder.getFileMD5ToString(game.file!!)
        game.mD5 = md5

        return md5 != null
    }

    private fun copyInformation(game: Game, json: GameJSON) {
        game.setName(json.name)
        game.description = json.description
        game.developer = json.developer
        game.genre = json.genre
        game.released = json.released
        game.coverURL = json.cover
    }
}
