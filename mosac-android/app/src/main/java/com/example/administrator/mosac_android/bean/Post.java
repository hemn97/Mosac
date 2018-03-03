package com.example.administrator.mosac_android.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class Post implements Serializable {
    private int post_id;
    private String title;
    private String content;
    private int author_id;
    private int views;
    private String time;
    private int lightNum;
    private int commentNum;
    private String author;
    public Post(int post_id, String title, String content, int author_id, int views, String time, String username, int lightNum, int commentNum) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.author_id = author_id;
        this.views = views;
        this.time = time;
        this.author = username;
        this.lightNum = lightNum;
        this.commentNum = commentNum;
    }
    public Post(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }
    public int getPost_id() {return post_id;}
    public String getTitle() {return title;}
    public String getContent() { return content;}
    public int getAuthor_id() { return author_id;}
    public int getViews() { return views;}
    public String getTime() { return time;}
    public String getAuthor() { return author;}
    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }
    public void setViews(int views) {
        this.views = views;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public int getLightNum() {return lightNum;}
    public void setLightNum(int lightNum) {
        this.lightNum = lightNum;
    }
    public int getCommentNum() {return commentNum;}
    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
    public void setAuthor() {this.author =  author;}
}
