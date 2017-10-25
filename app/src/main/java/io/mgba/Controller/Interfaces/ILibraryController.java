package io.mgba.Controller.Interfaces;

import com.google.common.base.Function;

public interface ILibraryController {

    /**
    * Starts fetching the games present in the selected directory.
    */
    void prepareGames(Function<LibraryLists, Void> callback);

    /**
     * Called when the activity will enter background.
     * Since its on the background there isnt a guarantee that its alive. So we remove the broadcast
     * Since we dont have the broadcast the result will not be delivered, however the processing will be done,
     * and saved to the CP. So next time the activity will be alive the results will be fetch to the CP
     */
    void stop();


}

