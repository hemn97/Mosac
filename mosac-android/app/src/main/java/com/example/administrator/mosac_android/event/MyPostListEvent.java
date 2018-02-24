package com.example.administrator.mosac_android.event;

import com.example.administrator.mosac_android.model.Post;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class MyPostListEvent {
    private List<Post> postList;
    public MyPostListEvent(List<Post> postList) {
        this.postList = postList;
    }
    public List<Post> getPostList() {
        return postList;
    }
}
