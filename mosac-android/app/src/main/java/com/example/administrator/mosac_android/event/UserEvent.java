package com.example.administrator.mosac_android.event;

import com.example.administrator.mosac_android.model.User;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class UserEvent {
    private User user;
    public UserEvent(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
