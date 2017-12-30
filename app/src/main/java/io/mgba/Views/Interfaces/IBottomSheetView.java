package io.mgba.Views.Interfaces;

import android.view.View;

import com.flipboard.bottomsheet.BottomSheetLayout;

import io.mgba.Data.Database.Game;

public interface IBottomSheetView {
    View getView(BottomSheetLayout sheet, Game game);
}
