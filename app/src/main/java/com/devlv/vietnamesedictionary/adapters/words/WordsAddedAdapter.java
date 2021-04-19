package com.devlv.vietnamesedictionary.adapters.words;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.ItemClickListener;
import com.devlv.vietnamesedictionary.common.models.Word;
import com.devlv.vietnamesedictionary.databinding.ItemListWordsAddedBinding;

import java.util.ArrayList;

public class WordsAddedAdapter extends RecyclerView.Adapter<WordsAddedAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<Word> wordArrayList;
    private ItemClickListener<Word> itemClickListener;

    public void setItemClickListener(ItemClickListener<Word> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public WordsAddedAdapter(Context context, ArrayList<Word> wordArrayList) {
        this.context = context;
        this.wordArrayList = wordArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListWordsAddedBinding binding
                = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_list_words_added, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.binding.setWord(wordArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return wordArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemListWordsAddedBinding binding;

        public ItemViewHolder(@NonNull ItemListWordsAddedBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.imgMore.setOnClickListener(v -> {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(getAdapterPosition(), wordArrayList.get(getAdapterPosition()),v);
            });
            binding.cvPreview.setOnClickListener(v -> {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(getAdapterPosition(), wordArrayList.get(getAdapterPosition()),null);
            });
        }
    }

}