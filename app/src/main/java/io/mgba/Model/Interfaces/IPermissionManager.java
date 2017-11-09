package io.mgba.Model.Interfaces;

import permissions.dispatcher.PermissionRequest;

public interface IPermissionManager {
    boolean hasPermission(String permission);
    void showFilePicker();
    void showRationaleForStorage(final PermissionRequest request);
}
