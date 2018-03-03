package com.example.administrator.mosac_android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.adapter.CommentAdapter;
import com.example.administrator.mosac_android.bean.Comment;
import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.presenter.CommentPresenter;
import com.example.administrator.mosac_android.presenter.UpdatePostPresenter;
import com.example.administrator.mosac_android.view.CommentView;
import com.example.administrator.mosac_android.view.UpdatePostView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class PostCommentActivity extends BaseActivity implements CommentView, UpdatePostView {
    private RecyclerView commentListview;
    private LinearLayout comment_bt;
    private RelativeLayout emptyComment;
    private int post_id;
    private int user_id;
    private CommentPresenter commentPresenter;
    private UpdatePostPresenter updatePostPresenter;
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            commentList = (List<Comment>) msg.getData().getParcelableArrayList("commentList").get(0);
            if(commentList == null) {
                emptyComment.setVisibility(View.VISIBLE);
            } else {
                commentAdapter.updateAdapter(commentList);
                emptyComment.setVisibility(View.INVISIBLE);
            }
        }
    };

    private Handler secondHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            commentPresenter.getData("FindAllComment", Integer.toString(post_id));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcomment);
        commentPresenter = new CommentPresenter();
        commentPresenter.attachView(this);
        updatePostPresenter = new UpdatePostPresenter();
        updatePostPresenter.attachView(this);
        bindViews();
        commentAdapter = new CommentAdapter(PostCommentActivity.this, R.layout.commentitem_ly, null);
        commentListview.setAdapter(commentAdapter);
        commentListview.setLayoutManager(new LinearLayoutManager(PostCommentActivity.this)); // 线性显示

        post_id = getIntent().getIntExtra("post_id", 0);
        user_id = getIntent().getIntExtra("user_id", 0);

        commentPresenter.getData("FindAllComment", Integer.toString(post_id));
        setListener();

    }

    private void bindViews() {
        commentListview = findViewById(R.id.commentlistview);
        emptyComment = findViewById(R.id.emptyComment);
        comment_bt = findViewById(R.id.comment);
    }

    private void setListener() {
        comment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factor = LayoutInflater.from(PostCommentActivity.this);
                View view_in = factor.inflate(R.layout.comment_dialog, null);
                final EditText content = view_in.findViewById(R.id.content);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PostCommentActivity.this);
                alertDialog.setView(view_in);
                alertDialog.setPositiveButton("发表评论", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(content.getText().toString().equals("")) {
                            Toast.makeText(PostCommentActivity.this, "回复内容不能为空！", Toast.LENGTH_LONG).show();
                        }
                        else if(content.getText().toString().length() < 4) {
                            Toast.makeText(PostCommentActivity.this, "回复内容不能少于4个字！", Toast.LENGTH_LONG).show();
                        }
                        else {
                            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");//设置日期格式
                            String time = df.format(new Date()); // new Date()为获取当前系统时间
                            updatePostPresenter.getData("CommentPost", Integer.toString(post_id),
                                    Integer.toString(user_id), content.getText().toString(), time);
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
        commentPresenter.detachView();
        updatePostPresenter.detachView();
    }

    public void showComments(List<Comment> data) {
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        ArrayList list = new ArrayList();
        list.add(data);
        bundle.putParcelableArrayList("commentList", list);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    public void onOperationSuccess() {
        // 评论成功，刷新评论列表
        secondHandler.sendMessage(new Message());
    }
}
