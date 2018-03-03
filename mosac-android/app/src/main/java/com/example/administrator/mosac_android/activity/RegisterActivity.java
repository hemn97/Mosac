package com.example.administrator.mosac_android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.presenter.UserPresenter;
import com.example.administrator.mosac_android.utils.ToastUtils;
import com.example.administrator.mosac_android.view.UserView;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class RegisterActivity extends BaseActivity implements UserView{
    private Button confirm;
    private EditText edit_username;
    private EditText edit_contactnumber;
    private EditText edit_number;
    private EditText edit_password;
    private EditText edit_email;
    private EditText edit_department;
    private UserPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindViews();
        // 获取Presenter
        userPresenter = new UserPresenter();
        userPresenter.attachView(this);
        setListener();
    }
    private void bindViews(){
        confirm = (Button) findViewById(R.id.confirm);
        edit_number = (EditText) findViewById(R.id.edit_number);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_contactnumber = (EditText) findViewById(R.id.edit_contactnumber);
        edit_department = (EditText) findViewById(R.id.edit_department);
    }
    private void setListener() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    userPresenter.getData("RegisterUser", edit_username.getText().toString(),
                            edit_number.getText().toString(), edit_password.getText().toString(),
                            edit_contactnumber.getText().toString(), edit_email.getText().toString(),
                            edit_department.getText().toString());
                }
            }
        });
    }

    @Override
    public void onOperationSuccess() {
        // 注册成功
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开View引用
        userPresenter.detachView();
    }

}
