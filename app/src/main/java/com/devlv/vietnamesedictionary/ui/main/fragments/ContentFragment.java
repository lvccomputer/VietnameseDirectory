package com.devlv.vietnamesedictionary.ui.main.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.devlv.vietnamesedictionary.App;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.Utils;
import com.devlv.vietnamesedictionary.common.models.Word;

import static com.devlv.vietnamesedictionary.Utils.BASE_FOLDER;
import static com.devlv.vietnamesedictionary.Utils.BASE_URI;

public class ContentFragment extends BaseFragment {
    private TextView tvTitle, tvContent;
    private ImageView imgPreview;
    private Word word;

    public static ContentFragment newInstance(Word word) {

        Bundle args = new Bundle();
        args.putParcelable("_word", word);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_content;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(this).load(R.drawable.bg_app).into((ImageView) view.findViewById(R.id.img_background));
        if (getArguments() != null) {
            word = getArguments().getParcelable("_word");
        }
        bindViews();
        initData();
    }

    private void bindViews() {
        tvTitle = view.findViewById(R.id.tv_title_meaning);
        tvContent = view.findViewById(R.id.tv_content_meaning);
        imgPreview = view.findViewById(R.id.img_preview);
        view.findViewById(R.id.img_back).setOnClickListener(v -> mainActivity.onBackPressed());
    }

    private void initData() {
        tvTitle.setText(word.getTitle());
        tvContent.setText(word.getContent());
        if (word.getPhoto() != null) {
            if (word.getUserCreate() != 1)
                Glide.with(mainActivity).load(Utils.uri + word.getPhoto().toLowerCase() + ".png").into(imgPreview);
            else {
                String path = BASE_URI + App.getApp().getPackageName()
                        + "/" + BASE_FOLDER + "/" + word.getPhoto();
                Glide.with(mainActivity).load(path).into(imgPreview);
            }
        }
    }

}