package com.example.administrator.mosac_android.event;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class FindPasswordEvent {
    private String password;
    public FindPasswordEvent(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
