package io.mgba.utilities.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import androidx.core.content.PermissionChecker

/**
 * Created by André Carvalho on 21/08/2018
 */
object PermissionController {

    val TAG = 27

    enum class Permissions(val id: Int, val permission: Array<String>) {
        LOCATION_PERMISSION(0, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)),
        MICROPHONE_PERMISSION(1, arrayOf(Manifest.permission.RECORD_AUDIO)),
        STORAGE_PERMISSION(2, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }

    fun isPermissionGranted(context: Context, permission: Array<String>): Boolean {
        var granted = permission.size

        for (item in permission.indices) {
            if (PermissionChecker.checkSelfPermission(context, permission[item]) == PackageManager.PERMISSION_GRANTED) {
                granted--
            }
        }

        return granted == 0
    }

    fun requestPermission(listener: PermissionsManager, context: Context, permission: Permissions, title: String, description: String, onGranted: () -> Any, id: Int){
        if(SDK_INT < M || isPermissionGranted(context, permission.permission)){
            //The permission is already granted or we dont need to ask
            onGranted.invoke()
        } else {
            listener.requestPermission(permission.permission, id)
        }
    }

    fun requestPermissions(listener: PermissionsManager, context: Context, permissions: Array<Permissions>, onGranted: () -> Unit, id: Int){
        val permission = ArrayList<String>()

        for (item in permissions) {
            if(SDK_INT < M || isPermissionGranted(context, item.permission)){
                // Nothing to do here, since we already have that permission
            } else {
                permission.addAll(item.permission)
            }
        }

        // Now... If the permission is empty we have all the the permissions we need, so we can call the onGranted
        if(permission.size == 0){
            onGranted.invoke()
        } else {
            listener.requestPermission(permission.toTypedArray(), id)
        }
    }


    fun requestPermissions(listener: PermissionsManager, context: Context, permissions: Array<Permissions>, title: String, description: String, onGranted: () -> Unit, id: Int){
        val permission = ArrayList<String>()

        for (item in permissions) {
            if(SDK_INT < M || isPermissionGranted(context, item.permission)){
                // Nothing to do here, since we already have that permission
            } else {
                permission.addAll(item.permission)
            }
        }

        // Now... If the permission is empty we have all the the permissions we need, so we can call the onGranted
        if(permission.size == 0){
            onGranted.invoke()
        } else {
            listener.requestPermission(permission.toTypedArray(), id)
        }
    }

    fun onPermissionRequestResult(listener: PermissionsManager, permissions: Array<out String>, requestCode: Int, grantResults: IntArray) {
        var granted = permissions.size

        for (item in permissions.indices) {
            if (grantResults[item] == PackageManager.PERMISSION_GRANTED) {
                granted--
            }
        }

        if (granted == 0) {
            listener.onPermissionAccepted(requestCode)
        } else {
            listener.onPermissionDenied(requestCode)
        }
    }
}
