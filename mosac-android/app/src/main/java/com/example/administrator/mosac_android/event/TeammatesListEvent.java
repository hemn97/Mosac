package com.example.administrator.mosac_android.event;

import com.example.administrator.mosac_android.model.User;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class TeammatesListEvent {
    private List<User> teammatesList;
    public TeammatesListEvent(List<User> teammatesList) {
        this.teammatesList = teammatesList;
    }
    public List<User> getTeammatesList() {
        return teammatesList;
    }
}
