package com.example.administrator.mosac_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mosac_android.event.FindPasswordEvent;
import com.example.administrator.mosac_android.event.VerifyEvent;
import com.example.administrator.mosac_android.webservice.WebserviceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private Button confirm;
    private EditText edit_number;
    private EditText edit_password;
    private ImageView del_number;
    private ImageView del_password;
    private TextView register;
    private TextView forget;
    private WebserviceHelper webserviceHelper;
    private CheckBox cbIsRememberPass;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.administrator.mosac_android.R.layout.activity_login);
        webserviceHelper = new WebserviceHelper();
        // 绑定UI控件
        bindViews();
        // 注册EventBus
        cbIsRememberPass=(CheckBox) findViewById(com.example.administrator.mosac_android.R.id.cbIsRememberPass);
        sharedPreferences=getSharedPreferences("rememberpassword", Context.MODE_PRIVATE);
        boolean isRemember= sharedPreferences.getBoolean("rememberpassword",false);
        if(isRemember)
        {
            String name=sharedPreferences.getString("name","");
            String password=sharedPreferences.getString("password","");
            edit_number.setText(name);
            edit_password.setText(password);
            cbIsRememberPass.setChecked(true);
        }
        EventBus.getDefault().register(this);
        // 文本改变监听器
        edit_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 监听如果输入串长度大于0那么就显示clear按钮
                if (s.length() > 0) {
                    del_number.setVisibility(View.VISIBLE);
                } else {
                    del_number.setVisibility(View.INVISIBLE);
                }
            }
        });
        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 监听如果输入串长度大于0那么就显示clear按钮。
                if (s.length() > 0) {
                    del_password.setVisibility(View.VISIBLE);
                } else {
                    del_password.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void bindViews(){
        confirm = (Button) findViewById(com.example.administrator.mosac_android.R.id.confirm);
        edit_number = (EditText) findViewById(com.example.administrator.mosac_android.R.id.edit_number);
        edit_password = (EditText) findViewById(com.example.administrator.mosac_android.R.id.edit_password);
        del_number = (ImageView) findViewById(com.example.administrator.mosac_android.R.id.del_number);
        del_password = (ImageView) findViewById(com.example.administrator.mosac_android.R.id.del_password);
        register = (TextView) findViewById(com.example.administrator.mosac_android.R.id.register);
        forget = (TextView) findViewById(com.example.administrator.mosac_android.R.id.forget);
        // 设置点击监听事件
        confirm.setOnClickListener(this);
        register.setOnClickListener(this);
        forget.setOnClickListener(this);
        del_number.setOnClickListener(this);
        del_password.setOnClickListener(this);
    }

    // 点击事件处理逻辑
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.example.administrator.mosac_android.R.id.confirm:
                if(edit_number.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "学号不能为空", Toast.LENGTH_LONG).show();
                }
                else if(edit_password.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                }
                else {
                    webserviceHelper.verifyUser(edit_number.getText().toString(), edit_password.getText().toString());
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    if(cbIsRememberPass.isChecked())
                    {
                        editor.putBoolean("rememberpassword",true);
                        editor.putString("name",edit_number.getText().toString());
                        editor.putString("password",edit_password.getText().toString());
                    }
                    else
                    {
                        editor.clear();
                    }
                    editor.commit();
                }
                break;
            case com.example.administrator.mosac_android.R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case com.example.administrator.mosac_android.R.id.forget:
                LayoutInflater factor = LayoutInflater.from(LoginActivity.this);
                View view_in = factor.inflate(com.example.administrator.mosac_android.R.layout.findpassword_dialog, null);
                final EditText number = view_in.findViewById(com.example.administrator.mosac_android.R.id.edit_number);
                final EditText username = view_in.findViewById(com.example.administrator.mosac_android.R.id.edit_username);
                final EditText contact_number = view_in.findViewById(com.example.administrator.mosac_android.R.id.edit_contactnumber);
                final EditText email = view_in.findViewById(com.example.administrator.mosac_android.R.id.edit_email);

                final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                        .setView(view_in)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(number.getText().toString().equals("") || number.getText().toString().equals("") || number.getText().toString().equals("")
                                || number.getText().toString().equals("")) {
                            Toast.makeText(LoginActivity.this, "验证信息不能为空，请检查", Toast.LENGTH_LONG).show();
                        }
                        else {
                            webserviceHelper.findPassword(number.getText().toString(), username.getText().toString(),
                                    contact_number.getText().toString(), email.getText().toString());
                            alertDialog.dismiss();
                        }
                    }
                });
                break;
            case com.example.administrator.mosac_android.R.id.del_number:
                edit_number.setText("");
                break;
            case com.example.administrator.mosac_android.R.id.del_password:
                edit_password.setText("");
                break;
            default:
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVerifyEvent(VerifyEvent event) {
        boolean correct = event.getCorrect();
        if(correct) {
            // 登录验证成功
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("number", edit_number.getText().toString());
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(LoginActivity.this, "学号或密码错误，请检查", Toast.LENGTH_LONG).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFindPasswordEvent(FindPasswordEvent event) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        if (event.getPassword().equals("")) {
            alertDialog.setMessage("您输入的验证信息有误，请检查");
            alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        else
        {
            alertDialog.setMessage("您的密码是" + event.getPassword());
            alertDialog.setPositiveButton("我记住了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        alertDialog.show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
