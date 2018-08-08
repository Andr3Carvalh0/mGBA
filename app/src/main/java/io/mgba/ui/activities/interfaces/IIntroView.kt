package io.mgba.ui.activities.interfaces

import com.github.paolorotolo.appintro.AppIntroFragment

import androidx.appcompat.app.AppCompatActivity

interface IIntroView {
    fun addSlides(slides: List<AppIntroFragment>)
    fun savePreference(key: String, value: String)
    fun savePreference(key: String, value: Boolean)
    fun showProgressDialog()
    fun startActivity(activity: Class<out AppCompatActivity>)
}
