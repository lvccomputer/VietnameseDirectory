package com.devlv.vietnamesedictionary;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class Utils {
    private static final String TAG = "Utils";
    public static String uri = "file:///android_asset/photo/";

    public static void copyFileOrDir(Context context, String path) {
        AssetManager assetManager = context.getAssets();
        String[] assets = null;
        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(context, path);
            } else {
                String fullPath = "/data/data/" + context.getPackageName() + "/" + path;
                File dir = new File(fullPath);
                if (!dir.exists())
                    dir.mkdir();
                for (String asset : assets) {
                    copyFileOrDir(context, path + "/" + asset);
                }
            }
            Log.e(TAG, "copyFileOrDir: ");
        } catch (IOException ex) {
            Log.e(TAG, "I/O Exception", ex);
        }
    }

    private static void copyFile(Context context, String filename) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String newFileName = "/data/data/" + context.getPackageName() + "/" + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    //Check database already exist or not
    public static boolean checkPhotoFolder() {
        boolean checkDB = false;
        try {
            String myPath = "/data/data/" + App.getApp().getPackageName() + "/photo/";
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return checkDB;
    }

    public static String getFileNameByUri(Context context, Uri uri) {
        String displayName = "";
        if (uri.getScheme().startsWith("content")) {
            Cursor cursor = context.getContentResolver()
                    .query(uri, null, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(
                            cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Log.e(TAG, "getFileNameByUri: " + displayName);
                    cursor.close();
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        } else if (uri.getScheme().startsWith("file")) {
            try {
                File file = new File(new URI(uri.toString()));
                if (file.exists()) {
                    displayName = file.getName();
                    Log.e(TAG, "getFileNameByUri: " + file.getAbsolutePath());
                }
            } catch (URISyntaxException e) {
                Log.e(TAG, e.toString());
                e.printStackTrace();
            }
        } else {
            File file = new File(uri.getPath());
            if (file.exists()) {
                displayName = file.getName();
            }

        }
        Log.e(TAG, "getFileNameByUri: " + displayName);
        return displayName;
    }

    public static File createPhotoFolder() {
        @SuppressLint("SdCardPath") File newFile = new File(BASE_URI + App.getApp().getPackageName()
                + "/" + BASE_FOLDER + "/");
        if (!newFile.exists()) {
            newFile.mkdir();
        }
        return newFile;
    }

    public static final String BASE_URI = "/data/user/0/";
    public static final String BASE_FOLDER = "photo";

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}