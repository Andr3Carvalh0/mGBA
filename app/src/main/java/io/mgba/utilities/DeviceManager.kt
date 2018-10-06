package io.mgba.utilities

import android.content.Context
import android.net.ConnectivityManager
import io.mgba.mgba.Companion.context
import java.util.*

private val SUPPORTED_LANGUAGES = listOf<String>("eng")

fun getDeviceLanguage(): String {
    val iso = Locale.getDefault().isO3Language

    return if (SUPPORTED_LANGUAGES.contains(iso)) iso else SUPPORTED_LANGUAGES[0]
}

fun isConnectedToWeb(): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

