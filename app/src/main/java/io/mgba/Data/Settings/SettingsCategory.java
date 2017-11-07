package io.mgba.Data.Settings;

public class SettingsCategory {
    private final String title;
    private final int resource;

    public SettingsCategory(String title, int resource) {
        this.title = title;
        this.resource = resource;
    }

    public String getTitle() {
        return title;
    }

    public int getResource() {
        return resource;
    }
}
