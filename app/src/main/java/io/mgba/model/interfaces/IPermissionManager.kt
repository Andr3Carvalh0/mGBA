package io.mgba.model.interfaces

import permissions.dispatcher.PermissionRequest

interface IPermissionManager {
    fun hasPermission(permission: String): Boolean
    fun showFilePicker()
    fun showRationaleForStorage(request: PermissionRequest)
}
