package io.mgba.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtils {

    public static float getDisplayHeight(Context context){
        return prepareDisplayMetrics(context).heightPixels;
    }

    public static float getDisplayWidth(Context context){
        return prepareDisplayMetrics(context).widthPixels;
    }

    private static DisplayMetrics prepareDisplayMetrics(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics;
    }
}
