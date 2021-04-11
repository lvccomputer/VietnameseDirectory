package com.devlv.vietnamesedictionary.viewmodels;

import android.database.Cursor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devlv.vietnamesedictionary.App;
import com.devlv.vietnamesedictionary.common.db.DBManager;
import com.devlv.vietnamesedictionary.common.models.CharacterVN;

import java.util.ArrayList;

public class CharacterViewModel extends ViewModel {
    private MutableLiveData<ArrayList<CharacterVN>> mCharacterLiveData;

    public MutableLiveData<ArrayList<CharacterVN>> getCharacterLiveData() {
        if (mCharacterLiveData == null) {
            mCharacterLiveData = new MutableLiveData<>();
            getCharacter();
        }
        return mCharacterLiveData;
    }
    private void getCharacter(){
        ArrayList<CharacterVN> characterVNArrayList = new ArrayList<>();
        DBManager dbManager = new DBManager(App.getApp());
        dbManager.opeDB();
        Cursor cursor = dbManager.getCharacterCursor();
        for (int index=0;index <cursor.getCount();index++){
            CharacterVN vn = new CharacterVN(cursor.getInt(0),cursor.getString(1));
            characterVNArrayList.add(vn);
            cursor.moveToNext();
        }
        mCharacterLiveData.setValue(characterVNArrayList);
    }
}