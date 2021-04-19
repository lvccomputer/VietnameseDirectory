package com.devlv.vietnamesedictionary.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.ui.main.activities.MainActivity;

public abstract class BaseFragment extends Fragment {
    protected View view;
    protected MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutResource(), container, false);
        mainActivity = (MainActivity) getActivity();
        Glide.with(this).load(R.drawable.bg_app).into((ImageView) view.findViewById(R.id.img_background));
        return view;
    }
    protected abstract int getLayoutResource();
}