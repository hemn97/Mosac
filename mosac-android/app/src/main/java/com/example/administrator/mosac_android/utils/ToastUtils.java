package com.example.administrator.mosac_android.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class ToastUtils {
    private static Toast mToast;
    /**
     * 显示Toast
     */
    public static void showToast(Context context, CharSequence text, int duration) {
        if(mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
    public static void initToast(Context context) {
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }
}
