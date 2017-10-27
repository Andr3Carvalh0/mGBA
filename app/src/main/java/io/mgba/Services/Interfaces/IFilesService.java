package io.mgba.Services.Interfaces;


import com.google.common.base.Predicate;

import java.io.File;
import java.util.List;

public interface IFilesService {
    List<File> getGameList(Predicate predicate);
    List<File> getGameList();
    String getCurrentDirectory();
    void setCurrentDirectory(String directory);

}
