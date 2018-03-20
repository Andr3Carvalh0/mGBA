package io.mgba.Presenter.Interfaces;

import android.content.Intent;
import android.os.Bundle;

import permissions.dispatcher.PermissionRequest;

public interface ISettingsPanelPresenter {
    void init(Bundle savedInstanceState);

    void onSaveInstance(Bundle outState);

    void setupFragment();

    void onActivityResult(int requestCode, int resultCode, Intent intent);

    void showFilePicker();

    void showRationaleForStorage(PermissionRequest request);

    String requestPreferencesValue(String key, String defaultValue);

    String getTitle();
}
