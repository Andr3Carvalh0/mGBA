package io.mgba.utilities

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

object DisplayUtils {

    fun getDisplayHeight(context: Context): Float {
        return prepareDisplayMetrics(context).heightPixels.toFloat()
    }

    fun getDisplayWidth(context: Context): Float {
        return prepareDisplayMetrics(context).widthPixels.toFloat()
    }

    private fun prepareDisplayMetrics(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

        return displayMetrics
    }
}
