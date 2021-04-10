package com.devlv.vietnamesedictionary.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.devlv.vietnamesedictionary.R;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(mRun, 4000);
    }

    private Handler mHandler = new Handler();
    private Runnable mRun = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRun);
    }
}