package com.devlv.vietnamesedictionary.common.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

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

    public String getCharacterById(int id) {
        Cursor cursor = db.rawQuery("select " + DBTable.VN.CHARACTER + " from " + DBTable.VN.VN_TABLE_NAME + " where " + DBTable.VN.ID + " = '" + id + "'", null);
        String character = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                character = cursor.getString(0);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return character;

    }

    public Cursor getMeaningByIDCharacterCursor(int id) {
        Cursor cursor = db.query(DBTable.MEANING.MEANING_TABLE_NAME
                , null
                , DBTable.MEANING.VN_CHART + " = ?"
                , new String[]{String.valueOf(id)}
                , null, null, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    public Cursor getMoreCursor(int limit) {
        Cursor cursor = db.rawQuery("select * from " + DBTable.MEANING.MEANING_TABLE_NAME + " limit " + limit, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    public Cursor getAllMeaningCursor() {
        Cursor cursor = db.rawQuery("select * from " + DBTable.MEANING.MEANING_TABLE_NAME, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    public Cursor getContentUserCreate() {
        Cursor cursor = db.rawQuery("select * from "
                + DBTable.MEANING.MEANING_TABLE_NAME + " where " + DBTable.MEANING.USER_CREATE + " = 1", null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    private ContentValues meaningValue(int idChar, String title, String content, String photo, int add) {
        ContentValues values = new ContentValues();
        values.put(DBTable.MEANING.TITLE, title);
        values.put(DBTable.MEANING.CONTENT, content);
        values.put(DBTable.MEANING.PHOTO, photo);
        values.put(DBTable.MEANING.USER_CREATE, add);
        values.put(DBTable.MEANING.VN_CHART, idChar);
        return values;
    }

    public long insertMeaning(int idChar, String title, String content, String photo, int add) {
        return db.insert(DBTable.MEANING.MEANING_TABLE_NAME, null, meaningValue(idChar, title, content, photo, add));
    }

    public long updateMeaning(int id, String title, String content, String photo) {
        SQLiteStatement statement =
                db.compileStatement("update " + DBTable.MEANING.MEANING_TABLE_NAME +
                        " set " + DBTable.MEANING.TITLE + " = '" + title + "'"
                        + ", " + DBTable.MEANING.CONTENT + " = '" + content + "'"
                        + ", " + DBTable.MEANING.PHOTO + " = '" + photo + "' where " + DBTable.MEANING.ID + " = '" + id + "'");
        return statement.executeUpdateDelete();
    }

    public long deleteMeaning(int id) {
        return db.delete(DBTable.MEANING.MEANING_TABLE_NAME, DBTable.MEANING.ID + " = ?", new String[]{String.valueOf(id)});
    }
}
