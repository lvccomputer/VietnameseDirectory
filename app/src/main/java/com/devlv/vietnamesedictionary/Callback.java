package com.devlv.vietnamesedictionary;

public interface Callback<T> {
    void onCallbackResult(T data);
    void onFailure(String error);
}