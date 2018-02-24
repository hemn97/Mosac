package com.example.administrator.mosac_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.activity.TeamActivity;
import com.example.administrator.mosac_android.adpater.TeamAdapter;
import com.example.administrator.mosac_android.event.StudyTeamListEvent;
import com.example.administrator.mosac_android.model.Team;
import com.example.administrator.mosac_android.model.User;
import com.example.administrator.mosac_android.webservice.WebserviceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class StudyFragment extends Fragment {
    private WebserviceHelper webserviceHelper;
    private List<Team> teamList;
    private RecyclerView teamlistRecyclerView;
    private TeamAdapter teamAdapter;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teamlist_ly,container,false);
        user = (User) getArguments().getSerializable("user");
        // 注册EventBus
        EventBus.getDefault().register(this);
        webserviceHelper = new WebserviceHelper();
        bindViews(view);
        return view;
    }
    public void bindViews(View v) {
        teamlistRecyclerView = v.findViewById(R.id.teamlist);
        teamlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // 线性显示
    }
    @Override
    public void onStart() {
        super.onStart();
        webserviceHelper.queryTeamByType("学习");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStudyTeamListEvent(StudyTeamListEvent event) {
        teamList = event.getTeamList();
        teamAdapter = new TeamAdapter(getContext(), R.layout.teamitem_ly, teamList);
        teamlistRecyclerView.setAdapter(teamAdapter);
        teamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), TeamActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("team", teamList.get(position));
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}