package com.example.administrator.mosac_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.mosac_android.event.CommentPostEvent;
import com.example.administrator.mosac_android.event.LightupPostEvent;
import com.example.administrator.mosac_android.model.Post;
import com.example.administrator.mosac_android.model.User;
import com.example.administrator.mosac_android.webservice.WebserviceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class PostActivity extends Activity {
    private TextView title;
    private TextView time;
    private TextView author;
    private TextView content;
    private TextView views;
    private LinearLayout light_bt;
    private LinearLayout queryComment;
    private ImageView deletePost;
    private WebserviceHelper webserviceHelper;
    private Post post;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.administrator.mosac_android.R.layout.activity_post);
        // 注册EventBus
        EventBus.getDefault().register(this);
        webserviceHelper = new WebserviceHelper();
        post = (Post) getIntent().getSerializableExtra("post");
        user = (User) getIntent().getSerializableExtra("user");
        bindViews();
    }
    public void bindViews() {
        title = findViewById(com.example.administrator.mosac_android.R.id.title);
        time = findViewById(com.example.administrator.mosac_android.R.id.time);
        content = findViewById(com.example.administrator.mosac_android.R.id.content);
        views = findViewById(com.example.administrator.mosac_android.R.id.views);
        light_bt = findViewById(com.example.administrator.mosac_android.R.id.light);
        queryComment = findViewById(com.example.administrator.mosac_android.R.id.queryComent);
        deletePost = findViewById(com.example.administrator.mosac_android.R.id.deletePost);
        if(user.getUser_id() != post.getAuthor_id()) {
            deletePost.setVisibility(View.INVISIBLE);
        } else {
            deletePost.setVisibility(View.VISIBLE);
        }
        author = findViewById(com.example.administrator.mosac_android.R.id.author);
        title.setText(post.getTitle());
        time.setText(post.getTime());
        content.setText(post.getContent());
        views.setText(Integer.toString(post.getViews()));
        author.setText(post.getAuthor());
        queryComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, ViewCommentActivity.class);
                intent.putExtra("post_id", post.getPost_id());
                intent.putExtra("post", post);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        light_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webserviceHelper.lightupPost(post.getPost_id(), user.getUser_id());
            }
        });

        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PostActivity.this);
                alertDialog.setMessage("你确定要删除这个帖子吗？");
                alertDialog.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        webserviceHelper.deletePost(post.getPost_id());
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLightupPostEvent(LightupPostEvent event) {
        if(event.getSuccess() == true) {
            Toast.makeText(PostActivity.this, "点亮成功", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(PostActivity.this, "您已经点亮过了，请勿重复点亮", Toast.LENGTH_LONG).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommentPostEvent(CommentPostEvent event) {
        if(event.getSuccess() == true) {
            Toast.makeText(PostActivity.this, "评论成功", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(PostActivity.this, "评论失败", Toast.LENGTH_LONG).show();
        }
    }
}
