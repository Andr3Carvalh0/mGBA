package io.mgba.data.interfaces

import permissions.dispatcher.PermissionRequest

interface IPermissionManager {
    fun hasPermission(permission: String): Boolean
    fun showFilePicker()
    fun showRationaleForStorage(request: PermissionRequest)
}
