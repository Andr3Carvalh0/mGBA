package io.mgba.UI.Activities.Interfaces;

public interface ISettings {
    void requestStoragePermission();
    String requestPreferencesValue(String key, String defaultValue);
}
