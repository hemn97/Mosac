package com.example.administrator.mosac_android.event;

import com.example.administrator.mosac_android.model.Team;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class OutdoorTeamListEvent {
    private List<Team> teamList;
    public OutdoorTeamListEvent(List<Team> teamList) {
        this.teamList = teamList;
    }
    public List<Team> getTeamList() {
        return teamList;
    }
}
