package io.mgba.ui.activities.interfaces

import java.util.HashMap

import androidx.appcompat.app.AppCompatActivity

interface ISettingsView {
    fun startActivity(activity: Class<out AppCompatActivity>, extras: HashMap<String, String>)
}
