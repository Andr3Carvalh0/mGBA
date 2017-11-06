package io.mgba.Data;

public enum Platform {
    GBA(0),
    GBC(1),
    FAVS(2);

    private final int value;

    Platform(int value)
    {
        this.value = value;
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
}
