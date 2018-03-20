package io.mgba.UI.Views.Interfaces;

import android.os.Bundle;
import android.view.View;

import com.flipboard.bottomsheet.BottomSheetLayout;

import io.mgba.Data.Database.Game;

public interface IGameInformationView {
    View prepareView(BottomSheetLayout container, Game game);
    void onSaveInstanceState(Bundle outState);
    View prepareView(BottomSheetLayout container, Bundle inState);

}
