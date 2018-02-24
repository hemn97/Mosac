package com.example.administrator.mosac_android.event;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class DeletePostEvent {
    private boolean success;
    public DeletePostEvent(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }
}
