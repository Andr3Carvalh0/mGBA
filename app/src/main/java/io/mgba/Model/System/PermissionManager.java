package io.mgba.Model.System;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.nononsenseapps.filepicker.Views.Activities.FilePickerActivity;

import io.mgba.Model.Interfaces.IPermissionManager;
import io.mgba.R;
import permissions.dispatcher.PermissionRequest;

public class PermissionManager implements IPermissionManager {
    public static final int DIR_CODE = 347;
    private final AppCompatActivity activity;

    public PermissionManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean hasPermission(String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return activity.getApplicationContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;

        return true;
    }

    @Override
    public void showFilePicker() {
        Intent i = new Intent(activity.getApplicationContext(), FilePickerActivity.class);
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        activity.startActivityForResult(i, DIR_CODE);
    }

    @Override
    public void showRationaleForStorage(final PermissionRequest request) {
        new AlertDialog.Builder(activity)
                .setMessage(R.string.permission_storage_rationale)
                .setPositiveButton(R.string.button_allow, (dialog, button) -> request.proceed())
                .setNegativeButton(R.string.button_deny, (dialog, button) -> request.cancel())
                .show();
    }
}