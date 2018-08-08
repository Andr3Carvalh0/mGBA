package io.mgba.ui.activities.interfaces


import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.mikepenz.aboutlibraries.LibsBuilder
import androidx.appcompat.app.AppCompatActivity

interface IMainView {
    fun clearSuggestions()
    fun showSuggestions(list: List<SearchSuggestion>)
    fun startAboutPanel(aboutPanel: LibsBuilder)
    fun startActivityForResult(activity: Class<out AppCompatActivity>, code: Int)
    fun showProgress()
}
