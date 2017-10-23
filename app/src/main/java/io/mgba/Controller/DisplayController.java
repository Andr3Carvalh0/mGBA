package io.mgba.Controller;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import io.mgba.R;

/**
 * Utility class to calculate how many/the size of games we will show for each row of the recyclerview.
 */
public class DisplayController {

    public static final double LOWER_THRESHOLD = 0.1;
    public static final double UPPER_THRESHOLD = 0.9;

    private final float GAME_SIZE;
    private final Context mContext;

    public DisplayController(Context mContext) {
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
        int widthDP = Math.round(convertPixelsToDp(width, mContext));
        int final_game_size = (int)GAME_SIZE;

        float elemsPerRow = widthDP / final_game_size;
        int final_elemsPerRow = (int)elemsPerRow;
        int decimal = (int) elemsPerRow;
        float fraction = elemsPerRow - decimal;

        boolean sub = fraction > UPPER_THRESHOLD && fraction >= LOWER_THRESHOLD;

        while(fraction > UPPER_THRESHOLD || fraction > LOWER_THRESHOLD){

            if(sub)
                final_game_size --;
            else
                final_game_size++;

            elemsPerRow = widthDP / final_game_size;
            decimal = (int) elemsPerRow;
            fraction = elemsPerRow - decimal;
        }

        final_game_size = final_game_size - 4;

        int[] toReturn = new int[3];
        toReturn[0] = final_elemsPerRow;
        toReturn[1] = Math.round(convertDpToPixel(final_game_size, mContext));
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
