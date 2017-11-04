package io.mgba.Controllers.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.mgba.Constants;
import io.mgba.Services.Interfaces.IProcessingService;
import io.mgba.mgba;

public class ProcessingService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IProcessingService controller = new io.mgba.Services.ProcessingService(
                (mgba) getApplication(),
                intent.getParcelableArrayListExtra(Constants.GAMES_INTENT),
                this::stopSelf);

        controller.start();

        return START_STICKY;
    }
}
