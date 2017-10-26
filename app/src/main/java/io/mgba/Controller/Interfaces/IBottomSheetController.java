package io.mgba.Controller.Interfaces;

import android.view.View;

import com.flipboard.bottomsheet.BottomSheetLayout;

import io.mgba.Data.DTOs.Game;

public interface IBottomSheetController {
    View getView(BottomSheetLayout sheet, Game game);
}
