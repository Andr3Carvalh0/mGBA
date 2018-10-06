package io.mgba.widgets

import android.view.ViewGroup
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar
import io.mgba.R
import io.mgba.utilities.dp

object MaterialSnackbar{

    fun notify(view: View): Builder {
        return Builder(view)
    }

    class Builder internal constructor(private val view: View) {
        private var title = "Ola"
        private var duration = Snackbar.LENGTH_SHORT
        private var margins = 16
        private val elevation = 128f.dp
        private var onClick: (View) -> Unit = {}
        private var onClickText: String? = null

        fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun duration(value: Int): Builder {
            this.duration = value
            return this
        }

        fun margin(value: Int): Builder {
            this.margins = value
            return this
        }

        fun onClick(text: String, onClick: (View) -> Unit): Builder {
            this.onClick = onClick
            this.onClickText = text
            return this
        }

        fun build(): Snackbar {
            val snackbar = Snackbar.make(view, title, duration)
            snackbar.view.background = view.context.getDrawable(R.drawable.snackbar)
            snackbar.view.layoutParams = snackbar.view.layoutParams.apply {
                (this as ViewGroup.MarginLayoutParams).setMargins(margins, margins, margins, margins)
            }

            ViewCompat.setElevation(snackbar.view, elevation)

            if(onClickText != null) {
                snackbar.setAction(onClickText, onClick)
            }

            snackbar.setActionTextColor(view.context.resources.getColor(R.color.actionColor))

            return snackbar
        }
    }
}