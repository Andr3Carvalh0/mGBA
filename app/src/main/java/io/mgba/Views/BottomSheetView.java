package io.mgba.Views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.github.florent37.glidepalette.GlidePalette;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Data.DTOs.Game;
import io.mgba.R;
import io.mgba.Views.Interfaces.IBottomSheetView;

public class BottomSheetView implements IBottomSheetView {

    private final Context ctx;
    @BindView(R.id.gameDescription)
    TextView gameDescription;
    @BindView(R.id.gameTitle)
    TextView gameTitle;
    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.bottomsheet_header)
    RelativeLayout bottomsheetHeader;
    @BindView(R.id.savestate_recyclerview)
    RecyclerView bottomsheetRecyclerview;
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
        this.ctx = ctx;
    }

    @Override
    public View getView(BottomSheetLayout sheet, Game game) {

        view = LayoutInflater.from(ctx).inflate(R.layout.library_sheet_view, sheet, false);
        ButterKnife.bind(this, view);
        prepareView(game);

        return view;
    }

    private void prepareView(Game game) {
        gameTitle.setText(game.getName());
        gameDescription.setText(game.getDescription());

        if(game.getCoverURL() != null) {

            Glide.with(view)
                .load(game.getCoverURL())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.error))
                .listener(
                        GlidePalette
                        .with(game.getCoverURL())
                        .intoCallBack(palette -> {
                            if (palette != null) {
                                final Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                                final Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();

                                final Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                                final Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

                                if (darkMutedSwatch != null) {
                                    fab.setBackgroundTintList(ColorStateList.valueOf(darkMutedSwatch.getRgb()));
                                    savestateTitle.setTextColor(ColorStateList.valueOf(darkMutedSwatch.getRgb()));
                                }

                                if (vibrantSwatch != null) {
                                    fab.setBackgroundTintList(ColorStateList.valueOf(vibrantSwatch.getRgb()));
                                    savestateTitle.setTextColor(ColorStateList.valueOf(vibrantSwatch.getRgb()));
                                }

                                if (lightVibrantSwatch != null) {
                                    bottomsheetHeader.setBackgroundColor(lightVibrantSwatch.getRgb());
                                    noSavestateMessage.setTextColor(ColorStateList.valueOf(lightVibrantSwatch.getRgb()));
                                    noContentImage.setColorFilter(lightVibrantSwatch.getRgb(), android.graphics.PorterDuff.Mode.SRC_IN);

                                }

                                if (lightMutedSwatch != null) {
                                    bottomsheetHeader.setBackgroundColor(lightMutedSwatch.getRgb());
                                    noSavestateMessage.setTextColor(ColorStateList.valueOf(lightMutedSwatch.getRgb()));
                                    noContentImage.setColorFilter(lightMutedSwatch.getRgb(), android.graphics.PorterDuff.Mode.SRC_IN);
                                }

                            }
                        })
                        .use(GlidePalette.Profile.VIBRANT_LIGHT)
                        .intoTextColor(gameDescription, GlidePalette.Swatch.BODY_TEXT_COLOR)
                        .intoTextColor(gameTitle, GlidePalette.Swatch.TITLE_TEXT_COLOR))
                .into(cover);
        }
    }
}
