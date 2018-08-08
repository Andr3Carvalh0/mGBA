package io.mgba.presenter.interfaces

import android.content.Intent

import permissions.dispatcher.PermissionRequest

interface IIntroPresenter {

    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent)

    fun onDeniedForStorage()

    fun showFilePicker()

    fun showRationaleForStorage(request: PermissionRequest)

    fun onResume()

    fun onPause()
}
