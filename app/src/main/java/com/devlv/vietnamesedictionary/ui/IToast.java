package com.devlv.vietnamesedictionary.ui;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public interface IToast<T> {
    default void onToast(T... object) {
        if (this instanceof AppCompatActivity) {
            Toast.makeText((Context) this, "" + Arrays.toString(object), Toast.LENGTH_SHORT).show();
        }
    }
}
