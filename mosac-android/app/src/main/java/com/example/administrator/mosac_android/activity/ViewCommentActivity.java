package com.example.administrator.mosac_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.mosac_android.adpater.CommentAdapter;
import com.example.administrator.mosac_android.event.CommentListEvent;
import com.example.administrator.mosac_android.event.CommentPostEvent;
import com.example.administrator.mosac_android.model.Comment;
import com.example.administrator.mosac_android.model.Post;
import com.example.administrator.mosac_android.model.User;
import com.example.administrator.mosac_android.webservice.WebserviceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class ViewCommentActivity extends Activity {
    private RecyclerView commentListview;
    private List<Comment> commentList;
    private WebserviceHelper webserviceHelper;
    private LinearLayout comment_bt;
    private CommentAdapter commentAdapter;
    private RelativeLayout emptyComment;
    private Post post;
    private User user;
    int postid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.administrator.mosac_android.R.layout.activity_viewcomment);
        // 注册EventBus
        EventBus.getDefault().register(this);
        webserviceHelper = new WebserviceHelper();
        post = (Post) getIntent().getSerializableExtra("post");
        user = (User) getIntent().getSerializableExtra("user");
        postid=getIntent().getIntExtra("post_id", 0);
        webserviceHelper.getCommentList(postid);
        commentListview = findViewById(com.example.administrator.mosac_android.R.id.commentlistview);
        emptyComment = findViewById(com.example.administrator.mosac_android.R.id.emptyComment);
        comment_bt = findViewById(com.example.administrator.mosac_android.R.id.comment);
        commentListview.setLayoutManager(new LinearLayoutManager(ViewCommentActivity.this)); // 线性显示

        comment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factor = LayoutInflater.from(ViewCommentActivity.this);
                View view_in = factor.inflate(com.example.administrator.mosac_android.R.layout.comment_dialog, null);
                final EditText content = view_in.findViewById(com.example.administrator.mosac_android.R.id.content);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewCommentActivity.this);
                alertDialog.setView(view_in);
                alertDialog.setPositiveButton("发表评论", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(content.getText().toString().equals("")) {
                            Toast.makeText(ViewCommentActivity.this, "回复内容不能为空！", Toast.LENGTH_LONG).show();
                        }
                        else if(content.getText().toString().length() < 4) {
                            Toast.makeText(ViewCommentActivity.this, "回复内容不能少于4个字！", Toast.LENGTH_LONG).show();
                        }
                        else {
                            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");//设置日期格式
                            String time = df.format(new Date()); // new Date()为获取当前系统时间
                            webserviceHelper.commentPost(post.getPost_id(), user.getUser_id(), content.getText().toString(), time);
                        }
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
    public void onCommentListEvent(CommentListEvent event) {
        commentList = event.getCommentList();
        if(commentList.size() == 0) {
            emptyComment.setVisibility(View.VISIBLE);
        } else {
            commentAdapter = new CommentAdapter(ViewCommentActivity.this, com.example.administrator.mosac_android.R.layout.commentitem_ly, commentList);
            commentListview.setAdapter(commentAdapter);
            emptyComment.setVisibility(View.INVISIBLE);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommentPostEvent(CommentPostEvent event) {
        if(event.getSuccess() == true) {
            // 评论成功，刷新评论列表
            webserviceHelper.getCommentList(postid);
        }
    }
}
