package com.example.administrator.mosac_android.event;

import com.example.administrator.mosac_android.model.Comment;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class CommentListEvent {
    private List<Comment> commentList;
    public CommentListEvent(List<Comment> commentList) {
        this.commentList = commentList;
    }
    public List<Comment> getCommentList() {
        return commentList;
    }
}
