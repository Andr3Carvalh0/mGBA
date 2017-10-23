package io.mgba.View.Activities.Interfaces;


public interface IMain {
    void getFavouritesAsync();
    void getGBAGamesAsync();
    void getGBGamesAsync();
    int[] getItemsPerColumn();
    int getDPWidth();
}
