package io.mgba.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GBService extends Service {
    public GBService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
