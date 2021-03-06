package com.example.administrator.mosac_android.view;

import com.example.administrator.mosac_android.bean.Post;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public interface PostView extends BaseView{
    /**
     * 当数据请求成功后，调用此接口显示数据
     * @param data 数据源
     */
    void showPosts(List<Post> data);
}
