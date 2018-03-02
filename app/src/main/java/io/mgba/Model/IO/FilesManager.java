package io.mgba.Model.IO;

import com.annimon.stream.Stream;
import com.google.common.base.Predicate;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import io.mgba.Data.Database.Game;
import io.mgba.Model.Interfaces.IFilesManager;

import static io.mgba.mgba.printLog;

/**
 * Handles the fetching/filtering of the supported files for the selected dir.
 */
public class FilesManager implements IFilesManager {

    private static final LinkedList<String> GBC_FILES_SUPPORTED;
    private static final LinkedList<String> GBA_FILES_SUPPORTED;
    private static final String TAG = "FileService";

    static {
        GBC_FILES_SUPPORTED = new LinkedList<>();
        GBA_FILES_SUPPORTED = new LinkedList<>();
        GBA_FILES_SUPPORTED.add("gba");
        GBC_FILES_SUPPORTED.add("gb");
        GBC_FILES_SUPPORTED.add("gbc");
    }

    private File gameDir;

    public FilesManager(String directory) {
        printLog(TAG, "CTOR: " + directory);

        if(!directory.equals(""))
            this.gameDir = new File(directory);
    }

    /**
     * Gets the file extension.
     * For example. For the file 'a.bc' this method will return 'bc'
     * @param file The file to extract a extension
     * @return the file's extension
     */
    public static String getFileExtension(File file){
        return file.getName()
                .substring(file.getName().lastIndexOf("."))
                .substring(1)
                .toLowerCase();
    }

    /**
     * Gets the filename without the extension
     * @param file
     * @return
     */
    public static String getFileWithoutExtension(File file){
        final String[] tmp = file.getPath().split("/");

        return tmp[tmp.length - 1].substring(0, tmp[tmp.length - 1].lastIndexOf("."));
    }

    /**
     * Based on the files of the directory, discard the ones we dont need.
     * If the file is a directory or the extension isnt in the FILES_SUPPORTED list, that file
     * isnt valid.
     * @param files the files of the directory
     * @return files that contain gba, gb, gbc extension and arent folders
     */
    private List<File> filter(File[] files){
        return filter(files, f -> !f.isDirectory() &&
                        (GBA_FILES_SUPPORTED.contains(getFileExtension(f)) ||
                        GBC_FILES_SUPPORTED.contains(getFileExtension(f))));
    }

    /**
     * Return a list thats has been filtered based on the @predicate
     * @param list the list to filter
     * @param predicate the predicate to evaluate every element of the list
     * @return a list that was filtered
     */
    private List<File> filter(File[] list, Predicate<File> predicate){
        return Stream.of(list)
                .filter(predicate::apply)
                .toList();
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

    @Override
    public List<File> getGameList(Predicate predicate) {
        if(gameDir == null)
            return new LinkedList<>();

        return fetchGames(predicate);
    }

    @Override
    public List<File> getGameList() {
        if(gameDir == null)
            return new LinkedList<>();

        return fetchGames();
    }

    @Override
    public String getCurrentDirectory() {
        if(gameDir == null)
            return null;
        return gameDir.getAbsolutePath();
    }

    @Override
    public void setCurrentDirectory(String directory) {
        if(!directory.equals(""))
            this.gameDir = new File(directory);
    }
}
