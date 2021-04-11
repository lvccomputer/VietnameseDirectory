package com.devlv.vietnamesedictionary.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.ItemClickListener;
import com.devlv.vietnamesedictionary.adapters.characters.CharacterAdapter;
import com.devlv.vietnamesedictionary.common.models.CharacterVN;
import com.devlv.vietnamesedictionary.ui.fragments.PreviewFragment;
import com.devlv.vietnamesedictionary.viewmodels.CharacterViewModel;

import java.util.ArrayList;

public class CharacterActivity extends BaseActivity implements ItemClickListener<CharacterVN> {
    private static final String TAG = "CharacterActivity";
    private RecyclerView rcvCharacter;
    private CharacterAdapter mCharacterAdapter;
    private ArrayList<CharacterVN> characterVNArrayList;

    /**
     * ViewModel
     *
     * @return list character
     */
    private CharacterViewModel mCharacterVM;

    @Override
    protected int getActivityResource() {
        return R.layout.activity_character;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initVM();
        bindViews();
        initRecycler();
        setRecycler();
        actions();
    }

    private void initVM() {
        mCharacterVM = ViewModelProviders.of(this).get(CharacterViewModel.class);
    }

    /**
     * initialization recyclerview
     */
    private void initRecycler() {
        mCharacterAdapter = new CharacterAdapter(this, characterVNArrayList);
        rcvCharacter.setLayoutManager(new GridLayoutManager(this, 6, RecyclerView.VERTICAL, false));
        rcvCharacter.setAdapter(mCharacterAdapter);
        mCharacterAdapter.setItemClickListener(this);
    }

    /**
     * set data for recycler
     */
    private void setRecycler() {
        mCharacterVM.getCharacterLiveData().observe(this, characterVNS -> {
            characterVNArrayList.clear();
            characterVNArrayList.addAll(characterVNS);
            mCharacterAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void bindViews() {
        characterVNArrayList = new ArrayList<>();
        rcvCharacter = findViewById(R.id.rcv_character);

    }

    @Override
    protected void actions() {
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(int position, CharacterVN data) {
        onToast(position, data.toString());
        showPreviewFragment(data.getId(), data.getCharacter());
    }

    private void addFragment(@NonNull Fragment fragment,
                             @NonNull String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit)
                .add(R.id.character_container, fragment, tag)
                .commitAllowingStateLoss();

    }

    public void showPreviewFragment(int id, String character) {
        if (getSupportFragmentManager().findFragmentByTag(PreviewFragment.class.getSimpleName()) == null) {
            PreviewFragment fragment = PreviewFragment.newInstance(id, character);
            addFragment(fragment, PreviewFragment.class.getSimpleName());
        }
    }

}