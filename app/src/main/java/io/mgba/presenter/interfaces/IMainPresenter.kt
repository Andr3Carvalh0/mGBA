package io.mgba.presenter.interfaces

import android.content.Intent
import android.view.MenuItem
import io.mgba.model.interfaces.ILibrary

interface IMainPresenter {
    val iLibrary: ILibrary?

    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent)
    fun onDestroy()
    fun onMenuItemSelected(item: MenuItem)
    fun onSearchTextChanged(oldQuery: String, newQuery: String)

}
