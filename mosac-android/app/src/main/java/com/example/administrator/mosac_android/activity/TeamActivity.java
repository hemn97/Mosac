package com.example.administrator.mosac_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mosac_android.adpater.JoinitemAdapter;
import com.example.administrator.mosac_android.adpater.TeammateAdapter;
import com.example.administrator.mosac_android.event.CaptainEvent;
import com.example.administrator.mosac_android.event.DeleteTeamEvent;
import com.example.administrator.mosac_android.event.InTeamEvent;
import com.example.administrator.mosac_android.event.JoinEvent;
import com.example.administrator.mosac_android.event.JoinListEvent;
import com.example.administrator.mosac_android.event.JoinResponseEvent;
import com.example.administrator.mosac_android.event.JoinTeamEvent;
import com.example.administrator.mosac_android.event.LeaveTeamEvent;
import com.example.administrator.mosac_android.event.TeammatesListEvent;
import com.example.administrator.mosac_android.model.Team;
import com.example.administrator.mosac_android.model.User;
import com.example.administrator.mosac_android.webservice.WebserviceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class TeamActivity extends Activity {
    private User user;
    private User captain;
    private Team team;
    private WebserviceHelper webserviceHelper;
    private TextView teamname;
    private TextView description;
    private TextView captainname;
    private TextView time;
    private TextView place;
    private TextView nownum;
    private TextView maxnum;
    private TextView captainMSGname;
    private TextView captainMSGemail;
    private TextView captainMSGdepartment;
    private List<User> teammatesList;
    private TeammateAdapter teammateAdapter;
    private JoinitemAdapter joinitemAdapter;
    private List<User> joinList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        team = (Team) getIntent().getSerializableExtra("team");
        user = (User) getIntent().getSerializableExtra("user");
        // 注册EventBus
        EventBus.getDefault().register(this);
        webserviceHelper = new WebserviceHelper();
        webserviceHelper.queryUserWithId(team.getCaptain_id());
        if (user.getUser_id() == team.getCaptain_id()) {
            // 队长模式
            setContentView(com.example.administrator.mosac_android.R.layout.activity_team_captain);
            bindBasicView();
            Button teammates = findViewById(com.example.administrator.mosac_android.R.id.teammates);
            Button joinlist = findViewById(com.example.administrator.mosac_android.R.id.joinlist);
            Button leave = findViewById(com.example.administrator.mosac_android.R.id.leave);
            teammates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webserviceHelper.queryTeammates(team.getTeam_id());
                }
            });
            joinlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webserviceHelper.queryJoinlist(team.getTeam_id());
                }
            });
            leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TeamActivity.this);
                    alertDialog.setMessage("你确定要解散队伍吗？");
                    alertDialog.setPositiveButton("确定解散", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            webserviceHelper.deleteTeam(team.getTeam_id());
                        }
                    });
                    alertDialog.setNegativeButton("我点错了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }
            });
        } else {
            // 查询是队员模式还是游客模式
            webserviceHelper.ifInTeam(team.getTeam_id(), user.getUser_id());
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInTeamEvent(InTeamEvent event) {
        if(event.getCorrect() == true) {
            // 队员模式
            setContentView(com.example.administrator.mosac_android.R.layout.activity_team_teammate);
            bindBasicView();
            Button teammates = findViewById(com.example.administrator.mosac_android.R.id.teammates);
            Button leave = findViewById(com.example.administrator.mosac_android.R.id.leave);
            teammates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webserviceHelper.queryTeammates(team.getTeam_id());
                }
            });
            leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TeamActivity.this);
                    alertDialog.setMessage("你确定要离开队伍吗？");
                    alertDialog.setPositiveButton("确定离开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            webserviceHelper.leaveTeam(team.getTeam_id(), user.getUser_id());
                        }
                    });
                    alertDialog.setNegativeButton("我点错了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }
            });
        }
        else {
            // 游客模式
            setContentView(com.example.administrator.mosac_android.R.layout.activity_team_visitor);
            bindBasicView();
            Button join = findViewById(com.example.administrator.mosac_android.R.id.join);
            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TeamActivity.this);
                    alertDialog.setMessage("你确定要申请加入队伍吗？");
                    alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            webserviceHelper.joinTeam(team.getTeam_id(), user.getUser_id());
                        }
                    });
                    alertDialog.setNegativeButton("我点错了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }
            });
        }
    }
    public void bindBasicView() {
        teamname = findViewById(com.example.administrator.mosac_android.R.id.teamname);
        description = findViewById(com.example.administrator.mosac_android.R.id.description);
        captainname = findViewById(com.example.administrator.mosac_android.R.id.captainname);
        time = findViewById(com.example.administrator.mosac_android.R.id.time);
        place = findViewById(com.example.administrator.mosac_android.R.id.place);
        nownum = findViewById(com.example.administrator.mosac_android.R.id.nownum);
        maxnum = findViewById(com.example.administrator.mosac_android.R.id.maxnum);
        teamname.setText(team.getTeamname());
        description.setText(team.getDescription());
        captainname.setText(team.getCaptain_name());
        time.setText(team.getTime());
        place.setText(team.getPlace());
        nownum.setText(Integer.toString(team.getNownumber()));
        maxnum.setText(Integer.toString(team.getMaxnumber()));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTeammatesListEvent(TeammatesListEvent event) {
        teammatesList = event.getTeammatesList();
        LayoutInflater factor = LayoutInflater.from(TeamActivity.this);
        View view_in = factor.inflate(com.example.administrator.mosac_android.R.layout.teammates_list, null);
        captainMSGname = view_in.findViewById(com.example.administrator.mosac_android.R.id.captain_name);
        captainMSGemail = view_in.findViewById(com.example.administrator.mosac_android.R.id.captain_email);
        captainMSGdepartment = view_in.findViewById(com.example.administrator.mosac_android.R.id.captain_department);
        captainMSGname.setText(captain.getUsername());
        captainMSGemail.setText(captain.getEmail());
        captainMSGdepartment.setText(captain.getDepartment());
        RecyclerView teammatesRecyclerView = view_in.findViewById(com.example.administrator.mosac_android.R.id.teammateslist);
        teammatesRecyclerView.setLayoutManager(new LinearLayoutManager(TeamActivity.this)); // 线性显示
        TeammateAdapter teammateAdapter = new TeammateAdapter(TeamActivity.this, com.example.administrator.mosac_android.R.layout.teammate_item, teammatesList);
        teammatesRecyclerView.setAdapter(teammateAdapter);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TeamActivity.this);
        alertDialog.setView(view_in);
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLeaveTeamEvent(LeaveTeamEvent event) {
        if(event.getSuccess() == true) {
            // 成功离开队伍
            Toast.makeText(TeamActivity.this, "你已经成功离开队伍", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJoinTeamEvent(JoinTeamEvent event) {
        if(event.getSuccess() == true) {
            // 成功申请加入队伍
            Toast.makeText(TeamActivity.this, "你已经成功申请加入队伍，请等待队长审核", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(TeamActivity.this, "申请失败，队伍人数已满或已经申请过了", Toast.LENGTH_LONG).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJoinListEvent(JoinListEvent event) {
        joinList = event.getJoinList();
        LayoutInflater factor = LayoutInflater.from(TeamActivity.this);
        View view_in = factor.inflate(com.example.administrator.mosac_android.R.layout.joinlist, null);
        RecyclerView teammatesRecyclerView = view_in.findViewById(com.example.administrator.mosac_android.R.id.teammateslist);
        teammatesRecyclerView.setLayoutManager(new LinearLayoutManager(TeamActivity.this)); // 线性显示
        joinitemAdapter = new JoinitemAdapter(TeamActivity.this, com.example.administrator.mosac_android.R.layout.joinlist_item, joinList);
        teammatesRecyclerView.setAdapter(joinitemAdapter);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TeamActivity.this);
        alertDialog.setView(view_in);
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteTeamEvent(DeleteTeamEvent event) {
        if(event.getSuccess() == true) {
            Toast.makeText(TeamActivity.this, "你已经成功解散了队伍", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(TeamActivity.this, "解散队伍失败", Toast.LENGTH_LONG).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJoinEvent(JoinEvent event) {
        if(event.getAgree() == true) {
            webserviceHelper.agreeJoin(team.getTeam_id(), event.getJoin_id());
            nownum.setText(Integer.toString(Integer.parseInt(nownum.getText().toString())+1));
        } else {
            webserviceHelper.denyJoin(team.getTeam_id(), event.getJoin_id());
        }
        joinList.remove(event.getDataposition());
        joinitemAdapter.notifyDataSetChanged();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJoinResponseEvent(JoinResponseEvent event) {
        if(event.getSuccess() == true) {
            Toast.makeText(TeamActivity.this, "操作执行成功", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(TeamActivity.this, "操作执行失败", Toast.LENGTH_LONG);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCaptainEvent(CaptainEvent event) {
        captain = event.getUser();
    }
}
