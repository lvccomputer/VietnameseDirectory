package com.devlv.vietnamesedictionary.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.adapters.ItemClickListener;
import com.devlv.vietnamesedictionary.adapters.words.WordSearchAdapter;
import com.devlv.vietnamesedictionary.common.models.CharacterVN;
import com.devlv.vietnamesedictionary.common.models.Word;
import com.devlv.vietnamesedictionary.ui.activity.BaseActivity;
import com.devlv.vietnamesedictionary.ui.character.CharacterActivity;
import com.devlv.vietnamesedictionary.ui.word.WordActivity;
import com.devlv.vietnamesedictionary.widgets.SquareLinearLayout;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements ItemClickListener<Word>, TextWatcher, Callback<ArrayList<Word>> {
    private static final String TAG = "MainActivity";
    private SquareLinearLayout sqCharacter, sqWord, sqAddWord;

    private RecyclerView rcvResultSearch;
    private WordSearchAdapter wordSearchAdapter;
    private ArrayList<Word> wordArrayList;
    private EditText edtSearch;
    private boolean isLoad = false;
    private ImageView imgCloseSearch;
    private QueryWordTask mTask;

    @Override
    protected int getActivityResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        bindViews();
        actions();
        initRecycler();
        setDataRecycler();
    }

    @Override
    protected void bindViews() {
        sqCharacter = findViewById(R.id.sq_charcter);
        sqWord = findViewById(R.id.sq_word);
        sqAddWord = findViewById(R.id.sq_add_content);
        edtSearch = findViewById(R.id.edt_search);
        imgCloseSearch = findViewById(R.id.img_close_search);
        wordArrayList = new ArrayList<>();
        rcvResultSearch = findViewById(R.id.rcv_result_search);

    }

    private void initRecycler() {
        wordSearchAdapter = new WordSearchAdapter(this, wordArrayList);
        rcvResultSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvResultSearch.setAdapter(wordSearchAdapter);
        wordSearchAdapter.setItemClickListener(this);
    }

    public void setDataRecycler() {
        mTask = new QueryWordTask(this, this);
        mTask.execute();
    }

    @Override
    protected void actions() {
        sqCharacter.setOnClickListener(v -> {
            startActivity(new Intent(this, CharacterActivity.class));
        });
        sqWord.setOnClickListener(v -> {
            startActivity(new Intent(this, WordActivity.class));
        });
        sqAddWord.setOnClickListener(v -> {
            showAddWordFragment();
        });
        edtSearch.addTextChangedListener(this);
        imgCloseSearch.setOnClickListener(v -> {
            edtSearch.setText("");
            rcvResultSearch.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(int position, Word data) {
        hideKeyBoard(this);
        showContentFragment(data);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isLoad) {
            isLoad = true;
            wordSearchAdapter.getFilter(new Callback() {
                @Override
                public void onCallbackResult(Object data) {
                    isLoad = false;
                }

                @Override
                public void onFailure(String error) {

                }
            }).filter(s);

        }
        if (TextUtils.isEmpty(s)) {
            imgCloseSearch.setVisibility(View.GONE);
            rcvResultSearch.setVisibility(View.GONE);
        } else {
            imgCloseSearch.setVisibility(View.VISIBLE);
            rcvResultSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCallbackResult(ArrayList<Word> data) {
        wordArrayList.clear();
        wordArrayList.addAll(data);
        wordSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String error) {

    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnTouchHideKeyBoard(View view) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideKeyBoard(MainActivity.this);
                return false;
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setOnTouchHideKeyBoard(innerView);
            }
        }
    }

    public static void hideKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setOnTouchHideKeyBoard(findViewById(R.id.drawer_layout));
    }

    private void addFragment(@NonNull Fragment fragment,
                             @NonNull String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit)
                .add(R.id.drawer_layout, fragment, tag)
                .commitAllowingStateLoss();

    }

    public void showContentFragment(Word word) {
        if (getSupportFragmentManager().findFragmentByTag(ContentFragment.class.getSimpleName()) == null) {
            ContentFragment fragment = ContentFragment.newInstance(word);
            addFragment(fragment, ContentFragment.class.getSimpleName());
        }
    }

    private void showAddWordFragment() {
        if (getSupportFragmentManager().findFragmentByTag(AddWordFragment.class.getSimpleName()) == null) {
            AddWordFragment fragment = AddWordFragment.newInstance();
            addFragment(fragment, AddWordFragment.class.getSimpleName());
        }
    }

    public void showAddContentWordFragment(CharacterVN characterVN,Callback<String> callback) {
        if (getSupportFragmentManager().findFragmentByTag(AddContentWordFragment.class.getSimpleName()) == null) {
            AddContentWordFragment fragment = AddContentWordFragment.newInstance(characterVN);
            fragment.setCallback(callback);
            addFragment(fragment, AddContentWordFragment.class.getSimpleName());
        }
    }
}