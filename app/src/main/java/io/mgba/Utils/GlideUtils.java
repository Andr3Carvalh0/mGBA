package io.mgba.Utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.glidepalette.GlidePalette;
import java.util.LinkedList;
import java.util.List;

public class GlideUtils<T> {

    private final String url;
    private T holder;
    private int placeholder = -1;
    private int onError = -1;
    private List<ColorView> views = new LinkedList<>();

    private GlideUtils(T holder, String url) {
        this.url = url;
        this.holder = holder;
    }

    public static GlideUtils init(View holder, String url){
        return new GlideUtils<>(holder, url);
    }

    public static GlideUtils init(Fragment holder, String url){
        return new GlideUtils<>(holder, url);
    }

    public GlideUtils setPlaceholders(int placeholder, int onError){
        this.placeholder = placeholder;
        this.onError = onError;

        return this;
    }

    public GlideUtils setPlaceholder(int placeholder){
        this.placeholder = placeholder;

        return this;
    }

    public GlideUtils setOnError(int onError){
        this.onError = onError;

        return this;
    }

    public GlideUtils colorView(Colors primarySwatch, Colors secundarySwatch, View... view){
        Stream.of(view)
                .map(v -> new ColorWrapper(v, primarySwatch, secundarySwatch))
                .forEach(c -> views.add(c));

        return this;
    }

    public GlideUtils colorView(Colors swatch, boolean title, TextView... view){
        Stream.of(view)
                .map(v -> new SpecialColorWrapper(swatch, v, title))
                .forEach(c -> views.add(c));

        return this;
    }

    public void build(ImageView imageView){
        RequestBuilder request = prepare();

        processPlaceholders(request);
        processColorExtraction(request);

        request.into(imageView);
    }

    private RequestBuilder prepare(){
        if(holder instanceof Fragment)
            return Glide.with((Fragment) holder).load(url);

        return Glide.with((View)holder).load(url);
    }

    private void processPlaceholders(RequestBuilder rm){
        final RequestOptions requestOptions = new RequestOptions();

        if(placeholder != -1)
            requestOptions.placeholder(placeholder);

        if(onError != -1)
            requestOptions.error(onError);

        rm.apply(requestOptions);
    }

    private void processColorExtraction(RequestBuilder rm){
        final GlidePalette<Drawable> requestColor = GlidePalette.with(url);

        requestColor
                .intoCallBack(palette -> {
                    if (palette != null) {
                        Stream.of(views)
                                .forEach(v -> v.colorView(palette, requestColor));
                    }
                });

        rm.listener(requestColor);
    }

    private Palette.Swatch getPalette(Colors v, Palette palette) {
        switch (v){
            case MUTED:
                return palette.getMutedSwatch();
            case VIBRANT:
                return palette.getVibrantSwatch();
            case LIGHT_MUTED:
                return palette.getLightMutedSwatch();
            case LIGHT_VIBRANT:
                return palette.getLightVibrantSwatch();
            case DARK_MUTED:
                return palette.getDarkMutedSwatch();
            default:
                return palette.getDarkVibrantSwatch();
        }
    }

    public enum Colors {
        VIBRANT(0),
        DARK_VIBRANT(1),
        LIGHT_VIBRANT(2),
        MUTED(3),
        DARK_MUTED(4),
        LIGHT_MUTED(5);

        private int value;

        Colors(int v) {
            this.value = v;
        }

        public int getValue() {
            return value;
        }
    }

    private abstract class ColorView{
        protected final View view;

        ColorView(View view) {
            this.view = view;
        }

        abstract void colorView(Palette palette, GlidePalette<Drawable> requestColor);

        public View getView() {
            return view;
        }
    }

    private class SpecialColorWrapper extends ColorView{
        private final Colors profile;
        private boolean title;

        SpecialColorWrapper(Colors profile, View view, boolean title) {
            super(view);
            this.profile = profile;
            this.title = title;
        }

        @Override
        void colorView(Palette palette, GlidePalette<Drawable> requestColor) {
            requestColor.use(profile.getValue())
                    .intoTextColor((TextView) view, title ? GlidePalette.Swatch.TITLE_TEXT_COLOR
                                                          : GlidePalette.Swatch.BODY_TEXT_COLOR);
        }
    }

    private class ColorWrapper extends ColorView{

        private final Colors primarySwatch;
        private final Colors secondarySwatch;

        ColorWrapper(View view, Colors primarySwatch, Colors secondarySwatch) {
            super(view);
            this.primarySwatch = primarySwatch;
            this.secondarySwatch = secondarySwatch;
        }

        Colors getPrimarySwatch() {
            return primarySwatch;
        }

        Colors getSecondarySwatch() {
            return secondarySwatch;
        }

        void colorView(Palette palette, GlidePalette<Drawable> requestColor) {
            Palette.Swatch swatch = getPalette(getPrimarySwatch(), palette);

            if(swatch == null)
                swatch = getPalette(getSecondarySwatch(), palette);

            if(swatch == null)
                return;

            if (view instanceof TextView) {
                ((TextView) view).setTextColor(ColorStateList.valueOf(swatch.getRgb()));
                return;
            }

            if (view instanceof FloatingActionButton){
                view.setBackgroundTintList(ColorStateList.valueOf(swatch.getRgb()));
                return;
            }

            if (view instanceof ImageView) {
                ((ImageView) view).setColorFilter(swatch.getRgb(), android.graphics.PorterDuff.Mode.SRC_IN);
                return;
            }

            view.setBackgroundColor(swatch.getRgb());
        }
    }
}
