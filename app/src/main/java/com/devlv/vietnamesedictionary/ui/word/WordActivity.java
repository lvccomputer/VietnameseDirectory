package com.devlv.vietnamesedictionary.ui.word;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.ItemClickListener;
import com.devlv.vietnamesedictionary.adapters.words.PreviewAdapter;
import com.devlv.vietnamesedictionary.common.models.Word;
import com.devlv.vietnamesedictionary.ui.activity.BaseActivity;
import com.devlv.vietnamesedictionary.ui.main.ContentFragment;
import com.devlv.vietnamesedictionary.widgets.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

public class WordActivity extends BaseActivity implements ItemClickListener<Word> {
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
        Glide.with(this).load(R.drawable.bg_app).into((ImageView) findViewById(R.id.img_background));
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
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
    }

    private void initRecycler() {
        mPreviewAdapter = new PreviewAdapter(this, wordArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvWordPreview.setLayoutManager(linearLayoutManager);
        rcvWordPreview.setAdapter(mPreviewAdapter);
        mListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                progressBar.setVisibility(View.VISIBLE);
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
                }, WordActivity.this);
                limit += 20;
                mTask.execute(limit);
            }
        };
        rcvWordPreview.addOnScrollListener(mListener);
        mPreviewAdapter.setWordItemClickListener(this);
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
    private void addFragment(@NonNull Fragment fragment,
                             @NonNull String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit)
                .add(R.id.word_container, fragment, tag)
                .commitAllowingStateLoss();

    }

    public void showContentFragment(Word word) {
        if (getSupportFragmentManager().findFragmentByTag(ContentWordFragment.class.getSimpleName()) == null) {
            ContentWordFragment fragment = ContentWordFragment.newInstance(word);
            addFragment(fragment, ContentFragment.class.getSimpleName());
        }
    }

    @Override
    public void onItemClick(int position, Word data) {
        showContentFragment(data);
    }
}