package io.mgba.ui.activities.interfaces

interface ISettings {
    fun requestStoragePermission()
    fun requestPreferencesValue(key: String, defaultValue: String): String
}
