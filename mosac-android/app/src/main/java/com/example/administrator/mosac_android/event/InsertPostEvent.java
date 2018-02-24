package com.example.administrator.mosac_android.event;

import com.example.administrator.mosac_android.model.Post;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class InsertPostEvent {
    private Post post;
    public InsertPostEvent(Post post) {
        this.post = post;
    }
    public Post getPost() {
        return post;
    }
}
