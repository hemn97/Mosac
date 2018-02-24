package com.example.administrator.mosac_android.event;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class RegisterEvent {
    private boolean success;
    public RegisterEvent(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }
}
