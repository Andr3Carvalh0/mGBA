package io.mgba.model.system

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment

import com.nononsenseapps.filepicker.Views.Activities.FilePickerActivity

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.mgba.model.interfaces.IPermissionManager
import io.mgba.R
import permissions.dispatcher.PermissionRequest

class PermissionManager(private val activity: AppCompatActivity) : IPermissionManager {

    override fun hasPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) activity.applicationContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED else true

    }

    override fun showFilePicker() {
        val i = Intent(activity.applicationContext, FilePickerActivity::class.java)
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().path)

        activity.startActivityForResult(i, DIR_CODE)
    }

    override fun showRationaleForStorage(request: PermissionRequest) {
        AlertDialog.Builder(activity)
                .setMessage(R.string.permission_storage_rationale)
                .setPositiveButton(R.string.button_allow) { dialog, button -> request.proceed() }
                .setNegativeButton(R.string.button_deny) { dialog, button -> request.cancel() }
                .show()
    }

    companion object {
        val DIR_CODE = 347
    }
}