package io.mgba.model.interfaces

interface IPreferencesManager {
    fun save(key: String, value: String)
    fun save(key: String, value: Boolean)
    operator fun get(key: String, defaultValue: String): String
    operator fun get(key: String, defaultValue: Boolean): Boolean
}
