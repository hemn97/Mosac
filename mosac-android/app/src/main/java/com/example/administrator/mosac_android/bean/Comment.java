package com.example.administrator.mosac_android.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class Comment implements Serializable {
    private int comment_id;
    private int post_id;
    private int user_id;
    private String content;
    private String time;
    private String name;
    public Comment(int comment_id, int post_id, String name, String comment, String time) {
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.content = comment;
        this.time = time;
        this.name = name;
    }
    public String getContent() {
        return content;
    }
    public String getName() {
        return name;
    }
    public String getTime() {
        return time;
    }
}
