package io.mgba.utilities.device

import androidx.core.content.ContextCompat
import io.mgba.mgba.Companion.context

object ResourcesManager {
    fun getColor(id: Int): Int = ContextCompat.getColor(context, id)
    fun getString(id: Int): String = context.getString(id)
}