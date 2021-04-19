package com.devlv.vietnamesedictionary.adapters.words;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.ItemClickListener;
import com.devlv.vietnamesedictionary.common.models.Word;
import com.devlv.vietnamesedictionary.databinding.ItemListPreviewBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ItemViewHolder> {
    /**
     * callback
     */
    private ItemClickListener<Word> wordItemClickListener;

    public void setWordItemClickListener(ItemClickListener<Word> wordItemClickListener) {
        this.wordItemClickListener = wordItemClickListener;
    }

    private Context mContext;
    private ArrayList<Word> wordArrayList;

    public PreviewAdapter(Context mContext, ArrayList<Word> wordArrayList) {
        this.mContext = mContext;
        this.wordArrayList = wordArrayList;
        sortList();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListPreviewBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_list_preview, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.itemListPreviewBinding.setWord(wordArrayList.get(position));
    }

    private void sortList() {
        Collections.sort(wordArrayList, (o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
    }

    @Override
    public int getItemCount() {
        return wordArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemListPreviewBinding itemListPreviewBinding;

        public ItemViewHolder(@NonNull ItemListPreviewBinding itemView) {
            super(itemView.getRoot());
            itemListPreviewBinding = itemView;
            itemListPreviewBinding.cvPreview.setOnClickListener(v -> {
                if (wordItemClickListener != null)
                    wordItemClickListener.onItemClick(getAdapterPosition(), wordArrayList.get(getAdapterPosition()),v);
            });
        }

    }
}