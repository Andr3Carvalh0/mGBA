package io.mgba.Controller;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import io.mgba.R;

/**
* Utility class to calculate how many games we will show for each row.
* */
public class MetricsCalculator {

    public static final double THRESHOLD = 0.3;
    public static final double UPPER_THRESHOLD = 0.85;
    private final float GAME_SIZE;
    private final Context mContext;

    public MetricsCalculator(Context mContext) {
        this.mContext = mContext;
        float game_px = mContext.getResources().getDimension(R.dimen.cover_size);

        GAME_SIZE = convertPixelsToDp(game_px, mContext);
    }

    /**
     * Returns an array with the number of elements per row and the size of an element.
     * Right now we accept tha the max elements per row is 3
     * @param width
     * @return
     */
    public int[] calculate(long width) {
        float widthDP = Math.round(convertPixelsToDp(width, mContext));
        int final_game_size = (int)GAME_SIZE;

        float elemsPerRow = widthDP / final_game_size;
        int decimal = (int) elemsPerRow;
        float fraction = elemsPerRow - decimal;

        //if the elemsPerRow return a number like 2,86
        // where y.xx xx> .85 in that case we try to add y+1 elements
        while (fraction > UPPER_THRESHOLD){
            final_game_size++;
            elemsPerRow = widthDP / final_game_size;
            decimal = (int) elemsPerRow;
            fraction = elemsPerRow - decimal;
        }

        //Recalculate everything again, now with the separators in mind
        float separators =  convertPixelsToDp(mContext.getResources().getDimension(R.dimen.game_space_borders), mContext)
                            * (decimal - 1);

        widthDP -= separators;
        elemsPerRow = widthDP / GAME_SIZE;
        decimal = (int) elemsPerRow;
        fraction = elemsPerRow - decimal;

        while(fraction > THRESHOLD){
            final_game_size--;
            elemsPerRow = widthDP / final_game_size;
            decimal = (int) elemsPerRow;
            fraction = elemsPerRow - decimal;
        }

        int[] toReturn = new int[3];
        toReturn[0] = decimal;
        toReturn[1] = (int)convertDpToPixel(final_game_size, mContext);
        //element size in dp
        toReturn[2] = final_game_size;

        return toReturn;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
