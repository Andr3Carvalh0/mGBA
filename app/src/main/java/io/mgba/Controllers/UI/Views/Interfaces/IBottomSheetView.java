package io.mgba.Controllers.UI.Views.Interfaces;

import android.view.View;

import com.flipboard.bottomsheet.BottomSheetLayout;

import io.mgba.Data.DTOs.Game;

public interface IBottomSheetView {
    View getView(BottomSheetLayout sheet, Game game);
}
