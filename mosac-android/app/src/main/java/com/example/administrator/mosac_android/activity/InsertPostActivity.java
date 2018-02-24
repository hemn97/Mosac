package com.example.administrator.mosac_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mosac_android.event.InsertPostEvent;
import com.example.administrator.mosac_android.model.Post;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class InsertPostActivity extends Activity {
    private TextView cancel;
    private TextView ok;
    private EditText title;
    private EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.administrator.mosac_android.R.layout.activity_insertpost);
        bindViews();
    }
    public void bindViews() {
        cancel = findViewById(com.example.administrator.mosac_android.R.id.cancel);
        ok = findViewById(com.example.administrator.mosac_android.R.id.ok);
        title = findViewById(com.example.administrator.mosac_android.R.id.title);
        content = findViewById(com.example.administrator.mosac_android.R.id.content);
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
                    Toast.makeText(InsertPostActivity.this, "标题不能为空！", Toast.LENGTH_LONG).show();
                }
                else if(content.getText().toString().equals("")) {
                    Toast.makeText(InsertPostActivity.this, "内容不能为空！", Toast.LENGTH_LONG).show();
                }
                else {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");//设置日期格式
                    String time = df.format(new Date()); // new Date()为获取当前系统时间
                    Post post = new Post(title.getText().toString(), content.getText().toString(), time);
                    EventBus.getDefault().post(new InsertPostEvent(post));
                    finish();
                }
            }
        });
    }
}
