package com.example.administrator.mosac_android.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.administrator.mosac_android.bean.Comment;
import com.example.administrator.mosac_android.bean.User;
import com.example.administrator.mosac_android.fragment.Tab1Fragment;
import com.example.administrator.mosac_android.fragment.Tab2Fragment;
import com.example.administrator.mosac_android.fragment.Tab4Fragment;
import com.example.administrator.mosac_android.presenter.GetUserMsgPresenter;
import com.example.administrator.mosac_android.presenter.PostPresenter;
import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.view.GetUserMsgView;
import com.example.administrator.mosac_android.view.PostView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class HomeActivity extends BaseFragmentActivity implements GetUserMsgView, View.OnClickListener {
    //UI Object
    private LinearLayout tab1;
    private LinearLayout tab2;
    private LinearLayout tab4;
    private FrameLayout home_content;
    private FragmentManager fragmentManager;
    //Fragment Object
    private Tab1Fragment tab1Fragment;
    private Tab2Fragment tab2Fragment;
    private Tab4Fragment tab4Fragment;
    private User user;
    private GetUserMsgPresenter getUserMsgPresenter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tab1.performClick();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getUserMsgPresenter = new GetUserMsgPresenter();
        getUserMsgPresenter.attachView(this);
        // 获取登录用户信息
        getUserMsgPresenter.getData("GetUserMessage", getIntent().getStringExtra("user_number"));
        fragmentManager = getSupportFragmentManager();
        bindViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getUserMsgPresenter.detachView();
    }
    private void bindViews() {
        tab1 = (LinearLayout) findViewById(R.id.tab1);
        tab2 = (LinearLayout) findViewById(R.id.tab2);
        tab4 = (LinearLayout) findViewById(R.id.tab4);
        home_content = (FrameLayout) findViewById(R.id.home_content);
        tab1.setOnClickListener(HomeActivity.this);
        tab2.setOnClickListener(HomeActivity.this);
        tab4.setOnClickListener(HomeActivity.this);
    }
    private void setSelected(){
        tab1.setSelected(false);
        tab2.setSelected(false);
        tab4.setSelected(false);
    }
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(tab1Fragment != null)fragmentTransaction.hide(tab1Fragment);
        if(tab2Fragment != null)fragmentTransaction.hide(tab2Fragment);
        if(tab4Fragment != null)fragmentTransaction.hide(tab4Fragment);
    }
    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction); // 首先隐藏所有Fragment
        switch (v.getId()) {
            case R.id.tab1:
                setSelected(); // 重置所有文本的选中状态
                tab1.setSelected(true);
                if(tab1Fragment == null){
                    tab1Fragment = new Tab1Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("user_id", user.getUser_id());
                    tab1Fragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.home_content, tab1Fragment);
                }else{
                    fragmentTransaction.show(tab1Fragment);
                }
                break;
            case R.id.tab2:
                setSelected();
                tab2.setSelected(true);
                if(tab2Fragment == null){
                    tab2Fragment = new Tab2Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("user_id", user.getUser_id());
                    tab2Fragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.home_content, tab2Fragment);
                }else{
                    fragmentTransaction.show(tab2Fragment);
                }
                break;
            case R.id.tab4:
                setSelected();
                tab4.setSelected(true);
                if(tab4Fragment == null){
                    tab4Fragment = new Tab4Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    tab4Fragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.home_content, tab4Fragment);
                }else{
                    fragmentTransaction.show(tab4Fragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    public void onGetUserMsg(User user) {
        this.user = user;
        mHandler.sendMessage(new Message());
    }

}
