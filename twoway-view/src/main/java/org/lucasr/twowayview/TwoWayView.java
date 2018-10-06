/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lucasr.twowayview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.lucasr.twowayview.decorator.FastScroller;
import org.lucasr.twowayview.manager.interfaces.TwoWayLayoutManager;

import java.lang.reflect.Constructor;

import androidx.recyclerview.widget.RecyclerView;

public class TwoWayView extends RecyclerView {
    private static final String LOGTAG = "TwoWayView";
    private FastScroller mFastScroller;

    private static final Class<?>[] sConstructorSignature = new Class[] {
            Context.class, AttributeSet.class};

    final Object[] sConstructorArgs = new Object[2];

    public TwoWayView(Context context) {
        this(context, null);
    }

    public TwoWayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoWayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);

        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.twowayview_TwoWayView, defStyle, 0);

        final String name = a.getString(R.styleable.twowayview_TwoWayView_twowayview_layoutManager);
        if (!TextUtils.isEmpty(name)) {
            loadLayoutManagerFromName(context, attrs, name);
        }

        layout(context, attrs);

        a.recycle();
    }

    private void loadLayoutManagerFromName(Context context, AttributeSet attrs, String name) {
        try {
            final int dotIndex = name.indexOf('.');
            if (dotIndex == -1) {
                name = "org.lucasr.twowayview.manager." + name;
            } else if (dotIndex == 0) {
                final String packageName = context.getPackageName();
                name = packageName + "." + name;
            }

            Class<? extends TwoWayLayoutManager> clazz =
                    context.getClassLoader().loadClass(name).asSubclass(TwoWayLayoutManager.class);

            Constructor<? extends TwoWayLayoutManager> constructor =
                    clazz.getConstructor(sConstructorSignature);

            sConstructorArgs[0] = context;
            sConstructorArgs[1] = attrs;

            setLayoutManager(constructor.newInstance(sConstructorArgs));
        } catch (Exception e) {
            throw new IllegalStateException("Could not load TwoWayLayoutManager from " +
                                             "class: " + name, e);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mFastScroller.attachRecyclerView(this);

        ViewParent parent = getParent();

        if (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.addView(mFastScroller);
            mFastScroller.setLayoutParams(viewGroup);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mFastScroller.detachRecyclerView();
        super.onDetachedFromWindow();
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (!(layout instanceof TwoWayLayoutManager)) {
            throw new IllegalArgumentException("TwoWayView can only use TwoWayLayoutManager " +
                                                "subclasses as its layout manager");
        }

        super.setLayoutManager(layout);
    }

    public TwoWayLayoutManager.Orientation getOrientation() {
        TwoWayLayoutManager layout = (TwoWayLayoutManager) getLayoutManager();
        return layout.getOrientation();
    }

    public void setOrientation(TwoWayLayoutManager.Orientation orientation) {
        TwoWayLayoutManager layout = (TwoWayLayoutManager) getLayoutManager();
        layout.setOrientation(orientation);
    }

    public int getFirstVisiblePosition() {
        TwoWayLayoutManager layout = (TwoWayLayoutManager) getLayoutManager();
        return layout.getFirstVisiblePosition();
    }

    public int getLastVisiblePosition() {
        TwoWayLayoutManager layout = (TwoWayLayoutManager) getLayoutManager();
        return layout.getLastVisiblePosition();
    }

    private void layout(Context context, AttributeSet attrs) {
        mFastScroller = new FastScroller(context, attrs);
        mFastScroller.setId(R.id.twowayview_fastscroller);
    }
}
