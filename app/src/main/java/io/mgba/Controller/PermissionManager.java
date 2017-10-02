package io.mgba.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;
import com.nononsenseapps.filepicker.Views.Activities.FilePickerActivity;

import io.mgba.R;
import permissions.dispatcher.PermissionRequest;


public class PermissionManager{
    public static final int DIR_CODE = 347;

    public void showFilePicker(Activity activity) {
        Intent i = new Intent(activity.getApplicationContext(), FilePickerActivity.class);
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        activity.startActivityForResult(i, DIR_CODE);
    }

    public void showRationaleForStorage(final PermissionRequest request, Context ctx) {
        new AlertDialog.Builder(ctx)
                .setMessage(R.string.permission_storage_rationale)
                .setPositiveButton(R.string.button_allow, (dialog, button) -> request.proceed())
                .setNegativeButton(R.string.button_deny, (dialog, button) -> request.cancel())
                .show();
    }

    public void showDeniedForStorage(Context ctx) {
        Toast.makeText(ctx, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
    }

    public void showNeverAskForStorage(Context ctx) {
        Toast.makeText(ctx, R.string.permission_storage_neverask, Toast.LENGTH_SHORT).show();
    }

}
