package io.mgba.utilities

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.annimon.stream.Stream
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.floatingactionbutton.FloatingActionButton

import java.util.LinkedList

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import androidx.palette.graphics.Palette
import com.github.florent37.glidepalette.BitmapPalette

class GlideUtils<T> private constructor(private val holder: T, private val url: String) {
    private var placeholder = -1
    private var onError = -1
    private val views = LinkedList<ColorView>()

    fun setPlaceholders(placeholder: Int, onError: Int): GlideUtils<*> {
        this.placeholder = placeholder
        this.onError = onError

        return this
    }

    fun setPlaceholder(placeholder: Int): GlideUtils<*> {
        this.placeholder = placeholder

        return this
    }

    fun setOnError(onError: Int): GlideUtils<*> {
        this.onError = onError

        return this
    }

    fun colorView(primarySwatch: Colors, secundarySwatch: Colors, vararg view: View): GlideUtils<*> {
        Stream.of(*view)
                .map { v -> ColorWrapper(v, primarySwatch, secundarySwatch) }
                .forEach { c -> views.add(c) }

        return this
    }

    fun colorViewWithCustomBackground(primarySwatch: Colors, secundarySwatch: Colors, vararg view: View): GlideUtils<*> {
        Stream.of(*view)
                .map { v -> CustomBackgroundColorWrapper(v, primarySwatch, secundarySwatch) }
                .forEach { c -> views.add(c) }

        return this
    }

    fun colorView(swatch: Colors, title: Boolean, vararg view: TextView): GlideUtils<*> {
        Stream.of(*view)
                .map { v -> TextColorWrapper(swatch, v, title) }
                .forEach { c -> views.add(c) }

        return this
    }

    fun build(imageView: ImageView) {
        val request = prepare()

        processPlaceholders(request)
        processColorExtraction(request)

        request.into(imageView)
    }

    private fun prepare(): RequestBuilder<Drawable> {
        if (holder is Fragment)
            return Glide.with(holder as Fragment).load(url)

        return if (holder is AppCompatActivity) Glide.with(holder as AppCompatActivity).load(url)
               else Glide.with(holder as View).load(url)

    }

    private fun processPlaceholders(rm: RequestBuilder<Drawable>) {
        val requestOptions = RequestOptions()

        if (placeholder != -1)
            requestOptions.placeholder(placeholder)

        if (onError != -1)
            requestOptions.error(onError)

        rm.apply(requestOptions)
    }

    private fun processColorExtraction(rm: RequestBuilder<Drawable>) {
        val requestColor = GlidePalette.with(url)

        requestColor.intoCallBack { palette ->
                        Stream.of(views)
                                .forEach { v -> v.colorView(palette, requestColor) }
                }

        rm.listener(requestColor)
    }

    private fun getPalette(v: Colors, palette: Palette?): Palette.Swatch {
        when (v) {
            GlideUtils.Colors.MUTED -> return palette!!.mutedSwatch!!
            GlideUtils.Colors.VIBRANT -> return palette!!.vibrantSwatch!!
            GlideUtils.Colors.LIGHT_MUTED -> return palette!!.lightMutedSwatch!!
            GlideUtils.Colors.LIGHT_VIBRANT -> return palette!!.lightVibrantSwatch!!
            GlideUtils.Colors.DARK_MUTED -> return palette!!.darkMutedSwatch!!
            else -> return palette!!.darkVibrantSwatch!!
        }
    }

    enum class Colors private constructor(val value: Int) {
        VIBRANT(0),
        DARK_VIBRANT(1),
        LIGHT_VIBRANT(2),
        MUTED(3),
        DARK_MUTED(4),
        LIGHT_MUTED(5)
    }

    private abstract inner class ColorView internal constructor(val view: View) {

        internal abstract fun colorView(palette: Palette?, requestColor: GlidePalette<Drawable>)
    }

    private inner class TextColorWrapper internal constructor(private val profile: Colors, view: View, private val title: Boolean) : ColorView(view) {

        override fun colorView(palette: Palette?, requestColor: GlidePalette<Drawable>) {
            requestColor.use(profile.value)
                    .intoTextColor(view as TextView, if (title)
                        BitmapPalette.Swatch.TITLE_TEXT_COLOR
                    else
                        BitmapPalette.Swatch.BODY_TEXT_COLOR)
        }
    }

    private inner class CustomBackgroundColorWrapper internal constructor(view: View, internal val primarySwatch: Colors, internal val secondarySwatch: Colors) : ColorView(view) {

        override fun colorView(palette: Palette?, requestColor: GlidePalette<Drawable>) {
            var swatch: Palette.Swatch? = getPalette(primarySwatch, palette)

            if (swatch == null)
                swatch = getPalette(secondarySwatch, palette)

            val background = view.background

            if (background is ShapeDrawable) {
                background.paint.color = swatch.rgb

            } else if (background is GradientDrawable) {
                background.setColor(swatch.rgb)

            } else if (background is ColorDrawable) {
                background.color = swatch.rgb
            }
        }
    }

    private inner class ColorWrapper internal constructor(view: View, internal val primarySwatch: Colors, internal val secondarySwatch: Colors) : ColorView(view) {

        override fun colorView(palette: Palette?, requestColor: GlidePalette<Drawable>) {
            var swatch: Palette.Swatch? = getPalette(primarySwatch, palette)

            if (swatch == null)
                swatch = getPalette(secondarySwatch, palette)

            if (view is TextView) {
                view.setTextColor(ColorStateList.valueOf(swatch.rgb))
                return
            }

            if (view is FloatingActionButton) {
                view.setBackgroundTintList(ColorStateList.valueOf(swatch.rgb))
                return
            }

            if (view is ImageView) {
                view.setColorFilter(swatch.rgb, android.graphics.PorterDuff.Mode.SRC_IN)
                return
            }

            view.setBackgroundColor(swatch.rgb)
        }
    }

    companion object {

        fun init(holder: AppCompatActivity, url: String): GlideUtils<*> {
            return GlideUtils(holder, url)
        }

        fun init(holder: View, url: String): GlideUtils<*> {
            return GlideUtils(holder, url)
        }

        fun init(holder: Fragment, url: String): GlideUtils<*> {
            return GlideUtils(holder, url)
        }
    }
}
