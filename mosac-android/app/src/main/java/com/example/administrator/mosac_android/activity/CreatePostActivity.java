package com.example.administrator.mosac_android.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.presenter.UpdatePostPresenter;
import com.example.administrator.mosac_android.utils.ToastUtils;
import com.example.administrator.mosac_android.view.UpdatePostView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

public class CreatePostActivity extends BaseActivity implements UpdatePostView {
    private TextView cancel;
    private TextView ok;
    private EditText title;
    private EditText content;
    private UpdatePostPresenter updatePostPresenter;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertpost);
        updatePostPresenter = new UpdatePostPresenter();
        updatePostPresenter.attachView(this);
        user_id = getIntent().getIntExtra("user_id", -1);
        ToastUtils.initToast(getContext());
        bindViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updatePostPresenter.detachView();
    }

    public void bindViews() {
        cancel = findViewById(R.id.cancel);
        ok = findViewById(R.id.ok);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().equals("")) {
                    Toast.makeText(CreatePostActivity.this, "标题不能为空！", Toast.LENGTH_LONG).show();
                }
                else if(content.getText().toString().equals("")) {
                    Toast.makeText(CreatePostActivity.this, "内容不能为空！", Toast.LENGTH_LONG).show();
                }
                else {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");//设置日期格式
                    String time = df.format(new Date()); // new Date()为获取当前系统时间
                    Post post = new Post(title.getText().toString(), content.getText().toString(), time);
                    updatePostPresenter.getData("InsertPost", title.getText().toString(),
                            content.getText().toString(), Integer.toString(user_id), time);
                }
            }
        });
    }

    public void onOperationSuccess() {
        // 发帖成功
        ToastUtils.showToast(getContext(), "恭喜你，发帖成功", Toast.LENGTH_SHORT);
        finish();
    }
}
