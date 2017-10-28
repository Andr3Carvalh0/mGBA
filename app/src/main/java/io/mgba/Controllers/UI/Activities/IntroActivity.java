package io.mgba.Controllers.UI.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils;

import java.util.LinkedList;
import java.util.List;

import io.mgba.Constants;
import io.mgba.R;
import io.mgba.Services.Interfaces.IPermissionService;
import io.mgba.Services.System.PermissionService;
import io.mgba.Services.System.PreferencesService;
import io.mgba.mgba;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class IntroActivity extends AppIntro2 {


    private List<AppIntroFragment> slides = new LinkedList<>();
    private IPermissionService permissionService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionService = new PermissionService(this);

        //Welcome-screen
        slides.add(AppIntro2Fragment.newInstance(getResources().getString(R.string.Welcome_Title),
                getResources().getString(R.string.Welcome_Description),
                R.mipmap.ic_launcher,
                getResources().getColor(R.color.colorPrimary)));

        //Feature-Library
        slides.add(AppIntro2Fragment.newInstance(getResources().getString(R.string.Library_Title),
                getResources().getString(R.string.Library_Description),
                R.mipmap.ic_launcher,
                getResources().getColor(R.color.colorPrimary)));

        for (AppIntroFragment frag : slides) {
            addSlide(frag);
        }

        setProgressButtonEnabled(true);
        setFadeAnimation();
        showSkipButton(false);

    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showFilePicker() {
        permissionService.showFilePicker();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForStorage(final PermissionRequest request) {
        permissionService.showRationaleForStorage(request);
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onDeniedForStorage(){
        onEnd();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        IntroActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PermissionService.DIR_CODE && resultCode == Activity.RESULT_OK) {
            String dir = FilePickerUtils.getSelectedDir(intent);
            onEnd(dir);
        }
    }

    private void onEnd(String dir){
        ((mgba)getApplication()).savePreference(PreferencesService.GAMES_DIRECTORY, dir);
        onEnd();
    }


    private void onEnd(){
        ((mgba)getApplication()).savePreference(PreferencesService.SETUP_DONE, true);

        Intent it = new Intent(getBaseContext(), SplashActivity.class);
        it.putExtra(Constants.SHOULD_RESTART, true);
        startActivity(it);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        IntroActivityPermissionsDispatcher.showFilePickerWithPermissionCheck(this);

    }
}