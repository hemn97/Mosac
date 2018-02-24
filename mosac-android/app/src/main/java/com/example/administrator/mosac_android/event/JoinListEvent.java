package com.example.administrator.mosac_android.event;

import com.example.administrator.mosac_android.model.User;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class JoinListEvent {
    private List<User> joinList;
    public JoinListEvent(List<User> joinList) {
        this.joinList = joinList;
    }
    public List<User> getJoinList() {
        return joinList;
    }
}
