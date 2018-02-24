package com.example.administrator.mosac_android.event;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class JoinEvent {
    private boolean agree;
    private int join_id;
    private int dataposition;
    public JoinEvent(boolean agree, int join_id, int dataposition) {
        this.agree = agree;
        this.join_id = join_id;
        this.dataposition = dataposition;
    }
    public boolean getAgree() {
        return agree;
    }
    public int getJoin_id() {
        return join_id;
    }
    public int getDataposition() {
        return dataposition;
    }
}
