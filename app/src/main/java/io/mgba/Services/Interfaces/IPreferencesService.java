package io.mgba.Services.Interfaces;

public interface IPreferencesService {
    void save(String key, String value);
    void save(String key, boolean value);
    String get(String key, String defaultValue);
    boolean get(String key, boolean defaultValue);
}
