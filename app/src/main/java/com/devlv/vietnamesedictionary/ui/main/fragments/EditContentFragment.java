package com.devlv.vietnamesedictionary.ui.main.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.devlv.vietnamesedictionary.App;
import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.FileUtils;
import com.devlv.vietnamesedictionary.PermissionUtils;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.Utils;
import com.devlv.vietnamesedictionary.common.db.DBManager;
import com.devlv.vietnamesedictionary.common.models.Word;
import com.devlv.vietnamesedictionary.ui.main.activities.MainActivity;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.devlv.vietnamesedictionary.Utils.BASE_FOLDER;
import static com.devlv.vietnamesedictionary.Utils.BASE_URI;
import static com.devlv.vietnamesedictionary.ui.main.fragments.AddContentWordFragment.REQUEST_CODE;

public class EditContentFragment extends BaseFragment {
    private static final String TAG = "EditContentFragment";

    private Word word;

    private TextView tvCharacterAddWord, tvSave, tvTitle;
    private LinearLayout lnAddPhoto;
    private EditText edtTitle, edtContent;
    private ImageView imgPreview;
    private ImageView imgDeletePhoto;
    private Uri uri;
    private String path, character;

    private Callback<String> callback;

    public void setCallback(Callback<String> callback) {
        this.callback = callback;
    }

    public static EditContentFragment newInstance(Word word) {

        Bundle args = new Bundle();
        args.putParcelable("_words", word);
        EditContentFragment fragment = new EditContentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_add_content_word;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) word = getArguments().getParcelable("_words");
        Glide.with(this).load(R.drawable.bg_app).into((ImageView) view.findViewById(R.id.img_background));
        bindViews();
        init();
        actions();
    }

    private void bindViews() {
        tvCharacterAddWord = view.findViewById(R.id.tv_char_add_word);
        lnAddPhoto = view.findViewById(R.id.ln_select_photo);
        edtTitle = view.findViewById(R.id.edt_words);
        edtContent = view.findViewById(R.id.edt_meaning);
        imgPreview = view.findViewById(R.id.img_photo_add_words);
        tvSave = view.findViewById(R.id.tv_save);
        imgDeletePhoto = view.findViewById(R.id.img_delete_photo);
        tvTitle = view.findViewById(R.id.tv_title_edit_fragment);
    }

    private void init() {
        //set title
        tvTitle.setText(mainActivity.getResources().getString(R.string.title_edit_word_fragment));
        //get first character
        DBManager dbManager = new DBManager(mainActivity);
        dbManager.opeDB();
        character = dbManager.getCharacterById(word.getIdChar());
        dbManager.closeDB();
        tvCharacterAddWord.setText(character);

        //set content
        edtTitle.setText(word.getTitle());
        edtContent.setText(word.getContent());
        if (word.getPhoto() != null) {
            path = BASE_URI + App.getApp().getPackageName()
                    + "/" + BASE_FOLDER + "/" + word.getPhoto();
            Glide.with(mainActivity).load(path).into(imgPreview);
        }
    }

    private void actions() {
        tvSave.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(edtTitle.getText().toString())
                    && !TextUtils.isEmpty(edtContent.getText().toString())) {
                if (Utils.deAccent(String.valueOf(edtTitle.getText().toString().charAt(0))).toUpperCase().equals(Utils.deAccent(character))) {
                    String fileName = null;
                    if (path == null) {
                        if (uri != null) {
                            File srcFile = new File(FileUtils.getPath(mainActivity, uri));
                            File dstFile = Utils.createPhotoFolder();
                            fileName = srcFile.getName();
                            Log.e(TAG, "actions: " + fileName);
                            try {
                                org.apache.commons.io.FileUtils.copyFileToDirectory(srcFile, dstFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e(TAG, "actions: " + e);
                            }
                        }
                    } else {
                        File file = new File(path);
                        fileName = file.getName();
                    }

                    DBManager dbManager = new DBManager(mainActivity);
                    dbManager.opeDB();
                    dbManager.updateMeaning(word.getId(), edtTitle.getText().toString(), edtContent.getText().toString(), fileName);
                    dbManager.closeDB();
                    mainActivity.onBackPressed();
                    mainActivity.onToast("Updated!");
                    if (callback != null) callback.onCallbackResult("success");
                } else {
                    mainActivity.onToast(mainActivity.getResources().getString(R.string.words_title_of_edit) + " " +
                            mainActivity.getResources().getString(R.string.not_equals) + " " + character);
                }

            } else {
                mainActivity.onToast(mainActivity.getResources().getString(R.string.words_title_of_edit)
                        + " " + mainActivity.getResources().getString(R.string.or) + " " + mainActivity.getResources().getString(R.string.title_add_word_meaning)
                        + " " + mainActivity.getResources().getString(R.string.empty));
            }
        });
        lnAddPhoto.setOnClickListener(v -> {
            if (PermissionUtils.isPermissionStorage(mainActivity)) {
                Utils.createPhotoFolder();
                try {
                    Intent intentImage = new Intent();
                    intentImage.setType("image/*");
                    intentImage.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intentImage, "Select Picture"), REQUEST_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    Log.e(TAG, ex.toString());
                }
            } else PermissionUtils.givePermissionStorage(mainActivity);
        });
        imgDeletePhoto.setOnClickListener(v -> {
            imgPreview.setImageDrawable(null);
            uri = null;
            path = null;
            imgDeletePhoto.setVisibility(View.GONE);
        });
        view.findViewById(R.id.img_back).setOnClickListener(v -> mainActivity.onBackPressed());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {//request from gallery
            if (resultCode == RESULT_OK) {
                if (data != null && data.getData() != null) {
                    path = null;
                    this.uri = data.getData();
                    imgDeletePhoto.setVisibility(View.VISIBLE);
                    Glide.with(mainActivity).load(data.getData()).into(imgPreview);
                } else {
                    Toast.makeText(getActivity(), "Select image failure!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}