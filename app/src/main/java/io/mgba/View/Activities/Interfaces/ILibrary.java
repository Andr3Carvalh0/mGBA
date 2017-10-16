package io.mgba.View.Activities.Interfaces;

import java.util.List;

import io.mgba.Data.DTOs.GameboyAdvanceGame;
import io.mgba.Data.DTOs.GameboyGame;
import io.mgba.Data.DTOs.Interface.Game;

public interface ILibrary{
    List<Game> getFavourites();
    List<GameboyAdvanceGame> getGBAGames();
    List<GameboyGame> getGBGames();
}
