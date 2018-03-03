package com.example.administrator.mosac_android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.presenter.UpdatePostPresenter;
import com.example.administrator.mosac_android.utils.ToastUtils;
import com.example.administrator.mosac_android.view.UpdatePostView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class PostDetailActivity extends BaseActivity implements UpdatePostView{
    private TextView title;
    private TextView time;
    private TextView author;
    private TextView content;
    private TextView views;
    private LinearLayout light_bt;
    private LinearLayout queryComment;
    private Post post;
    private int user_id;
    private UpdatePostPresenter updatePostPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        post = (Post) getIntent().getSerializableExtra("post");
        user_id = getIntent().getIntExtra("user_id", -1);
        // get Presenter
        updatePostPresenter = new UpdatePostPresenter();
        updatePostPresenter.attachView(this);
        ToastUtils.initToast(this);
        bindViews();
        setListener();
    }

    public void bindViews() {
        title = findViewById(R.id.title);
        time = findViewById(R.id.time);
        content = findViewById(R.id.content);
        views = findViewById(R.id.views);
        light_bt = findViewById(R.id.light);
        queryComment = findViewById(R.id.queryComent);
        author = findViewById(R.id.author);
        title.setText(post.getTitle());
        time.setText(post.getTime());
        content.setText(post.getContent());
        views.setText(Integer.toString(post.getViews()));
        author.setText(post.getAuthor());
    }

    public void setListener() {
        queryComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetailActivity.this, PostCommentActivity.class);
                intent.putExtra("post_id", post.getPost_id());
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
        light_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePostPresenter.getData("LightupPost", Integer.toString(post.getPost_id()), Integer.toString(user_id));
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updatePostPresenter.detachView();
    }

    public void onOperationSuccess() {
        // 点亮成功
        ToastUtils.showToast(this, "点亮成功", Toast.LENGTH_SHORT);
    }
}
