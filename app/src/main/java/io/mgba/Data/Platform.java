package io.mgba.Data;

import java.util.Arrays;
import java.util.List;

public enum Platform {
    GBA(0, "gba"),
    GBC(1, "gb", "gbc"),
    FAVS(2);

    private final int value;
    private final List<String> extensions;

    Platform(int value, String... extension)
    {
        this.value = value;
        this.extensions = Arrays.asList(extension);
    }

    public static Platform forValue(Integer platform) {
        switch (platform){
            case 0:
                return GBA;
            case 1:
                return GBC;
            default:
                return FAVS;
        }
    }

    public int getValue() {
        return value;
    }

    public List<String> getExtensions(){
        return extensions;
    }
}
