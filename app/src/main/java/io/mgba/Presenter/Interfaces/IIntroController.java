package io.mgba.Presenter.Interfaces;

import android.content.Intent;

import com.github.paolorotolo.appintro.AppIntro2;

import permissions.dispatcher.PermissionRequest;

public interface IIntroController {
    void setupView(AppIntro2 introActivity);

    void onActivityResult(int requestCode, int resultCode, Intent intent);

    void onDeniedForStorage();

    void showFilePicker();

    void showRationaleForStorage(PermissionRequest request);

    void onResume();

    void onPause();
}
