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
import com.devlv.vietnamesedictionary.Callback;
import com.devlv.vietnamesedictionary.FileUtils;
import com.devlv.vietnamesedictionary.PermissionUtils;
import com.devlv.vietnamesedictionary.R;
import com.devlv.vietnamesedictionary.Utils;
import com.devlv.vietnamesedictionary.common.db.DBManager;
import com.devlv.vietnamesedictionary.common.models.CharacterVN;
import com.devlv.vietnamesedictionary.ui.main.activities.MainActivity;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AddContentWordFragment extends BaseFragment {
    public static final int REQUEST_CODE = 1111;
    private static final String TAG = "AddContentWordFragment";

    private TextView tvCharacterAddWord, tvSave;
    private LinearLayout lnAddPhoto;
    private EditText edtTitle, edtContent;
    private ImageView imgPreview;
    private ImageView imgDeletePhoto;

    /**
     * data
     */
    private Uri uri;
    private CharacterVN characterVN;

    private Callback<String> callback;

    public void setCallback(Callback<String> callback) {
        this.callback = callback;
    }

    public static AddContentWordFragment newInstance(CharacterVN characterVN) {

        Bundle args = new Bundle();
        args.putParcelable("_character", characterVN);
        AddContentWordFragment fragment = new AddContentWordFragment();
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
        if (getArguments() != null) {
            characterVN = getArguments().getParcelable("_character");
        }
        bindViews();
        actions();
        init();

    }

    private void bindViews() {
        tvCharacterAddWord = view.findViewById(R.id.tv_char_add_word);
        lnAddPhoto = view.findViewById(R.id.ln_select_photo);
        edtTitle = view.findViewById(R.id.edt_words);
        edtContent = view.findViewById(R.id.edt_meaning);
        imgPreview = view.findViewById(R.id.img_photo_add_words);
        tvSave = view.findViewById(R.id.tv_save);
        imgDeletePhoto = view.findViewById(R.id.img_delete_photo);
    }

    private void init() {
        tvCharacterAddWord.setText(characterVN.getCharacter());

    }

    private void actions() {
        tvSave.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(edtTitle.getText().toString())
                    && !TextUtils.isEmpty(edtContent.getText().toString())) {
                if (Utils.deAccent(String.valueOf(edtTitle.getText().toString().charAt(0))).toUpperCase().equals(Utils.deAccent(characterVN.getCharacter()))) {
                    String fileName = null;
                    if (uri != null) {
                        File srcFile = new File(FileUtils.getPath(mainActivity, uri));
                        File dstFile = Utils.createPhotoFolder();
                        fileName = srcFile.getName();
                        try {
                            org.apache.commons.io.FileUtils.copyFileToDirectory(srcFile, dstFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    DBManager dbManager = new DBManager(mainActivity);
                    dbManager.opeDB();
                    dbManager.insertMeaning(characterVN.getId(), edtTitle.getText().toString(), edtContent.getText().toString(), fileName, 1);
                    dbManager.closeDB();
                    mainActivity.onBackPressed();
                    mainActivity.onToast("Saved!");
                    if (callback != null) callback.onCallbackResult("success");
                } else {
                    mainActivity.onToast(mainActivity.getResources().getString(R.string.words_title_of_edit) + " " +
                            mainActivity.getResources().getString(R.string.not_equals) + " " + characterVN.getCharacter());
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
                    this.uri = data.getData();
                    imgDeletePhoto.setVisibility(View.VISIBLE);
                    Glide.with(mainActivity).load(data.getData()).into(imgPreview);
                } else {
                    Toast.makeText(getActivity(), "Select image failure!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity.setOnTouchHideKeyBoard(view.findViewById(R.id.fragment_addcontent));
    }
}