package io.mgba.utilities.permissions

/**
 * Created by André Carvalho on 21/08/2018
 */
interface PermissionsManager {
    fun requestPermission(permission: Array<String>, id: Int)
    fun onPermissionDenied(id: Int)
    fun onPermissionAccepted(id: Int)


    enum class PERMISSION_STATE {
        GRANTED,
        DENIED,
        UNKNOWN
    }
}