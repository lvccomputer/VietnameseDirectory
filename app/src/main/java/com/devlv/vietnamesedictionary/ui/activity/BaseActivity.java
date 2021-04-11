package com.devlv.vietnamesedictionary.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.devlv.vietnamesedictionary.ui.IToast;

public abstract class BaseActivity extends AppCompatActivity implements IToast {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityResource());
        onCreateActivity(savedInstanceState);
    }

    protected abstract int getActivityResource();

    protected abstract void onCreateActivity(Bundle bundle);

    protected abstract void bindViews();

    protected abstract void actions();

}