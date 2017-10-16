package io.mgba.Data.DTOs.Interface;

import java.io.File;

public class Game {
    private File file;
    private String name;
    private String game_title;
    private String game_code;
    private String maker_name;

    public Game() {
    }

    public Game(File file) {
        this.file = file;
    }

    public Game(File file, String name, String game_title, String game_code, String maker_name) {
        this.file = file;
        this.name = name;
        this.game_title = game_title;
        this.game_code = game_code;
        this.maker_name = maker_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGame_title(String game_title) {
        this.game_title = game_title;
    }

    public void setGame_code(String game_code) {
        this.game_code = game_code;
    }

    public void setMaker_name(String maker_name) {
        this.maker_name = maker_name;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        if(name == null)
            return file.getName();
        return name;
    }

    public String getGame_title() {
        return game_title;
    }

    public String getGame_code() {
        return game_code;
    }

    public String getMaker_name() {
        return maker_name;
    }
}
