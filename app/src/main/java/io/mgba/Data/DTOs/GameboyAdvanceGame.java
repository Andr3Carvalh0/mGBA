package io.mgba.Data.DTOs;

import java.io.File;

import io.mgba.Data.DTOs.Interface.Game;

public class GameboyAdvanceGame extends Game {


    public GameboyAdvanceGame(File file) {
        super(file);
    }

    public GameboyAdvanceGame(File file, String name, String game_title, String game_code, String maker_name) {
        super(file, name, game_title, game_code, maker_name);
    }
}
