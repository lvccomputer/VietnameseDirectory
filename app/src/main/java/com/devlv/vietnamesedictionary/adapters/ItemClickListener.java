package com.devlv.vietnamesedictionary.adapters;

import android.view.View;

public interface ItemClickListener<T> {
    void onItemClick(int position, T data, View view);
}