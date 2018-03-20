package io.mgba.Data.Settings;

public class Settings {
    private final String title;
    private final int resource;

    public Settings(String title, int resource) {
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
