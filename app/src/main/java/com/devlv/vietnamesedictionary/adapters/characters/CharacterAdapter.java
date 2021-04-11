package com.devlv.vietnamesedictionary.adapters.characters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.ItemClickListener;
import com.devlv.vietnamesedictionary.common.models.CharacterVN;
import com.devlv.vietnamesedictionary.databinding.ItemListCharacterBinding;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ItemViewHolder> {
    /**
     * callback
     */
    private ItemClickListener<CharacterVN> itemClickListener;

    public void setItemClickListener(ItemClickListener<CharacterVN> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private Context mContext;
    private ArrayList<CharacterVN> characterVNArrayList;

    public CharacterAdapter(Context mContext, ArrayList<CharacterVN> characterVNArrayList) {
        this.mContext = mContext;
        this.characterVNArrayList = characterVNArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListCharacterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_list_character, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.itemListCharacterBinding.setCharacter(characterVNArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return characterVNArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemListCharacterBinding itemListCharacterBinding;

        public ItemViewHolder(@NonNull ItemListCharacterBinding itemView) {
            super(itemView.getRoot());
            itemListCharacterBinding = itemView;
            itemListCharacterBinding.cvItem.setOnClickListener(v -> {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(getAdapterPosition(), characterVNArrayList.get(getAdapterPosition()));
            });
        }
    }
}