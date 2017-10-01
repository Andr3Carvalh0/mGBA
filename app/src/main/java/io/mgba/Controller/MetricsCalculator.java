package io.mgba.Controller;


import android.content.Context;

import io.mgba.R;

/**
* Utility class to calculate how many games we will show for each row.
* */
public class MetricsCalculator {

    private final long GAME_MIN_SIZE;
    private final int IDEAL_PER_ROW = 3;

    public MetricsCalculator(Context mContext) {
        GAME_MIN_SIZE = (long) mContext.getResources().getDimension(R.dimen.min_game_cover_size);
    }

    public int calculate(long width, long device_density){

        //Cannot put 3 elems per row
        if(width / IDEAL_PER_ROW < GAME_MIN_SIZE)
            return (int) ((int) (width / 2) * device_density);

        return (int) ((int) (width / 3) * device_density);
    }

}
