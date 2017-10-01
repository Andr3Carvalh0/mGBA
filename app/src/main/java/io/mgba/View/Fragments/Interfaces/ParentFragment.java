package io.mgba.View.Fragments.Interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.mgba.R;

public abstract class ParentFragment extends Fragment {
    protected View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.favourites_fragment, container, false);

        return mView;
    }
}
