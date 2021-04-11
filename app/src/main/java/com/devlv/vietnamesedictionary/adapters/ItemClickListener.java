package com.devlv.vietnamesedictionary.adapters;

public interface ItemClickListener<T> {
    void onItemClick(int position, T data);
}