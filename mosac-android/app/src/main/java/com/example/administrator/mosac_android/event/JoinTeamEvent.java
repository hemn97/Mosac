package com.example.administrator.mosac_android.event;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class JoinTeamEvent {
    private boolean success;
    public JoinTeamEvent(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }
}