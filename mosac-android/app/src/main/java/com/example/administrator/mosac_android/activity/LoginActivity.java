package com.example.administrator.mosac_android.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.presenter.FindPasswdPresenter;
import com.example.administrator.mosac_android.presenter.UserPresenter;
import com.example.administrator.mosac_android.utils.ToastUtils;
import com.example.administrator.mosac_android.view.FindPasswdView;
import com.example.administrator.mosac_android.view.UserView;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class LoginActivity extends BaseActivity implements UserView, FindPasswdView, View.OnClickListener {
    private Button confirm;
    private EditText edit_number;
    private EditText edit_password;
    private ImageView del_number;
    private ImageView del_password;
    private TextView register;
    private TextView forget;
    private CheckBox cbIsRememberPass;
    private SharedPreferences sharedPreferences;
    private UserPresenter userPresenter;
    private FindPasswdPresenter findPasswdPresenter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
            alertDialog.setMessage("您的密码是：" + msg.obj);
            alertDialog.setPositiveButton("我记住了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ToastUtils.initToast(this);
        // 绑定UI控件
        bindViews();
        // 自动填充账号密码
        autoFill();
        // 设置监听事件
        setListener();
        // 获取Presenter
        userPresenter = new UserPresenter();
        userPresenter.attachView(this);
        findPasswdPresenter = new FindPasswdPresenter();
        findPasswdPresenter.attachView(this);
    }

    private void autoFill() {
        sharedPreferences=getSharedPreferences("rememberpassword", Context.MODE_PRIVATE);
        boolean isRemember= sharedPreferences.getBoolean("rememberpassword",false);
        if(isRemember)
        {
            String name = sharedPreferences.getString("name","");
            String password = sharedPreferences.getString("password","");
            edit_number.setText(name);
            edit_password.setText(password);
            cbIsRememberPass.setChecked(true);
        }
    }

    private void bindViews(){
        confirm = (Button) findViewById(R.id.confirm);
        edit_number = (EditText) findViewById(R.id.edit_number);
        edit_password = (EditText) findViewById(R.id.edit_password);
        del_number = (ImageView) findViewById(R.id.del_number);
        del_password = (ImageView) findViewById(R.id.del_password);
        register = (TextView) findViewById(R.id.register);
        forget = (TextView) findViewById(R.id.forget);
        cbIsRememberPass = (CheckBox) findViewById(R.id.cbIsRememberPass);
    }

    private void setListener() {
        confirm.setOnClickListener(this);
        register.setOnClickListener(this);
        forget.setOnClickListener(this);
        del_number.setOnClickListener(this);
        del_password.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                if(edit_number.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "学号不能为空", Toast.LENGTH_LONG).show();
                }
                else if(edit_password.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                }
                else {
                    // 验证登录
                    userPresenter.getData("LoginValidate", edit_number.getText().toString(), edit_password.getText().toString());
                    // 保存账号密码
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
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forget:
                LayoutInflater factor = LayoutInflater.from(LoginActivity.this);
                View view_in = factor.inflate(R.layout.findpassword_dialog, null);
                final EditText number = view_in.findViewById(R.id.edit_number);
                final EditText username = view_in.findViewById(R.id.edit_username);
                final EditText contact_number = view_in.findViewById(R.id.edit_contactnumber);
                final EditText email = view_in.findViewById(R.id.edit_email);

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
                        if(number.getText().toString().equals("") || username.getText().toString().equals("") || contact_number.getText().toString().equals("")
                                || email.getText().toString().equals("")) {
                            Toast.makeText(LoginActivity.this, "验证信息不能为空，请检查", Toast.LENGTH_LONG).show();
                        }
                        else {
                            // 验证信息
                            findPasswdPresenter.getData("FindPassword", number.getText().toString(),
                                    username.getText().toString(), contact_number.getText().toString(), email.getText().toString());
                            alertDialog.dismiss();
                        }
                    }
                });
                break;
            case R.id.del_number:
                edit_number.setText("");
                break;
            case R.id.del_password:
                edit_password.setText("");
                break;
            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开View引用
        userPresenter.detachView();
        findPasswdPresenter.detachView();
    }

    @Override
    public void onOperationSuccess() {
        // 登录验证成功
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("user_number", edit_number.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    public void onFindPasswd(String password) {
        Message msg = Message.obtain();
        msg.obj = password;
        mHandler.sendMessage(msg);
    }
}
