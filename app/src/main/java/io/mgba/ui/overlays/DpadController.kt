package io.mgba.ui.overlays

import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton

class DpadController(context: Context, attrs: AttributeSet) : AppCompatImageButton(context, attrs) {
    //left, up, right, down -> just like Rect
    @Volatile private var state = -1

    private var backgroundService = Handler()

    private val handlerRunnable = object : Runnable {
        override fun run() {
            when (state) {
                0 -> Log.i("Andre", "LEFT")
                1 -> Log.i("Andre", "UP")
                2 -> Log.i("Andre", "RIGHT")
                3 -> Log.i("Andre", "DOWN")
            }

            handler.postDelayed(this, INTERVAL.toLong())
        }
    }

    private val bounds: Rect
        get() = Rect(0, 0, width, height)

    init {
        isFocusable = true
        isClickable = true

    }

    fun onTouch(v: View, event: MotionEvent): Boolean {
        val pointerIndex = event.actionIndex

        val rect = bounds

        val x = event.getX(pointerIndex)
        val y = event.getY(pointerIndex)

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                if (x > rect.left && x < rect.right && y > rect.top && y < rect.bottom) {
                    if (rect.top + height / 3 > event.getY(pointerIndex).toInt())
                        setState(1)

                    if (rect.bottom - height / 3 < event.getY(pointerIndex).toInt())
                        setState(3)

                    if (rect.left + width / 3 > event.getX(pointerIndex).toInt())
                        setState(0)

                    if (rect.right - width / 3 < event.getX(pointerIndex).toInt())
                        setState(2)
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                stop()
                return true
            }
        }

        return false
    }

    private fun setState(pos: Int) {
        if (state != pos) {
            state = pos
            handler.post(handlerRunnable)
        }
    }

    private fun stop() {
        handler.removeCallbacks(handlerRunnable)
        state = -1
    }

    companion object {

        private val INTERVAL = 500
    }
}
