package com.example.administrator.mosac_android.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.activity.LoginActivity;
import com.example.administrator.mosac_android.activity.PostDetailActivity;
import com.example.administrator.mosac_android.adapter.PostAdapter;
import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.presenter.PostPresenter;
import com.example.administrator.mosac_android.presenter.UpdatePostPresenter;
import com.example.administrator.mosac_android.presenter.UpdateTeamPresenter;
import com.example.administrator.mosac_android.view.PostView;
import com.example.administrator.mosac_android.view.UpdatePostView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class Tab1Fragment extends BaseFragment implements PostView, UpdatePostView {
    private EditText search_text;
    private ImageView search_bt;
    private RecyclerView postListview;
    private View mView;
    private PostPresenter postPresenter;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private int user_id;
    private UpdatePostPresenter updatePostPresenter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            postList = (List<Post>) msg.getData().getParcelableArrayList("postList").get(0);
            postAdapter.updateAdapter(postList);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        user_id = getArguments().getInt("user_id");
        bindViews();
        postAdapter = new PostAdapter(getContext(), R.layout.postitem_ly, null);
        postListview.setAdapter(postAdapter);
        postListview.setLayoutManager(new LinearLayoutManager(getContext())); // 线性显示
        setListener();
        // getPresenter
        postPresenter = new PostPresenter();
        postPresenter.attachView(this);
        updatePostPresenter = new UpdatePostPresenter();
        updatePostPresenter.attachView(this);

        return mView;
    }
    @Override
    public void onStart() {
        super.onStart();
        if(search_text.getText().toString().length() == 0) {
            postPresenter.getData("FindAllPosts", "");
        }
    }
    public void initAllMembersView(Bundle savedInstanceState) {
    }
    public int getContentViewId() {
        return R.layout.fragment_tab1;
    }
    public void bindViews() {
        search_text = (EditText) mView.findViewById(R.id.search_text);
        search_bt = (ImageView) mView.findViewById(R.id.search_bt);
        postListview = (RecyclerView) mView.findViewById(R.id.postlist);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //断开View引用
        postPresenter.detachView();
        updatePostPresenter.detachView();
    }
    public void setListener() {
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                // 监听如果输入串长度大于0那么就显示按钮。
                if (s.length() > 0) {
                    search_bt.setVisibility(View.VISIBLE);
                } else {
                    search_bt.setVisibility(View.INVISIBLE);
                    postPresenter.getData("FindAllPosts", "");
                }
            }
        });
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPresenter.getData("FindAllPosts", search_text.getText().toString());
            }
        });
        postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Post post = postList.get(position);
                Intent intent = new Intent(getContext(), PostDetailActivity.class);
                intent.putExtra("post", post);
                intent.putExtra("user_id", user_id);
                updatePostPresenter.getData("UpdatePostViews", Integer.toString(post.getPost_id()), Integer.toString(user_id));
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {
            }
        });
    }

    @Override
    public void showPosts(List<Post> data) {
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        ArrayList list = new ArrayList();
        list.add(data);
        bundle.putParcelableArrayList("postList", list);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    public void onOperationSuccess() {
        // 后台更新访问记录成功
        // do nothing
    }
}
