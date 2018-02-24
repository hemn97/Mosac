package com.example.administrator.mosac_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.activity.PostActivity;
import com.example.administrator.mosac_android.adpater.PostAdapter;
import com.example.administrator.mosac_android.event.PostListEvent;
import com.example.administrator.mosac_android.event.UpdatePostListEvent;
import com.example.administrator.mosac_android.model.Post;
import com.example.administrator.mosac_android.model.User;
import com.example.administrator.mosac_android.webservice.WebserviceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class Tab1Fragment extends Fragment {
    private EditText search_text;
    private ImageView search_bt;
    private RecyclerView postListview;
    private WebserviceHelper webserviceHelper;
    private List<Post> postList;
    private PostAdapter postAdapter;
    private Post post;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container,false);
        // 注册EventBus
        EventBus.getDefault().register(this);
        user = (User) getArguments().getSerializable("user");
        webserviceHelper = new WebserviceHelper();
        bindViews(view);
        postListview.setLayoutManager(new LinearLayoutManager(getContext())); // 线性显示
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        webserviceHelper.queryPostList("");
    }
    public void bindViews(View v) {
        search_text = (EditText) v.findViewById(R.id.search_text);
        search_bt = (ImageView) v.findViewById(R.id.search_bt);
        postListview = (RecyclerView) v.findViewById(R.id.postlist);
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
                    webserviceHelper.queryPostList("");
                }
            }
        });
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				String keyword = search_text.getText().toString();
				webserviceHelper.queryPostList(keyword);
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPostListEvent(PostListEvent event) {
        postList = event.getPostList();
        postAdapter = new PostAdapter(getContext(), R.layout.postitem_ly, postList);
        postListview.setAdapter(postAdapter);
        postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                post = postList.get(position);
                post.setViews(post.getViews()+1);
                webserviceHelper.updatePostViews(post.getPost_id());
                Intent intent = new Intent(getContext(), PostActivity.class);
                intent.putExtra("post", post);
                intent.putExtra("user", user);
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {

            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdatePostListEvent(UpdatePostListEvent event) {
        webserviceHelper.queryPostList("");
        Toast.makeText(getContext(), "发帖成功", Toast.LENGTH_LONG).show();
    }
}
