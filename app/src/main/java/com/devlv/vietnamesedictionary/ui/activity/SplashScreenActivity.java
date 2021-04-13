package com.devlv.vietnamesedictionary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.ui.main.MainActivity;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected int getActivityResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        mHandler.postDelayed(mRun, 4000);
        Glide.with(this).load(R.drawable.bg_app).into((ImageView) findViewById(R.id.img_background));
    }

    @Override
    protected void bindViews() {

    }

    @Override
    protected void actions() {

    }

    private Handler mHandler = new Handler();
    private Runnable mRun = () -> {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRun);
    }
}