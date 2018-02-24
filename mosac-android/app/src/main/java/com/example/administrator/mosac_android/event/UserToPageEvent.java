package com.example.administrator.mosac_android.event;

import com.example.administrator.mosac_android.model.User;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class UserToPageEvent {
    private User user;
    public UserToPageEvent(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
