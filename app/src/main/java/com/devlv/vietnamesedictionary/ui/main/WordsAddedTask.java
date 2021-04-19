package com.devlv.vietnamesedictionary.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.common.db.DBManager;
import com.devlv.vietnamesedictionary.common.models.Word;

import java.util.ArrayList;

public class WordsAddedTask extends AsyncTask<Void, Void, ArrayList<Word>> {
    private Context context;
    private Callback<ArrayList<Word>> callback;

    public WordsAddedTask(Context context, Callback<ArrayList<Word>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected ArrayList<Word> doInBackground(Void... voids) {
        return getList();
    }

    @Override
    protected void onPostExecute(ArrayList<Word> wordArrayList) {
        super.onPostExecute(wordArrayList);
        if (callback != null) callback.onCallbackResult(wordArrayList);
    }
    private ArrayList<Word> getList() {
        ArrayList<Word> wordArrayList = new ArrayList<>();
        DBManager dbManager = new DBManager(context);
        dbManager.opeDB();
        Cursor cursor = dbManager.getContentUserCreate();
        for (int i = 0; i < cursor.getCount(); i++) {
            Word word = new Word(cursor.getInt(0)
                    , cursor.getString(1)
                    , cursor.getString(2)
                    , cursor.getString(3)
                    , cursor.getInt(4), cursor.getInt(5));
            wordArrayList.add(word);
            cursor.moveToNext();
        }
        dbManager.closeDB();
        return wordArrayList;
    }

}