package io.mgba.UI.Views.Interfaces;

import android.view.View;

import com.flipboard.bottomsheet.BottomSheetLayout;

import io.mgba.Data.Database.Game;

public interface IGameInformationView {
    View getView(BottomSheetLayout sheet, Game game);


}
