package io.mgba.Controller.Interfaces;

import java.util.List;

import io.mgba.Data.DTOs.Game;

public class LibraryLists{
    private final List<Game> favourites;
    private final List<Game> gba;
    private final List<Game> gbc;

    public LibraryLists(List<Game> favourites, List<Game> gba, List<Game> gbc) {
        this.favourites = favourites;
        this.gba = gba;
        this.gbc = gbc;
    }

    public List<Game> getFavourites() {
        return favourites;
    }

    public List<Game> getGba() {
        return gba;
    }

    public List<Game> getGbc() {
        return gbc;
    }
}
