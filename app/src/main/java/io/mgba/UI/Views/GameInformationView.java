package io.mgba.UI.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.mgba.Constants;
import io.mgba.Data.Database.Game;
import io.mgba.R;
import io.mgba.UI.Activities.EmulationActivity;
import io.mgba.Utils.GlideUtils;
import io.mgba.Utils.GlideUtils.Colors;

public class GameInformationView extends BottomSheetDialogFragment implements View.OnClickListener {

    public static GameInformationView newInstance(Game game) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.ARG_PLAY_GAME, game);

        GameInformationView frag = new GameInformationView();
        frag.setArguments(args);
        return frag;
    }

    @BindView(R.id.sheet_container) CoordinatorLayout main;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library_sheet_view, container, false);
        ButterKnife.bind(this, view);

        final Bundle arguments = getArguments();

        if(arguments != null)
            this.game = arguments.getParcelable(Constants.ARG_PLAY_GAME);

        prepareView();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.ARG_SHEET_CONTENT, game);
    }


    private void prepareView() {
        title.setText(game.getName());
        description.setText(game.getDescription());

        if(cover != null && game.getCoverURL() != null) {
            GlideUtils.init(this, game.getCoverURL())
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
        Intent it = new Intent(getContext(), EmulationActivity.class);
        it.putExtra(Constants.ARG_PLAY_GAME, game);
        getContext().startActivity(it);
    }
}
