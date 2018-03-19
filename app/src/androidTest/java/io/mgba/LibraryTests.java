package io.mgba;

import android.app.Application;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import io.mgba.Model.Interfaces.ILibrary;
import io.mgba.Model.Library;
import io.mgba.Utils.TestsDB;
import io.mgba.Utils.TestsFLM;

@RunWith(AndroidJUnit4.class)
public class LibraryTests {

    private ILibrary library;

    @Before
    public void init() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        library = new Library(((mgba)ctx.getApplicationContext()), new TestsDB(), new TestsFLM());
    }

}
