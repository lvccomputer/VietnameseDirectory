package com.devlv.vietnamesedictionary.ui.character.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.ItemClickListener;
import com.devlv.vietnamesedictionary.adapters.words.PreviewAdapter;
import com.devlv.vietnamesedictionary.common.models.Word;
import com.devlv.vietnamesedictionary.ui.character.CharacterActivity;
import com.devlv.vietnamesedictionary.ui.IToast;

import java.util.ArrayList;

public class PreviewFragment extends Fragment implements Callback<ArrayList<Word>>, IToast, ItemClickListener<Word> {

    private View view;
    /**
     * UI
     */
    private TextView tvTitle;
    private ProgressBar progressBar;
    private RecyclerView rcvList;
    private PreviewAdapter mPreviewAdapter;
    private ArrayList<Word> wordArrayList;

    private CharacterActivity activity;
    private QueryTask mTask;

    /**
     * params
     */
    private int id;
    private String character;

    public static PreviewFragment newInstance(int id, String character) {
        Bundle args = new Bundle();
        args.putInt("_id", id);
        args.putString("_character", character);
        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_preview, container, false);
        activity = (CharacterActivity) getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(this).load(R.drawable.bg_app).into((ImageView) view.findViewById(R.id.img_background));
        if (getArguments() != null) {
            id = getArguments().getInt("_id");
            character = getArguments().getString("_character");
        }
        bindViews();
        initTitle();
        initRecycler();
        initTask();
    }

    private void bindViews() {
        wordArrayList = new ArrayList<>();
        rcvList = view.findViewById(R.id.rcv_preview);
        progressBar = view.findViewById(R.id.progressBar);
        tvTitle = view.findViewById(R.id.tv_title_preview);
        view.findViewById(R.id.img_back).setOnClickListener(v -> activity.onBackPressed());
    }

    @SuppressLint("SetTextI18n")
    private void initTitle() {
        tvTitle.setText(activity.getResources().getString(R.string.title_fragment_preview) + " " + character);
    }

    private void initRecycler() {
        mPreviewAdapter = new PreviewAdapter(activity, wordArrayList);
        rcvList.setLayoutManager(new LinearLayoutManager(activity));
        rcvList.setAdapter(mPreviewAdapter);
        mPreviewAdapter.setWordItemClickListener(this);
    }

    private void initTask() {
        mTask = new QueryTask(this, activity);
        mTask.execute(id);
    }

    @Override
    public void onCallbackResult(ArrayList<Word> data) {
        progressBar.setVisibility(View.GONE);
        wordArrayList.clear();
        wordArrayList.addAll(data);
        mPreviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String error) {
        onToast(error);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTask.cancel(true);
    }

    @Override
    public void onItemClick(int position, Word data,View v) {
        activity.showContentFragment(data);
    }
}