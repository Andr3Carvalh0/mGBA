package io.mgba.UI.Views;

import android.content.Context;
import android.util.AttributeSet;
import com.flipboard.bottomsheet.BottomSheetLayout;
import io.mgba.Utils.DisplayUtils;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class CustomBottomSheetLayout extends BottomSheetLayout {
    private final static int SCREEN_PERCENTAGE_PORT = 75;
    private final static int SCREEN_PERCENTAGE_LAND = 50;


    public CustomBottomSheetLayout(Context context) {
        super(context);
    }

    public CustomBottomSheetLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomBottomSheetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomBottomSheetLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    /**
     *
     * Sets the size for our sheet...
     * The current implementation is:
     * In portrait it will occupy 75% of the screen
     * In landscape it will occupy 50% of the screen
     */
    public float getPeekSheetTranslation() {
        return getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE
                    ?   (DisplayUtils.getDisplayWidth(getContext()) * SCREEN_PERCENTAGE_LAND) / 100
                    :   (DisplayUtils.getDisplayHeight(getContext()) * SCREEN_PERCENTAGE_PORT) / 100;
    }

}
