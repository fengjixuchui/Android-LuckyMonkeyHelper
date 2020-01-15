package com.newtoncode.app.luckymonkeyhelper.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * author: 乔晓松
 * email: qiaoxiaosong@fotoable.com
 * time: 2017/1/5 20:12
 * describe: 文件操作
 */
public class FileUtil {

    public static String readAsset(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(fileName);
            byteArrayOutputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[512];
            while ((len = inputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
