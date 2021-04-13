package com.devlv.vietnamesedictionary.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.ItemClickListener;
import com.devlv.vietnamesedictionary.adapters.characters.CharacterAdapter;
import com.devlv.vietnamesedictionary.common.models.CharacterVN;
import com.devlv.vietnamesedictionary.viewmodels.CharacterViewModel;

import java.util.ArrayList;

public class AddWordFragment extends Fragment implements ItemClickListener<CharacterVN>, Callback<String> {
    private View view;
    private MainActivity mainActivity;

    private RecyclerView rcvCharacter;
    private CharacterAdapter mCharacterAdapter;
    private ArrayList<CharacterVN> characterVNArrayList;
    /**
     * ViewModel
     *
     * @return list character
     */
    private CharacterViewModel mCharacterVM;

    public static AddWordFragment newInstance() {

        Bundle args = new Bundle();

        AddWordFragment fragment = new AddWordFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);
        mainActivity = (MainActivity) getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCharacterVM = ViewModelProviders.of(requireActivity()).get(CharacterViewModel.class);
        Glide.with(this).load(R.drawable.bg_app).into((ImageView) view.findViewById(R.id.img_background));
        bindViews();
        initRecycler();
        setRecycler();
    }

    private void bindViews() {
        characterVNArrayList = new ArrayList<>();
        rcvCharacter = view.findViewById(R.id.rcv_character);
        view.findViewById(R.id.img_back).setOnClickListener(v -> mainActivity.onBackPressed());
    }

    /**
     * initialization recyclerview
     */
    private void initRecycler() {
        mCharacterAdapter = new CharacterAdapter(mainActivity, characterVNArrayList);
        rcvCharacter.setLayoutManager(new GridLayoutManager(mainActivity, 6, RecyclerView.VERTICAL, false));
        rcvCharacter.setAdapter(mCharacterAdapter);
        mCharacterAdapter.setItemClickListener(this);
    }

    /**
     * set data for recycler
     */
    private void setRecycler() {
        mCharacterVM.getCharacterLiveData().observe(getViewLifecycleOwner(), characterVNS -> {
            characterVNArrayList.clear();
            characterVNArrayList.addAll(characterVNS);
            mCharacterAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onItemClick(int position, CharacterVN data) {
        mainActivity.showAddContentWordFragment(data,this);
    }

    @Override
    public void onCallbackResult(String data) {
        mainActivity.setDataRecycler();
    }

    @Override
    public void onFailure(String error) {

    }
}