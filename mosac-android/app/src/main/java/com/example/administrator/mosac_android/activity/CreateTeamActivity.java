package com.example.administrator.mosac_android.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.presenter.UpdateTeamPresenter;
import com.example.administrator.mosac_android.utils.DateTimePickerDialog;
import com.example.administrator.mosac_android.utils.ToastUtils;
import com.example.administrator.mosac_android.view.UpdateTeamView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

public class CreateTeamActivity extends BaseActivity implements UpdateTeamView {
    private int user_id;
    private TextView cancel;
    private TextView create;
    private EditText teamname;
    private EditText time;
    private EditText etime;
    private EditText place;
    private EditText description;
    private List<String> typeList = new ArrayList<String>();
    private String type;
    private UpdateTeamPresenter updateTeamPresenter;
    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createteam);
        ToastUtils.initToast(getContext());
        updateTeamPresenter = new UpdateTeamPresenter();
        updateTeamPresenter.attachView(this);
        user_id = getIntent().getIntExtra("user_id", -1);
        bindViews();
        setListener();
    }

    private void bindViews() {
        typeList.add("学习");
        typeList.add("运动");
        typeList.add("户外");
        typeList.add("游戏");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(CreateTeamActivity.this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp = (Spinner) findViewById(R.id.edit_type);
        sp.setAdapter(adapter);

        cancel = findViewById(R.id.cancel);
        create = findViewById(R.id.create);
        teamname = findViewById(R.id.edit_teamname);
        time = findViewById(R.id.edit_time);
        etime = findViewById(R.id.edit_etime);
        time.setInputType(InputType.TYPE_NULL);
        etime.setInputType(InputType.TYPE_NULL);
        place = findViewById(R.id.edit_place);
        description = findViewById(R.id.edit_description);
    }

    private void setListener() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReminders();
            }
        });
        etime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRemindere();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamname.getText().toString().equals("") || type.equals("") || time.getText().toString().equals("") ||
                        place.getText().toString().equals("") || description.getText().toString().equals("")) {
                    Toast.makeText(CreateTeamActivity.this, "请完整输入队伍的所有信息", Toast.LENGTH_LONG).show();
                }
                else {
                    updateTeamPresenter.getData("InsertTeam", Integer.toString(user_id),
                            teamname.getText().toString(), time.getText().toString(), description.getText().toString(),
                            place.getText().toString(), type);
                }
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                type = adapter.getItem(position);
            }
            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setReminders()
    {
        DateTimePickerDialog d = new DateTimePickerDialog(this, System.currentTimeMillis());
        d.setOnDateTimeSetListener(new DateTimePickerDialog.OnDateTimeSetListener()
        {
            @Override
            public void OnDateTimeSet(AlertDialog dialog, long date)
            {
                time.setText(getStringDate(date));
            }
        });
        d.show();
    }

    private void setRemindere()
    {
        DateTimePickerDialog d = new DateTimePickerDialog(this, System.currentTimeMillis());
        d.setOnDateTimeSetListener(new DateTimePickerDialog.OnDateTimeSetListener()
        {
            @Override
            public void OnDateTimeSet(AlertDialog dialog, long date)
            {
                etime.setText(getStringDate(date));
            }
        });
        d.show();
    }

    public static String getStringDate(Long date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd(HH:mm:ss)");
        String dateString = formatter.format(date);

        return dateString;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateTeamPresenter.detachView();
    }

    public void onUpdateTeamSuccess(String data) {
        // 创建队伍成功
        ToastUtils.showToast(getContext(), "恭喜你，创建队伍成功", Toast.LENGTH_SHORT);
        finish();
    }
}
