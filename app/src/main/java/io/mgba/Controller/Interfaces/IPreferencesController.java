package io.mgba.Controller.Interfaces;

public interface IPreferencesController {
    void save(String key, String value);
    String get(String key, String defaultValue);
}
