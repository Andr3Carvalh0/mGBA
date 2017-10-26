package io.mgba.Components.UI.Activities.Interfaces;

public interface ISettings {
    void requestStoragePermission();
    String requestPreferencesValue(String key, String defaultValue);
}
