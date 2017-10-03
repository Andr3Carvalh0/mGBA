package io.mgba.View.Activities.Interfaces;

public interface ISettings {
    void requestStoragePermission();
    String requestPreferencesValue(String key, String defaultValue);
}
