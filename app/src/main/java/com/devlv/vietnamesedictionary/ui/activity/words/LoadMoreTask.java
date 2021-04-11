package com.devlv.vietnamesedictionary.ui.activity.words;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.common.db.DBManager;
import com.devlv.vietnamesedictionary.common.models.Word;

import java.util.ArrayList;

public class LoadMoreTask extends AsyncTask<Integer, Void, ArrayList<Word>> {
    private Callback<ArrayList<Word>> callback;
    private Context mContext;

    public LoadMoreTask(Callback<ArrayList<Word>> callback, Context mContext) {
        this.callback = callback;
        this.mContext = mContext;
    }

    @Override
    protected ArrayList<Word> doInBackground(Integer... integers) {
        return getList(integers[0]);
    }

    private ArrayList<Word> getList(int limit) {
        ArrayList<Word> wordArrayList = new ArrayList<>();

        DBManager dbManager = new DBManager(mContext);
        dbManager.opeDB();
        Cursor cursor = dbManager.getMoreCursor(limit);
        for (int i = 0; i < cursor.getCount(); i++) {
            Word word = new Word(cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getString(2)
                    , cursor.getString(3)
                    , cursor.getInt(4));
            wordArrayList.add(word);
            cursor.moveToNext();
        }
        dbManager.closeDB();
        return wordArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Word> words) {
        super.onPostExecute(words);
        if (callback != null) callback.onCallbackResult(words);
    }
}