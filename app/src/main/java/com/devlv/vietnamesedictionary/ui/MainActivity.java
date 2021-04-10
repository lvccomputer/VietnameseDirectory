package com.devlv.vietnamesedictionary.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.common.db.DBManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBManager dbManager = new DBManager(this);
        dbManager.opeDB();
        Cursor cursor = dbManager.getCharacterCursor();
        for (int i = 0; i < cursor.getCount(); i++) {
            Log.e(TAG, "onCreate: " + cursor.getInt(0)
                    + " - " + cursor.getString(1));
            cursor.moveToNext();
        }

    }

}