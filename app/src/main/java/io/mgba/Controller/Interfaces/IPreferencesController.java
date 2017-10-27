package io.mgba.Controller.Interfaces;

public interface IPreferencesController {
    void save(String key, String value);
    void save(String key, boolean value);
    String get(String key, String defaultValue);
    boolean get(String key, boolean defaultValue);
}
