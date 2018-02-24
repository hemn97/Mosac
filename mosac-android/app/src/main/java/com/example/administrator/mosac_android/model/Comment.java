package com.example.administrator.mosac_android.model;

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
    public Comment(int comment_id, int post_id, int user_id, String content, String time, String name) {
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.content = content;
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
