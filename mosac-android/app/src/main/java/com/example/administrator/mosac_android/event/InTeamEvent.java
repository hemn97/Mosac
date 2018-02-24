package com.example.administrator.mosac_android.event;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class InTeamEvent {
    private boolean correct;
    public InTeamEvent(boolean correct) {
        this.correct = correct;
    }
    public boolean getCorrect() {
        return correct;
    }
}
