package com.devlv.vietnamesedictionary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class PermissionUtils {
    public static final int REQUEST_CODE_PERMISSION = 9999;

    public static String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    public static boolean isPermissionStorage(Context context) {
        return ContextCompat.checkSelfPermission(context, permissions[0]) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, permissions[1]) == PackageManager.PERMISSION_GRANTED;
    }


    public static void givePermissionStorage(Activity activity) {
        requestPermissions(activity, new String[]{permissions[0], permissions[1]}, REQUEST_CODE_PERMISSION);
    }
}