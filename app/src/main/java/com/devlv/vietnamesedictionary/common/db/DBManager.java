package com.devlv.vietnamesedictionary.common.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.io.IOException;

public class DBManager {
    private static final String TAG = "DBManager";
    //TODO add SQLiteException
    private Context context;
    private static SQLiteDatabase db;
    private DBHelper mDBHelper;

    public DBManager(Context context) {
        this.context = context;
    }

    /*
     * DB IO
     */
    public void opeDB() throws SQLiteException {
        mDBHelper = new DBHelper(context);
        // Gets the data repository in write mode
        mDBHelper.createDatabase();

        db = mDBHelper.getWritableDatabase();

    }

    public boolean isOpen() {
        return db.isOpen();
    }

    public void closeDB() {
        mDBHelper.close();
    }

    public void beginTransaction() {
        db.beginTransaction();
    }

    public void setTransactionSuccessful() {
        db.setTransactionSuccessful();
    }

    public void endTransaction() {
        db.endTransaction();
    }

    public Cursor getCharacterCursor() {
        Cursor cursor = db.rawQuery("select * from " + DBTable.VN.VN_TABLE_NAME, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    public Cursor getMeaningByIDCharacterCursor(int id) {
        Cursor cursor = db.query(DBTable.VN.VN_TABLE_NAME
                , null
                , DBTable.MEANING.VN_CHART + " = ?"
                , new String[]{String.valueOf(id)}
                , null, null, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }
}
