package com.example.administrator.mosac_android.event;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class JoinResponseEvent {
    private boolean success;
    public JoinResponseEvent(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }
}
