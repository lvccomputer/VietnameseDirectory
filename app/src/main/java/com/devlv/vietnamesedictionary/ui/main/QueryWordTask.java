package com.devlv.vietnamesedictionary.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.common.db.DBManager;
import com.devlv.vietnamesedictionary.common.models.Word;

import java.util.ArrayList;

public class QueryWordTask extends AsyncTask<Void, Void, ArrayList<Word>> {
    private Context mContext;
    private Callback<ArrayList<Word>> callback;

    public QueryWordTask(Context mContext, Callback<ArrayList<Word>> callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    @Override
    protected ArrayList<Word> doInBackground(Void... voids) {
        return getList();
    }

    private ArrayList<Word> getList() {
        ArrayList<Word> wordArrayList = new ArrayList<>();
        DBManager dbManager = new DBManager(mContext);
        dbManager.opeDB();
        Cursor cursor = dbManager.getAllMeaningCursor();
        for (int i = 0; i < cursor.getCount(); i++) {
            Word word = new Word(cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getString(2)
                    , cursor.getString(3)
                    , cursor.getInt(4), cursor.getInt(5));
            if (word.getUserCreate() != 1)
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