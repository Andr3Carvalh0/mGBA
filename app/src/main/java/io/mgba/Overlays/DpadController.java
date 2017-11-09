package io.mgba.Overlays;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DpadController extends AppCompatImageButton{

    private final static int INTERVAL = 500;
    //left, up, right, down -> just like Rect
    private volatile int state = -1;
    private Handler handler = new Handler();

    private Runnable handlerRunnable = new Runnable() {
        @Override
        public void run() {
            switch (state){
                case 0:
                    Log.i("Andre", "LEFT");
                    break;
                case 1:
                    Log.i("Andre", "UP");
                    break;
                case 2:
                    Log.i("Andre", "RIGHT");
                    break;
                case 3:
                    Log.i("Andre", "DOWN");

                    break;
            }


            handler.postDelayed(this, INTERVAL);
        }
    };

    public DpadController(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setClickable(true);

    }

    public boolean onTouch(View v, MotionEvent event) {
        int pointerIndex = event.getActionIndex();

        final Rect rect = getBounds();

        final float x = event.getX(pointerIndex);
        final float y = event.getY(pointerIndex);

        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (x > rect.left && x < rect.right && y > rect.top && y < rect.bottom) {
                    if (rect.top + (getHeight() / 3) > (int)event.getY(pointerIndex))
                        setState(1);

                    if (rect.bottom - (getHeight() / 3) < (int)event.getY(pointerIndex))
                        setState(3);

                    if (rect.left + (getWidth() / 3) > (int)event.getX(pointerIndex))
                        setState(0);

                    if (rect.right - (getWidth() / 3) < (int)event.getX(pointerIndex))
                        setState(2);
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                stop();
                return true;
        }

        return false;
    }

    private Rect getBounds(){
        return new Rect(0,0, getWidth(), getHeight());
    }

    private void setState(int pos){
        if(state != pos) {
            state = pos;
            handler.post(handlerRunnable);
        }
    }

    private void stop(){
        handler.removeCallbacks(handlerRunnable);
        state = -1;
    }
}
