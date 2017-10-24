package io.mgba.Components.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import io.mgba.Controller.ProcessingController;
import io.mgba.mgba;

public class ProcessingService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ProcessingController controller = new ProcessingController(
                (mgba) getApplication(),
                intent.getParcelableArrayListExtra("games"),
                this::stopSelf);

        controller.start();

        return START_NOT_STICKY;
    }

}
