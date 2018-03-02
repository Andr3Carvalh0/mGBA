package io.mgba.UI.Views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Data.Database.Game;
import io.mgba.R;
import io.mgba.UI.Views.Interfaces.IBottomSheetView;
import io.mgba.Utils.GlideUtils;
import io.mgba.Utils.GlideUtils.Colors;

public class BottomSheetView implements IBottomSheetView {

    private final Context context;
    @BindView(R.id.gameDescription)
    TextView gameDescription;
    @BindView(R.id.gameTitle)
    TextView gameTitle;
    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.bottomsheet_header)
    RelativeLayout bottomSheetHeader;
    @BindView(R.id.savestate_recyclerview)
    RecyclerView bottomSheetRecyclerview;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.sheet_container)
    CoordinatorLayout sheetContainer;
    @BindView(R.id.savestate_title)
    TextView savestateTitle;
    @BindView(R.id.no_content_image)
    ImageView noContentImage;
    @BindView(R.id.no_savestate_message)
    TextView noSavestateMessage;
    @BindView(R.id.no_savestates_container)
    RelativeLayout noSavestatesContainer;
    private View view;

    public BottomSheetView(Context ctx) {
        this.context = ctx;
    }

    @Override
    public View getView(BottomSheetLayout sheet, Game game) {

        view = LayoutInflater.from(context).inflate(R.layout.library_sheet_view, sheet, false);
        ButterKnife.bind(this, view);
        prepareView(game);

        return view;
    }

    private void prepareView(Game game) {
        gameTitle.setText(game.getName());
        gameDescription.setText(game.getDescription());

        if(game.getCoverURL() != null) {
            GlideUtils.init(view, game.getCoverURL())
                      .setPlaceholders(R.drawable.placeholder, R.drawable.error)
                      .colorView(Colors.VIBRANT, Colors.DARK_MUTED, fab, savestateTitle)
                      .colorView(Colors.LIGHT_MUTED, Colors.LIGHT_VIBRANT, bottomSheetHeader, noSavestateMessage, noContentImage)
                      .colorView(Colors.LIGHT_VIBRANT, true, gameTitle)
                      .colorView(Colors.LIGHT_VIBRANT, false, gameDescription)
                      .build(cover);
        }
    }
}
