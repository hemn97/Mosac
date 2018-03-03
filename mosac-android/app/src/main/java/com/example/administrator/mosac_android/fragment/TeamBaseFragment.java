package com.example.administrator.mosac_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.activity.TeamDetailActivity;
import com.example.administrator.mosac_android.adapter.TeamAdapter;
import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.bean.Team;
import com.example.administrator.mosac_android.presenter.TeamPresenter;
import com.example.administrator.mosac_android.view.TeamView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class TeamBaseFragment extends BaseFragment implements TeamView {
    protected RecyclerView teamlistRecyclerView;
    protected TeamAdapter teamAdapter;
    protected View mRootView;
    private TeamPresenter teamPresenter;
    private String type;
    private int user_id;
    private List<Team> teamList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = super.onCreateView(inflater, container, savedInstanceState);
        bindViews();
        // Adapter
        teamAdapter = new TeamAdapter(getContext(), R.layout.teamitem_ly, null);
        teamlistRecyclerView.setAdapter(teamAdapter);
        teamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), TeamDetailActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("team", teamList.get(position));
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {
            }
        });
        // presenter
        teamPresenter = new TeamPresenter();
        teamPresenter.attachView(this);
        type = getArguments().getString("type");
        user_id = getArguments().getInt("user_id");
        return mRootView;
    }

    public void initAllMembersView(Bundle savedInstanceState) {}
    public int getContentViewId() {
        return R.layout.teamlist_ly;
    }

    public void bindViews() {
        teamlistRecyclerView = mRootView.findViewById(R.id.teamlist);
        teamlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // 线性显示
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            teamAdapter.updateAdapter((List<Team>) msg.getData().getParcelableArrayList("teamList").get(0));
        }
    };

    public void showTeams(List<Team> data) {
        teamList = data;
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        ArrayList list = new ArrayList();
        list.add(data);
        bundle.putParcelableArrayList("teamList", list);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    @Override
    public void onStart() {
        super.onStart();
        teamPresenter.getData("FindAllTeam", type);
    }

}
