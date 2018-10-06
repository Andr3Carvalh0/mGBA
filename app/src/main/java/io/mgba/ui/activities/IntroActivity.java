package io.mgba.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import io.mgba.model.system.PermissionManager;
import io.mgba.model.system.PreferencesManager;
import io.mgba.presenter.interfaces.IIntroPresenter;
import io.mgba.presenter.IntroPresenter;
import io.mgba.ui.activities.interfaces.IIntroView;
import io.mgba.mgba;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class IntroActivity extends AppIntro2 implements IIntroView{

    private IIntroPresenter controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new IntroPresenter(new PermissionManager(this), this);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showFilePicker() {
        controller.showFilePicker();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForStorage(final PermissionRequest request) {
        controller.showRationaleForStorage(request);
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onDeniedForStorage(){
        controller.onDeniedForStorage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        IntroActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        controller.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        IntroActivityPermissionsDispatcher.showFilePickerWithPermissionCheck(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.onResume();
    }

    @Override
    public void addSlides(List<AppIntroFragment> slides) {
        for (AppIntroFragment frag : slides)
            this.addSlide(frag);

        this.setProgressButtonEnabled(true);
        this.setFadeAnimation();
        this.showSkipButton(false);
    }

    @Override
    public void savePreference(String key, String value) {
        PreferencesManager.INSTANCE.save(key, value);
    }

    @Override
    public void savePreference(String key, boolean value) {
        PreferencesManager.INSTANCE.save(key, value);
    }

    @Override
    public void showProgressDialog() {
        ((mgba)getApplication()).showProgressDialog(this);
    }

    @Override
    public void startActivity(Class<? extends AppCompatActivity> activity) {
        Intent it = new Intent(this, activity);
        startActivity(it);
        finish();
    }
}
