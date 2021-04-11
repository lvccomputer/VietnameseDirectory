package com.devlv.vietnamesedictionary.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.ui.activity.words.WordActivity;
import com.devlv.vietnamesedictionary.widgets.SquareLinearLayout;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private SquareLinearLayout sqCharacter, sqWord;

    @Override
    protected int getActivityResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        bindViews();
        actions();

    }

    @Override
    protected void bindViews() {
        sqCharacter = findViewById(R.id.sq_charcter);
        sqWord = findViewById(R.id.sq_word);
    }

    @Override
    protected void actions() {
        sqCharacter.setOnClickListener(v -> {
            startActivity(new Intent(this, CharacterActivity.class));
        });
        sqWord.setOnClickListener(v -> {
            startActivity(new Intent(this, WordActivity.class));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}