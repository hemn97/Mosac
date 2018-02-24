package com.example.administrator.mosac_android.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.mosac_android.event.DeletePostEvent;
import com.example.administrator.mosac_android.event.InsertPostEvent;
import com.example.administrator.mosac_android.event.UserEvent;
import com.example.administrator.mosac_android.fragment.Tab1Fragment;
import com.example.administrator.mosac_android.fragment.Tab2Fragment;
import com.example.administrator.mosac_android.fragment.Tab4Fragment;
import com.example.administrator.mosac_android.model.Post;
import com.example.administrator.mosac_android.model.User;
import com.example.administrator.mosac_android.webservice.WebserviceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class HomeActivity extends FragmentActivity implements View.OnClickListener {
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
    private WebserviceHelper webserviceHelper;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.administrator.mosac_android.R.layout.activity_home);
        // 注册EventBus
        EventBus.getDefault().register(this);
        webserviceHelper = new WebserviceHelper();
        getUser();
        fragmentManager = getSupportFragmentManager();
        bindViews();
    }
    //UI组件初始化与事件绑定
    private void bindViews() {
        tab1 = (LinearLayout) findViewById(com.example.administrator.mosac_android.R.id.tab1);
        tab2 = (LinearLayout) findViewById(com.example.administrator.mosac_android.R.id.tab2);
        tab4 = (LinearLayout) findViewById(com.example.administrator.mosac_android.R.id.tab4);
        home_content = (FrameLayout) findViewById(com.example.administrator.mosac_android.R.id.home_content);
        tab1.setOnClickListener(HomeActivity.this);
        tab2.setOnClickListener(HomeActivity.this);
        tab4.setOnClickListener(HomeActivity.this);
    }
    //重置所有文本的选中状态
    private void setSelected(){
        tab1.setSelected(false);
        tab2.setSelected(false);
        tab4.setSelected(false);
    }
    //隐藏所有Fragment
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
            case com.example.administrator.mosac_android.R.id.tab1:
                setSelected(); // 重置所有文本的选中状态
                tab1.setSelected(true);
                if(tab1Fragment == null){
                    tab1Fragment = new Tab1Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    tab1Fragment.setArguments(bundle);
                    fragmentTransaction.add(com.example.administrator.mosac_android.R.id.home_content, tab1Fragment);
                }else{
                    fragmentTransaction.show(tab1Fragment);
                }
                break;
            case com.example.administrator.mosac_android.R.id.tab2:
                setSelected();
                tab2.setSelected(true);
//                title.setText("组队");
                if(tab2Fragment == null){
                    tab2Fragment = new Tab2Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    tab2Fragment.setArguments(bundle);
                    fragmentTransaction.add(com.example.administrator.mosac_android.R.id.home_content, tab2Fragment);
                }else{
                    fragmentTransaction.show(tab2Fragment);
                }
                break;
            case com.example.administrator.mosac_android.R.id.tab4:
                setSelected();
                tab4.setSelected(true);
//                title.setText("我");
                if(tab4Fragment == null){
                    tab4Fragment = new Tab4Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    tab4Fragment.setArguments(bundle);
                    fragmentTransaction.add(com.example.administrator.mosac_android.R.id.home_content, tab4Fragment);
                }else{
                    fragmentTransaction.show(tab4Fragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }
    private void getUser() {
        webserviceHelper.queryUserWithNumber(getIntent().getStringExtra("number"));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(UserEvent event) {
        user = event.getUser();
        tab1.performClick();   //模拟一次点击，即进去后选择第一项
        webserviceHelper.queryMyPost(user.getUser_id());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInsertPostEvent(InsertPostEvent event) {
        Post post = event.getPost();
        webserviceHelper.insertPost(post.getTitle(), post.getContent(), user.getUser_id(), post.getTime());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeletePostEvent(DeletePostEvent event) {
        if (event.getSuccess() == true) {
            Toast.makeText(HomeActivity.this, "删除帖子成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(HomeActivity.this, "删除帖子失败", Toast.LENGTH_LONG).show();
        }
    }
}
