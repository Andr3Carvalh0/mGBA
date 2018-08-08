package io.mgba.presenter.interfaces

import android.content.Intent
import android.os.Bundle

import permissions.dispatcher.PermissionRequest

interface ISettingsPanelPresenter {

    val title: String

    fun onSaveInstance(outState: Bundle)

    fun setupFragment()

    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent)

    fun showFilePicker()

    fun showRationaleForStorage(request: PermissionRequest)

    fun requestPreferencesValue(key: String, defaultValue: String): String
}
