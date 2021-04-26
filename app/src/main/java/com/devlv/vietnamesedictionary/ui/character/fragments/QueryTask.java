package com.devlv.vietnamesedictionary.ui.character.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.common.db.DBManager;
import com.devlv.vietnamesedictionary.common.models.Word;

import java.util.ArrayList;

public class QueryTask extends AsyncTask<Integer, Void, ArrayList<Word>> {
    private static final String TAG = "QueryTask";
    private Callback<ArrayList<Word>> callback;
    private Context mContext;

    public QueryTask(Callback<ArrayList<Word>> callback, Context mContext) {
        this.callback = callback;
        this.mContext = mContext;
    }

    @Override
    protected ArrayList<Word> doInBackground(Integer... integers) {
        return getWords(integers[0]);
    }

    private ArrayList<Word> getWords(int id) {
        ArrayList<Word> wordArrayList = new ArrayList<>();
        DBManager dbManager = new DBManager(mContext);
        dbManager.opeDB();
        Cursor cursor = dbManager.getMeaningByIDCharacterCursor(id);
        for (int i = 0; i < cursor.getCount(); i++) {
            Word word = new Word(cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getString(2)
                    , cursor.getString(3)
                    , cursor.getInt(4)
                    , cursor.getInt(5));
            if (word.getUserCreate()!=1)
                wordArrayList.add(word);
            cursor.moveToNext();
        }
        dbManager.closeDB();
        return wordArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Word> words) {
        super.onPostExecute(words);
        if (words.size() > 0) {
            if (callback != null) callback.onCallbackResult(words);
        } else if (callback != null) callback.onFailure("Empty!");
    }
}