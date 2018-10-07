package com.nononsenseapps.filepicker.Views.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import com.nononsenseapps.filepicker.Controllers.Interfaces.OnFilePickedListener;
import com.nononsenseapps.filepicker.R;
import com.nononsenseapps.filepicker.Views.Fragments.FilePickerFragment;
import com.nononsenseapps.filepicker.Views.Fragments.Interfaces.FragmentContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class FilePickerActivity extends AppCompatActivity implements OnFilePickedListener {
    public static final String EXTRA_START_PATH = "nononsense.intent" + ".START_PATH";

    public static void start(AppCompatActivity context, int code){
        Intent intent = new Intent(context, FilePickerActivity.class);

        context.startActivityForResult(intent, code);
    }

    protected static final String TAG = "filepicker_fragment";

    private FragmentContract fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nnf_activity_filepicker);
        FilePickerActivityPermissionsDispatcher.onGrantWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void onGrant() {
        fragment = (FilePickerFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) { fragment = getFragment(Environment.getExternalStorageDirectory().getPath()); }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, (FilePickerFragment)fragment, TAG).commit();

        setResult(Activity.RESULT_CANCELED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        FilePickerActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onDenial() {
        onCancelled();
    }

    protected FragmentContract getFragment(@Nullable final String startPath){
        fragment = new FilePickerFragment();
        ((FilePickerFragment)fragment).setArgs(startPath);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
    }

    @Override
    public void onBackPressed() {
        if(fragment != null) {
            if(!fragment.onBackPress()) {
                onCancelled();
            }
        }
    }

    @Override
    public void onFilePicked(@NonNull final String file) {
        Intent i = new Intent();
        i.putExtra("PATH", file);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @Override
    public void onCancelled() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
