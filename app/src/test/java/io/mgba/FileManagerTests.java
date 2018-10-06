package io.mgba;

import org.junit.Test;

import java.io.File;
import io.mgba.data.io.FilesManager;

import static org.junit.Assert.assertEquals;

public class FileManagerTests {

    @Test
    public void getExtensionOnFileWithExtension(){
        assertEquals("test", FilesManager.getFileExtension(new File("/something.test")));
    }


    @Test
    public void getExtensionOnFileWithoutExtension(){
        assertEquals("est", FilesManager.getFileExtension(new File("/somethingtest")));
    }

    @Test
    public void getFilenameWithoutExtension(){
        assertEquals("something", FilesManager.getFileWithoutExtension(new File("/something.test")));
    }


}
