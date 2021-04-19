package com.devlv.vietnamesedictionary.ui.main.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.ItemClickListener;
import com.devlv.vietnamesedictionary.adapters.words.WordsAddedAdapter;
import com.devlv.vietnamesedictionary.common.db.DBManager;
import com.devlv.vietnamesedictionary.common.models.Word;
import com.devlv.vietnamesedictionary.ui.main.WordsAddedTask;
import com.devlv.vietnamesedictionary.widgets.quickactions.ActionItem;
import com.devlv.vietnamesedictionary.widgets.quickactions.QuickAction;

import java.util.ArrayList;

public class WordsAddedFragment extends BaseFragment implements Callback<ArrayList<Word>>, ItemClickListener<Word>, QuickAction.OnActionItemClickListener {

    private RecyclerView rcvWordsAdded;
    private ArrayList<Word> wordArrayList;
    private WordsAddedAdapter wordsAddedAdapter;
    private WordsAddedTask mTask;
    private QuickAction mQuickAction;

    public static WordsAddedFragment newInstance() {

        Bundle args = new Bundle();

        WordsAddedFragment fragment = new WordsAddedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_words_added;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(this).load(R.drawable.bg_app).into((ImageView) view.findViewById(R.id.img_background));
        bindViews();
        initRecycler();
        setRecycler();
        initMoreAction();
    }

    private void bindViews() {
        rcvWordsAdded = view.findViewById(R.id.rcv_words_added);
        wordArrayList = new ArrayList<>();
        view.findViewById(R.id.img_back).setOnClickListener(v -> mainActivity.onBackPressed());
    }

    private void initRecycler() {
        wordsAddedAdapter = new WordsAddedAdapter(mainActivity, wordArrayList);
        rcvWordsAdded.setLayoutManager(new LinearLayoutManager(mainActivity));
        rcvWordsAdded.setAdapter(wordsAddedAdapter);
        wordsAddedAdapter.setItemClickListener(this);
    }

    private void setRecycler() {
        mTask = new WordsAddedTask(mainActivity, this);
        mTask.execute();
    }

    private void initMoreAction() {
        ActionItem delItem, editItem;
        editItem = new ActionItem(0, getResources().getString(R.string.option_edit), mainActivity.getDrawable(R.drawable.ic_edit_diary));
        delItem = new ActionItem(1, getResources().getString(R.string.option_delete), mainActivity.getDrawable(R.drawable.ic_delete_diary));
        mQuickAction = new QuickAction(mainActivity, QuickAction.HORIZONTAL);
        mQuickAction.addActionItem(editItem);
        mQuickAction.addActionItem(delItem);
        mQuickAction.setOnActionItemClickListener(this);

    }

    @Override
    public void onCallbackResult(ArrayList<Word> data) {
        wordArrayList.clear();
        wordArrayList.addAll(data);
        wordsAddedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String error) {

    }

    @Override
    public void onItemClick(int position, Word data, View view) {
        if (view!=null){
            mQuickAction.show(view, data, position);
        }else {
            mainActivity.showContentFragment(data);
        }
    }

    @Override
    public void onItemClick(int actionId, Word word, int position) {
        switch (actionId) {
            case 0:
                mainActivity.showEditFragment(word, new Callback<String>() {
                    @Override
                    public void onCallbackResult(String data) {
                        setRecycler();
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
                break;
            case 1:
                mainActivity.onToast("Delete!");
                deleteWords(word, position);
                break;
        }
    }

    private void deleteWords(Word word, int position) {
        DBManager dbManager = new DBManager(mainActivity);
        dbManager.opeDB();
        dbManager.deleteMeaning(word.getId());
        dbManager.closeDB();
        wordArrayList.remove(word);
        wordsAddedAdapter.notifyItemRemoved(position);

    }
}