package com.example.administrator.mosac_android.activity;

import android.app.Activity;
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

import com.example.administrator.mosac_android.webservice.WebserviceHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class CreateTeamActivity extends Activity {
    private int user_id;
    private TextView cancel;
    private TextView create;
    private EditText teamname;
    private EditText maxnumber;
    private EditText time;
    private EditText etime;
    private EditText place;
    private EditText description;
    private WebserviceHelper webserviceHelper;
    private List<String> typeList = new ArrayList<String>();
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.administrator.mosac_android.R.layout.activity_createteam);
        webserviceHelper = new WebserviceHelper();
        user_id = getIntent().getIntExtra("user_id", -1);
        bindViews();
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

    public void bindViews() {
        typeList.add("学习");
        typeList.add("运动");
        typeList.add("户外");
        typeList.add("游戏");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(CreateTeamActivity.this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sp = (Spinner) findViewById(com.example.administrator.mosac_android.R.id.edit_type);
        sp.setAdapter(adapter);
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
        cancel = findViewById(com.example.administrator.mosac_android.R.id.cancel);
        create = findViewById(com.example.administrator.mosac_android.R.id.create);
        teamname = findViewById(com.example.administrator.mosac_android.R.id.edit_teamname);
        maxnumber = findViewById(com.example.administrator.mosac_android.R.id.edit_maxnumber);
        time = findViewById(com.example.administrator.mosac_android.R.id.edit_time);
        etime = findViewById(com.example.administrator.mosac_android.R.id.edit_etime);
        time.setInputType(InputType.TYPE_NULL);
        etime.setInputType(InputType.TYPE_NULL);
        place = findViewById(com.example.administrator.mosac_android.R.id.edit_place);
        description = findViewById(com.example.administrator.mosac_android.R.id.edit_description);
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
                        place.getText().toString().equals("") ||
                        description.getText().toString().equals("") || maxnumber.getText().toString().equals("")) {
                    Toast.makeText(CreateTeamActivity.this, "请完整输入队伍的所有信息", Toast.LENGTH_LONG).show();
                }
                else {
                    webserviceHelper.addTeam(teamname.getText().toString(), type,
                            description.getText().toString(), time.getText().toString()+" ~ "+etime.getText().toString(),
                            place.getText().toString(), Integer.parseInt(maxnumber.getText().toString()), user_id);
                    finish();
                }
            }
        });
    }
}
