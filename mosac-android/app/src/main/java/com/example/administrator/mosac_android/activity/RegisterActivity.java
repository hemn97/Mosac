package com.example.administrator.mosac_android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.mosac_android.event.RegisterEvent;
import com.example.administrator.mosac_android.webservice.WebserviceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button confirm;
    private EditText edit_username;
    private EditText edit_contactnumber;
    private EditText edit_number;
    private EditText edit_password;
    private EditText edit_email;
    private EditText edit_department;
    private WebserviceHelper webserviceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.administrator.mosac_android.R.layout.activity_register);
        webserviceHelper = new WebserviceHelper();
        // 注册EventBus
        EventBus.getDefault().register(this);
        bindViews();
    }

    private void bindViews(){
        confirm = (Button) findViewById(com.example.administrator.mosac_android.R.id.confirm);
        edit_number = (EditText) findViewById(com.example.administrator.mosac_android.R.id.edit_number);
        edit_password = (EditText) findViewById(com.example.administrator.mosac_android.R.id.edit_password);
        edit_username = (EditText) findViewById(com.example.administrator.mosac_android.R.id.edit_username);
        edit_email = (EditText) findViewById(com.example.administrator.mosac_android.R.id.edit_email);
        edit_contactnumber = (EditText) findViewById(com.example.administrator.mosac_android.R.id.edit_contactnumber);
        edit_department = (EditText) findViewById(com.example.administrator.mosac_android.R.id.edit_department);
        // 设置点击监听事件
        confirm.setOnClickListener(this);
    }

    // 点击事件处理逻辑
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.example.administrator.mosac_android.R.id.confirm:
                if(edit_username.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "姓名不能为空", Toast.LENGTH_LONG).show();
                }
                else if(edit_number.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "学号不能为空", Toast.LENGTH_LONG).show();
                }
                else if(edit_password.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                }
                else if(edit_contactnumber.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "联系电话不能为空", Toast.LENGTH_LONG).show();
                }
                else if(edit_email.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_LONG).show();
                }
                else if(edit_department.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "院系不能为空", Toast.LENGTH_LONG).show();
                }
                else {
                    webserviceHelper.registerUser(edit_username.getText().toString(),
                            edit_number.getText().toString(),
                            edit_password.getText().toString(),
                            edit_contactnumber.getText().toString(),
                            edit_email.getText().toString(),
                            edit_department.getText().toString());
                }
                break;
            default:
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterEvent(RegisterEvent event) {
        boolean success = event.getSuccess();
        if(success) {
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
            // 登录验证成功
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            intent.putExtra("number", edit_number.getText().toString());
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(RegisterActivity.this, "注册失败，学号已存在", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
