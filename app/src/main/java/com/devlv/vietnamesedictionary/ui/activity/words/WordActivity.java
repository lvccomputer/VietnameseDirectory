package com.devlv.vietnamesedictionary.ui.activity.words;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.words.PreviewAdapter;
import com.devlv.vietnamesedictionary.common.models.Word;
import com.devlv.vietnamesedictionary.ui.activity.BaseActivity;
import com.devlv.vietnamesedictionary.widgets.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

public class WordActivity extends BaseActivity {
    private static final String TAG = "WordActivity";
    private int limit = 20;

    private ProgressBar progressBar;
    private RecyclerView rcvWordPreview;
    private PreviewAdapter mPreviewAdapter;
    private ArrayList<Word> wordArrayList;
    private LoadMoreTask mTask;

    private EndlessRecyclerViewScrollListener mListener;

    @Override
    protected int getActivityResource() {
        return R.layout.activity_word;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        onToast(WordActivity.class.getSimpleName());
        bindViews();
        actions();
        initRecycler();
        initTask();
        loadMore(limit);
    }

    @Override
    protected void bindViews() {
        rcvWordPreview = findViewById(R.id.rcv_word_preview);
        wordArrayList = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initRecycler() {
        mPreviewAdapter = new PreviewAdapter(this, wordArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvWordPreview.setLayoutManager(linearLayoutManager);
        rcvWordPreview.setAdapter(mPreviewAdapter);
        mListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.e(TAG, "onLoadMore: ");
                progressBar.setVisibility(View.VISIBLE);
                mTask = new LoadMoreTask(new Callback<ArrayList<Word>>() {
                    @Override
                    public void onCallbackResult(ArrayList<Word> data) {
                        Log.e(TAG, "onCallbackResult: "+data.size());
                        progressBar.setVisibility(View.INVISIBLE);
                        wordArrayList.clear();
                        wordArrayList.addAll(data);
                        mPreviewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                }, WordActivity.this);
                limit += 20;
                mTask.execute(limit);
                Log.e(TAG, "onLoadMore: "+limit);
            }
        };

        rcvWordPreview.addOnScrollListener(mListener);
    }

    private void initTask() {
        mTask = new LoadMoreTask(new Callback<ArrayList<Word>>() {
            @Override
            public void onCallbackResult(ArrayList<Word> data) {
                progressBar.setVisibility(View.INVISIBLE);
                wordArrayList.clear();
                wordArrayList.addAll(data);
                mPreviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {

            }
        }, this);
    }

    private void loadMore(int limit) {
        mTask.execute(limit);

    }

    @Override
    protected void actions() {

    }
}