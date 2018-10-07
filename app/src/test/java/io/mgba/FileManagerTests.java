package io.mgba;

import org.junit.Test;

import java.io.File;
import io.mgba.data.FileManager;

import static org.junit.Assert.assertEquals;

public class FileManagerTests {

    @Test
    public void getExtensionOnFileWithExtension(){
        assertEquals("test", FileManager.getFileExtension(new File("/something.test")));
    }


    @Test
    public void getExtensionOnFileWithoutExtension(){
        assertEquals("est", FileManager.getFileExtension(new File("/somethingtest")));
    }

    @Test
    public void getFilenameWithoutExtension(){
        assertEquals("something", FileManager.getFileWithoutExtension(new File("/something.test")));
    }


}
