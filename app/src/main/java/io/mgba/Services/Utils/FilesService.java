package io.mgba.Services.Utils;

import com.google.common.base.Predicate;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import io.mgba.Data.DTOs.GameboyAdvanceGame;
import io.mgba.Data.DTOs.GameboyGame;
import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.Services.Interfaces.IFilesService;

/**
 * Handles the fetching/filtering of the supported files for the selected dir.
 */
public class FilesService implements IFilesService{

    public static final LinkedList<String> GBC_FILES_SUPPORTED;
    public static final LinkedList<String> GBA_FILES_SUPPORTED;
    private LinkedList<Game> games = new LinkedList<>();

    static {
        GBC_FILES_SUPPORTED = new LinkedList<>();
        GBA_FILES_SUPPORTED = new LinkedList<>();
        GBA_FILES_SUPPORTED.add("gba");
        GBC_FILES_SUPPORTED.add("gb");
        GBC_FILES_SUPPORTED.add("gbc");
    }

    private File gameDir;

    public FilesService(String directory) {
        if(!directory.equals(""))
            this.gameDir = new File(directory);
    }

    /**
     * Based on the files of the directory, discard the ones we dont need.
     * If the file is a directory or the extension isnt in the FILES_SUPPORTED list, that file
     * isnt valid.
     * @param files the files of the directory
     * @return files that contain gba, gb, gbc extension and arent folders
     */
    private List<File> filter(File[] files){
        List<File> result = new LinkedList<>();

        for (File file : files) {
            if(!file.isDirectory() && (GBA_FILES_SUPPORTED.contains(getFileExtension(file)) || GBC_FILES_SUPPORTED.contains(getFileExtension(file))))
                result.add(file);
        }

        return result;
    }

    /**
     * Return a list thats has been filtered based on the @predicate
     * @param listToFilter the list to filter
     * @param predicate the predicate to evaluate every element of the list
     * @return a list that was filtered
     */
    private List<File> filter(File[] listToFilter, Predicate<File> predicate){
        LinkedList<File> toReturn = new LinkedList<>();

        for (File file : listToFilter) {

            if(predicate.apply(file)){
                toReturn.add(file);
            }
        }

        return toReturn;
    }

    /**
     * Returns a list that has every element sorted with the help of the @param comparator.
     * This method doesnt sort the original list.Instead it returns a new list with every element
     * of the @param listToSort but sorted.
     * @param listToSort the origin list
     * @param comparator the comparator to sort the elements
     * @return a new list with the elements sorted
     */
    private List<? extends Game> sort(LinkedList<? extends Game> listToSort, Comparator<Game> comparator){
        LinkedList<? extends Game> toReturn = listToSort;
        Collections.sort(toReturn, comparator);

        return toReturn;
    }

    private List<File> fetchGames() {
        final File[] files = gameDir.listFiles();

        return filter(files);
    }

    private List<File> fetchGames(Predicate predicate) {
        final File[] files = gameDir.listFiles();

        return filter(files, predicate);
    }

    /**
     * Gets the file extension.
     * For example. For the file 'a.bc' this method will return 'bc'
     * @param file The file to extract a extension
     * @return the file's extension
     */
    @Override
    public String getFileExtension(File file){
        return file.getName()
                .substring(file.getName().lastIndexOf("."))
                .substring(1);//removes "."
    }

    @Override
    public List<File> getGameList(Predicate predicate) {
        if(gameDir == null)
            return new LinkedList<>();

        return fetchGames(predicate);
    }
}
