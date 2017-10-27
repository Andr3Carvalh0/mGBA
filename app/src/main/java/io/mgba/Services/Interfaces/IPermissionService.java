package io.mgba.Services.Interfaces;

import permissions.dispatcher.PermissionRequest;

public interface IPermissionService {
    boolean hasPermission(String permission);
    void showFilePicker();
    void showRationaleForStorage(final PermissionRequest request);
}
