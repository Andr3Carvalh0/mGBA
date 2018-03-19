package io.mgba.Utils;

import com.google.common.base.Predicate;

import org.junit.BeforeClass;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import io.mgba.Model.Interfaces.IFilesManager;

public class TestsFLM implements IFilesManager {
    private static List<File> files;

    private String dir = "";

    @BeforeClass
    public static void init() {
        files = new LinkedList<>();
        files.add(new File("/a.b"));
        files.add(new File("/c.d"));
        files.add(new File("/e.f"));
        files.add(new File("/g.h"));
    }

    @Override
    public List<File> getGameList(Predicate predicate) {
        return files;
    }

    @Override
    public List<File> getGameList() {
        return files;
    }

    @Override
    public String getCurrentDirectory() {
        return dir;
    }

    @Override
    public void setCurrentDirectory(String directory) {
        dir = directory;
    }
}
