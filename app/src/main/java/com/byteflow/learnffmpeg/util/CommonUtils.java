package com.byteflow.learnffmpeg.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CommonUtils {
    private static final String TAG = "CommonUtils";

    public static void copyAssetsDirToSDCard(Context context, String assetsDirName, String sdCardPath) {
        Log.i(TAG, "copyAssetsDirToSDCard() called with: context = [" + context + "], assetsDirName = [" + assetsDirName + "], sdCardPath = [" + sdCardPath + "]");
        try {
            String[] list = context.getAssets().list(assetsDirName);
            if (list.length == 0) {
                InputStream inputStream = context.getAssets().open(assetsDirName);
                byte[] mByte = new byte[1024];
                int bt = 0;
                // String filePath = sdCardPath + File.separator + assetsDirName.substring(assetsDirName.lastIndexOf('/'));
                String filePath = sdCardPath + assetsDirName.substring(assetsDirName.lastIndexOf('/'));
                Log.d(TAG, "copyAssetsDirToSDCard: filePath : " + filePath);
                File file = new File(filePath);
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    return;
                }
                FileOutputStream fos = new FileOutputStream(file);
                while ((bt = inputStream.read(mByte)) != -1) {
                    fos.write(mByte, 0, bt);
                }
                fos.flush();
                inputStream.close();
                fos.close();
            } else {
                String subDirName = assetsDirName;
                if (assetsDirName.contains("/")) {
                    subDirName = assetsDirName.substring(assetsDirName.lastIndexOf('/') + 1);
                }
                Log.d(TAG, "copyAssetsDirToSDCard: sdCardPath : " + sdCardPath);
                File file = new File(sdCardPath);

                if (!file.exists()) {
                    file.mkdirs();
                }

                for (String s : list) {
                    copyAssetsDirToSDCard(context, "byteflow" + File.separator + s, sdCardPath);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "copyAssetsDirToSDCard ERROR :  " + e.getMessage());
            e.printStackTrace();
        }
    }
}
