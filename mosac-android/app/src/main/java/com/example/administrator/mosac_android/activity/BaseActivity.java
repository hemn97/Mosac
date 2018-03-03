package com.example.administrator.mosac_android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.mosac_android.utils.ToastUtils;
import com.example.administrator.mosac_android.view.BaseView;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class BaseActivity extends Activity implements BaseView {
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
    }
    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }
    @Override
    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }
    @Override
    public void showErr() {
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
    }
    @Override
    public Context getContext() {
        return BaseActivity.this;
    }
}
