package com.example.administrator.mosac_android.event;

import com.example.administrator.mosac_android.model.User;

/**
 * Created by Administrator on 2018/1/7 0007.
 */

public class CaptainEvent {
    private User user;
    public CaptainEvent(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
