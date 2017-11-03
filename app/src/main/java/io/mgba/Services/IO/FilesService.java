package io.mgba.Services.IO;

import android.content.Context;

import com.google.common.base.Predicate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import io.mgba.Data.DTOs.Game;
import io.mgba.Services.Interfaces.IFilesService;

import static io.mgba.mgba.printLog;

/**
 * Handles the fetching/filtering of the supported files for the selected dir.
 */
public class FilesService implements IFilesService{

    public static final LinkedList<String> GBC_FILES_SUPPORTED;
    public static final LinkedList<String> GBA_FILES_SUPPORTED;
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String TAG = "FileService";

    static {
        GBC_FILES_SUPPORTED = new LinkedList<>();
        GBA_FILES_SUPPORTED = new LinkedList<>();
        GBA_FILES_SUPPORTED.add("gba");
        GBC_FILES_SUPPORTED.add("gb");
        GBC_FILES_SUPPORTED.add("gbc");
    }

    private File gameDir;

    public FilesService(String directory) {
        printLog(TAG, "CTOR: " + directory);

        if(!directory.equals(""))
            this.gameDir = new File(directory);

    }

    private static byte[] getFileMD5(final File file, Context ctx) {
        if (file == null) return null;
        DigestInputStream dis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[1024 * 256];
            while (true) {
                if (!(dis.read(buffer) > 0)) break;
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {

            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e1) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String getFileMD5ToString(final File file, Context ctx) {
        byte[] bytes = getFileMD5(file, ctx);

        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }

        return new String(ret);
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
                .toLowerCase();//removes "."
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
