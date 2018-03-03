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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.adapter.JoinitemAdapter;
import com.example.administrator.mosac_android.adapter.TeammateAdapter;
import com.example.administrator.mosac_android.bean.Team;
import com.example.administrator.mosac_android.bean.User;
import com.example.administrator.mosac_android.event.JoinEvent;
import com.example.administrator.mosac_android.presenter.ApplicantPresenter;
import com.example.administrator.mosac_android.presenter.GetUserMsgPresenter;
import com.example.administrator.mosac_android.presenter.ManageTeamPresenter;
import com.example.administrator.mosac_android.presenter.TeamStatusPresenter;
import com.example.administrator.mosac_android.presenter.TeammatePresenter;
import com.example.administrator.mosac_android.presenter.UpdateTeamPresenter;
import com.example.administrator.mosac_android.utils.ToastUtils;
import com.example.administrator.mosac_android.view.ApplicantView;
import com.example.administrator.mosac_android.view.GetUserMsgView;
import com.example.administrator.mosac_android.view.ManageTeamView;
import com.example.administrator.mosac_android.view.TeamStatusView;
import com.example.administrator.mosac_android.view.TeammateView;
import com.example.administrator.mosac_android.view.UpdateTeamView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class TeamDetailActivity extends BaseActivity
        implements TeammateView, GetUserMsgView, ApplicantView, UpdateTeamView, TeamStatusView, ManageTeamView {
    private TextView teamname;
    private TextView description;
    private TextView captainname;
    private TextView time;
    private TextView place;
    private TextView nownum;
    private TextView captainMSGname;
    private TextView captainMSGemail;
    private TextView captainMSGdepartment;
    private JoinitemAdapter joinitemAdapter;
    private List<User> teammatesList;
    private List<User> joinList;
    private Team team;
    private int user_id;
    private User captain;
    private TeammatePresenter teammatePresenter;
    private GetUserMsgPresenter getUserMsgPresenter;
    private ApplicantPresenter applicantPresenter;
    private UpdateTeamPresenter updateTeamPresenter;
    private TeamStatusPresenter teamStatusPresenter;
    private ManageTeamPresenter manageTeamPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        team = (Team) getIntent().getSerializableExtra("team");
        user_id = getIntent().getIntExtra("user_id", -1);
        ToastUtils.initToast(getContext());
        // Presenter
        teammatePresenter = new TeammatePresenter();
        teammatePresenter.attachView(this);
        getUserMsgPresenter = new GetUserMsgPresenter();
        getUserMsgPresenter.attachView(this);
        applicantPresenter = new ApplicantPresenter();
        applicantPresenter.attachView(this);
        updateTeamPresenter = new UpdateTeamPresenter();
        updateTeamPresenter.attachView(this);
        teamStatusPresenter = new TeamStatusPresenter();
        teamStatusPresenter.attachView(this);
        manageTeamPresenter = new ManageTeamPresenter();
        manageTeamPresenter.attachView(this);
        // 注册EventBus
        EventBus.getDefault().register(this);
        getUserMsgPresenter.getData("GetUserMessage2", Integer.toString(team.getCaptain_id()));
        selectMode();
    }

    private void selectMode() {
        if (user_id == team.getCaptain_id()) {  // 队长模式
            setContentView(R.layout.activity_team_captain);
            bindBasicView();
            Button teammates = findViewById(R.id.teammates);
            Button joinlist = findViewById(R.id.joinlist);
            Button leave = findViewById(R.id.leave);
            teammates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    teammatePresenter.getData("FindAllTeammates", Integer.toString(team.getTeam_id()));
                }
            });
            joinlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    applicantPresenter.getData("FindAllApplicants", Integer.toString(team.getTeam_id()));
                }
            });
            leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TeamDetailActivity.this);
                    alertDialog.setMessage("你确定要解散队伍吗？");
                    alertDialog.setPositiveButton("确定解散", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateTeamPresenter.getData("DeleteTeam", Integer.toString(team.getTeam_id()));
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
            teamStatusPresenter.getData("IfInTeam", Integer.toString(team.getTeam_id()), Integer.toString(user_id));
        }
    }

    private void bindBasicView() {
        teamname = findViewById(R.id.teamname);
        description = findViewById(R.id.description);
        captainname = findViewById(R.id.captainname);
        time = findViewById(R.id.time);
        place = findViewById(R.id.place);
        nownum = findViewById(R.id.nownum);
        teamname.setText(team.getTeamname());
        description.setText(team.getDescription());
        captainname.setText(team.getCaptain_name());
        time.setText(team.getTime());
        place.setText(team.getPlace());
        nownum.setText(Integer.toString(team.getNownumber()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        teammatePresenter.detachView();
        getUserMsgPresenter.detachView();
        applicantPresenter.detachView();
        updateTeamPresenter.detachView();
        manageTeamPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LayoutInflater factor = LayoutInflater.from(TeamDetailActivity.this);
            View view_in = factor.inflate(R.layout.teammates_list, null);
            captainMSGname = view_in.findViewById(R.id.captain_name);
            captainMSGemail = view_in.findViewById(R.id.captain_email);
            captainMSGdepartment = view_in.findViewById(R.id.captain_department);
            captainMSGname.setText(team.getCaptain_name());
            captainMSGemail.setText(captain.getEmail());
            captainMSGdepartment.setText(captain.getDepartment());
            RecyclerView teammatesRecyclerView = view_in.findViewById(R.id.teammateslist);
            teammatesRecyclerView.setLayoutManager(new LinearLayoutManager(TeamDetailActivity.this)); // 线性显示
            TeammateAdapter teammateAdapter = new TeammateAdapter(TeamDetailActivity.this, R.layout.teammate_item, teammatesList);
            teammatesRecyclerView.setAdapter(teammateAdapter);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(TeamDetailActivity.this);
            alertDialog.setView(view_in);
            alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }
    };

    private Handler applicantHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            joinList = (List<User>) msg.getData().getParcelableArrayList("applicantsList").get(0);
            LayoutInflater factor = LayoutInflater.from(TeamDetailActivity.this);
            View view_in = factor.inflate(R.layout.joinlist, null);
            RecyclerView teammatesRecyclerView = view_in.findViewById(R.id.teammateslist);
            teammatesRecyclerView.setLayoutManager(new LinearLayoutManager(TeamDetailActivity.this)); // 线性显示
            joinitemAdapter = new JoinitemAdapter(TeamDetailActivity.this, R.layout.joinlist_item, joinList);
            teammatesRecyclerView.setAdapter(joinitemAdapter);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(TeamDetailActivity.this);
            alertDialog.setView(view_in);
            alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }
    };

    private Handler inTeamHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setContentView(R.layout.activity_team_teammate);
            bindBasicView();
            Button teammates = findViewById(R.id.teammates);
            Button leave = findViewById(R.id.leave);
            teammates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    teammatePresenter.getData("FindAllTeammates", Integer.toString(team.getTeam_id()));
                }
            });
            leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TeamDetailActivity.this);
                    alertDialog.setMessage("你确定要离开队伍吗？");
                    alertDialog.setPositiveButton("确定离开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        updateTeamPresenter.getData("LeaveTeam", Integer.toString(team.getTeam_id()),
                                Integer.toString(user_id));
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
    };

    private Handler notInTeamHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setContentView(R.layout.activity_team_visitor);
            bindBasicView();
            Button join = findViewById(R.id.join);
            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TeamDetailActivity.this);
                    alertDialog.setMessage("你确定要申请加入队伍吗？");
                    alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        updateTeamPresenter.getData("JoinTeam", Integer.toString(team.getTeam_id()),
                                Integer.toString(user_id));
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
    };

    public void showTeammates(List<User> data) {
        teammatesList = data;
        mHandler.sendMessage(new Message());
    }

    public void onGetUserMsg(User user) {
        // 获取到队长信息
        captain = user;
    }

    public void showApplicants(List<User> data) {
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        ArrayList list = new ArrayList();
        list.add(data);
        bundle.putParcelableArrayList("applicantsList", list);
        msg.setData(bundle);
        applicantHandler.sendMessage(msg);
    }

    public void onUpdateTeamSuccess(String data) {
        if(data.equals("leave")) {
            // 离开队伍成功
            ToastUtils.showToast(getContext(), "你已经成功离开了队伍", Toast.LENGTH_SHORT);
            finish();
        } else if(data.equals("delete")){
            // 解散队伍成功
            ToastUtils.showToast(getContext(), "你已经成功解散了队伍", Toast.LENGTH_SHORT);
            finish();
        } else if(data.equals("join")) {
            // 申请加入队伍成功
            ToastUtils.showToast(getContext(), "你已经成功申请加入该队伍", Toast.LENGTH_SHORT);
        }
    }

    public void isInTeam(boolean status) {
        if(status) {    // 在队伍中
            inTeamHandler.sendMessage(new Message());
        } else {    // 不在队伍中
            notInTeamHandler.sendMessage(new Message());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJoinEvent(JoinEvent event) {
        if(event.getAgree() == true) {
            manageTeamPresenter.getData("AgreeJoinTeam", Integer.toString(team.getTeam_id()), Integer.toString(event.getJoin_id()));
            nownum.setText(Integer.toString(Integer.parseInt(nownum.getText().toString())+1));
        } else {
            manageTeamPresenter.getData("DenyJoinTeam", Integer.toString(team.getTeam_id()), Integer.toString(event.getJoin_id()));
        }
        joinList.remove(event.getDataposition());
        joinitemAdapter.notifyDataSetChanged();
    }

    public void onManageTeamSuccess(){
        ToastUtils.showToast(getContext(), "操作执行成功", Toast.LENGTH_SHORT);
    }
}
