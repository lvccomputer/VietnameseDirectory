package com.devlv.vietnamesedictionary.adapters.words;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devlv.vietnamesedictionary.common.models.Word;
import com.devlv.vietnamesedictionary.databinding.ItemListPreviewBinding;

import java.util.ArrayList;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ItemViewHolder> {
    private Context mContext;
    private ArrayList<Word> wordArrayList;

    public WordsAdapter(Context mContext, ArrayList<Word> wordArrayList) {
        this.mContext = mContext;
        this.wordArrayList = wordArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}