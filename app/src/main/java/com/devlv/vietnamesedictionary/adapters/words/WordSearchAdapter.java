package com.devlv.vietnamesedictionary.adapters.words;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.ItemClickListener;
import com.devlv.vietnamesedictionary.common.models.Word;
import com.devlv.vietnamesedictionary.databinding.ItemListPreviewBinding;

import java.util.ArrayList;

public class WordSearchAdapter extends RecyclerView.Adapter<WordSearchAdapter.ItemViewHolder> {
    private static final String TAG = "WordSearchAdapter";
    private Context mContext;
    private ArrayList<Word> wordArrayList;
    private ArrayList<Word> searchList;
    private ItemClickListener<Word> itemClickListener;

    public void setItemClickListener(ItemClickListener<Word> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public WordSearchAdapter(Context mContext, ArrayList<Word> wordArrayList) {
        this.mContext = mContext;
        this.wordArrayList = wordArrayList;
    }

    public Filter getFilter(Callback<ArrayList<Word>> callback) {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    searchList = new ArrayList<>();
                } else {
                    ArrayList<Word> words = new ArrayList<>();
                    ArrayList<Word> words1 = new ArrayList<>();
                    for (Word word : wordArrayList) {
                        if (word.getTitle()!=null){
                            if (word.getTitle().toLowerCase().startsWith(charString.toLowerCase())) {
                                words1.add(word);
                            }
                        }

                    }
                    words.addAll(words1);

                    searchList = words;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = searchList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchList = (ArrayList<Word>) results.values;
                notifyDataSetChanged();
                if (callback != null) callback.onCallbackResult(null);
            }
        };
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListPreviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_list_preview, parent, false);

        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.binding.setWord(searchList.get(position));
    }

    @Override
    public int getItemCount() {
        if (searchList != null)
            return searchList.size();
        else return 0;
    }

    public void addFilter(ArrayList<Word> wordArrayList) {
        this.searchList.addAll(wordArrayList);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemListPreviewBinding binding;

        public ItemViewHolder(@NonNull ItemListPreviewBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            binding.cvPreview.setOnClickListener(v -> {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(getAdapterPosition(), searchList.get(getAdapterPosition()),v);
            });
        }
    }
}