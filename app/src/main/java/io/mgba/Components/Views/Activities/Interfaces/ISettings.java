package io.mgba.Components.Views.Activities.Interfaces;

public interface ISettings {
    void requestStoragePermission();
    String requestPreferencesValue(String key, String defaultValue);
}
