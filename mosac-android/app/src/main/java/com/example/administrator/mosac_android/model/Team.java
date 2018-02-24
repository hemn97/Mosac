package com.example.administrator.mosac_android.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class Team implements Serializable {
    private int team_id;
    private String teamname;
    private String time;
    private String place;
    private int nownumber;
    private int maxnumber;
    private String description;
    private int captain_id;
    private String captain_name;
    private String type;
    public Team(int team_id, String teamname, String time, String place, int nownumber, int maxnumber, String description, int captain_id, String captain_name, String type) {
        this.team_id = team_id;
        this.teamname = teamname;
        this.time = time;
        this.place = place;
        this.nownumber = nownumber;
        this.maxnumber = maxnumber;
        this.description = description;
        this.captain_id = captain_id;
        this.captain_name = captain_name;
        this.type = type;
    }
    public void setTeam_id(int team_id) { this.team_id = team_id; }
    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public void setNownumber(int nownumber) {
        this.nownumber = nownumber;
    }
    public void setMaxnumber(int maxnumber) {
        this.maxnumber = maxnumber;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCaptain_id(int captain_id) {
        this.captain_id = captain_id;
    }
    public void setCaptain_name(String captain_name) {
        this.captain_name = captain_name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getCaptain_id() { return captain_id; }
    public String getTeamname() { return teamname; }
    public String getTime() { return time; }
    public String getPlace() { return place; }
    public int getNownumber() { return nownumber; }
    public int getMaxnumber() { return maxnumber; }
    public String getDescription() { return description; }
    public String getCaptain_name() { return captain_name; }
    public String getType() { return type; }
    public int getTeam_id() { return team_id; }

}
