package com.devlv.vietnamesedictionary;

import android.app.Application;

import com.devlv.vietnamesedictionary.common.db.DBManager;

public class App extends Application {
    private static App app;

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        createDB();
    }

    private void createDB() {
        DBManager dbManager = new DBManager(this);
        dbManager.opeDB();
        dbManager.closeDB();
    }
}