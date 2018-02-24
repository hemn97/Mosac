package com.example.administrator.mosac_android.event;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class VerifyEvent {
    private boolean correct;
    public VerifyEvent(boolean correct) {
        this.correct = correct;
    }
    public boolean getCorrect() {
        return correct;
    }
}