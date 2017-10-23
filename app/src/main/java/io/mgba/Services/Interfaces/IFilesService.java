package io.mgba.Services.Interfaces;


import com.google.common.base.Predicate;

import java.io.File;
import java.util.List;

public interface IFilesService {
    String getFileExtension(File file);
    List<File> getGameList(Predicate predicate);
    List<File> getGameList();
}
