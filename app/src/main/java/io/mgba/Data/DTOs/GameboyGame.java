package io.mgba.Data.DTOs;

import java.io.File;

import io.mgba.Data.DTOs.Interface.Game;

public class GameboyGame extends Game {
    public GameboyGame(File file) {
        super(file);
    }
}
