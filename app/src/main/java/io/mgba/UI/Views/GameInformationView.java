package io.mgba.UI.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import butterknife.OnClick;
import io.mgba.Constants;
import io.mgba.Data.Database.Game;
import io.mgba.R;
import io.mgba.UI.Activities.EmulationActivity;
import io.mgba.UI.Views.Interfaces.IGameInformationView;
import io.mgba.Utils.GlideUtils;
import io.mgba.Utils.GlideUtils.Colors;

public class GameInformationView implements IGameInformationView, View.OnClickListener {

    private final Context context;

    @BindView(R.id.sheet_container)
    CoordinatorLayout main;

    @BindView(R.id.bottomsheet_header) RelativeLayout header;
    @BindView(R.id.gameTitle) TextView title;
    @BindView(R.id.gameDescription) TextView description;
    @BindView(R.id.cover) ImageView cover;

    @BindView(R.id.fab) FloatingActionButton playButton;

    @BindView(R.id.savestate_title) TextView savesHeader;
    @BindView(R.id.savestate_recyclerview) RecyclerView recyclerView;

    @BindView(R.id.no_content_image) ImageView noContentImage;
    @BindView(R.id.no_savestate_message) TextView noSavesMessage;
    @BindView(R.id.no_savestates_container) RelativeLayout noSavesContainer;

    private Game game;

    public GameInformationView(Context ctx) {
        this.context = ctx;
    }

    @Override
    public View prepareView(BottomSheetLayout container, Game game) {
        this.game = game;

        View view = LayoutInflater.from(context).inflate(R.layout.library_sheet_view, container, false);
        ButterKnife.bind(this, view);

        prepareView(view);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.ARG_SHEET_CONTENT, game);
    }

    @Override
    public View prepareView(BottomSheetLayout container, Bundle inState) {
        Game game = (Game) inState.getParcelable(Constants.ARG_SHEET_CONTENT);

        return prepareView(container, game);
    }

    private void prepareView(View view) {
        title.setText(game.getName());
        description.setText(game.getDescription());

        if(game.getCoverURL() != null) {
            GlideUtils.init(view, game.getCoverURL())
                      .setPlaceholders(R.drawable.placeholder, R.drawable.error)
                      .colorView(Colors.VIBRANT, Colors.DARK_MUTED, playButton, savesHeader)
                      .colorView(Colors.LIGHT_MUTED, Colors.LIGHT_VIBRANT, header, noSavesMessage, noContentImage)
                      .colorView(Colors.LIGHT_VIBRANT, true, title)
                      .colorView(Colors.LIGHT_VIBRANT, false, description)
                      .build(cover);
        }
    }

    @Override
    @OnClick(R.id.fab)
    public void onClick(View v) {
        Intent it = new Intent(context, EmulationActivity.class);
        it.putExtra(Constants.ARG_PLAY_GAME, game);
        context.startActivity(it);
    }
}
