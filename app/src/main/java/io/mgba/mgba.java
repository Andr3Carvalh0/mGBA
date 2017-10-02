package io.mgba;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

import permissions.dispatcher.RuntimePermissions;

public class mgba extends Application {
    private DisplayMetrics metrics;

    public long getDeviceWidth(Activity activity){
        activity.getWindowManager().getDefaultDisplay().getMetrics(getDisplayMetrics());
        return metrics.widthPixels;
    }

    private DisplayMetrics getDisplayMetrics(){
        if(metrics == null)
            metrics = new DisplayMetrics();

        return metrics;
    }
}
