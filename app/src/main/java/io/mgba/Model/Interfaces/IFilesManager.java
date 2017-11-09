package io.mgba.Model.Interfaces;


import com.google.common.base.Predicate;

import java.io.File;
import java.util.List;

public interface IFilesManager {
    List<File> getGameList(Predicate predicate);
    List<File> getGameList();
    String getCurrentDirectory();
    void setCurrentDirectory(String directory);

}
