package io.mgba.utilities.device

import android.content.Context
import android.content.res.Resources

/**
 * Created by André Carvalho on 24/08/2018
 */
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * converts a pixel value to dp;
 */
val Float.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

/**
 * converts a dp value to px;
 */
val Float.px: Float
    get() = (this * Resources.getSystem().displayMetrics.density)


fun getDisplayHeight(context: Context): Float {
    return Resources.getSystem().displayMetrics.heightPixels.toFloat()
}

fun getDisplayWidth(context: Context): Float {
    return Resources.getSystem().displayMetrics.widthPixels.toFloat()
}
