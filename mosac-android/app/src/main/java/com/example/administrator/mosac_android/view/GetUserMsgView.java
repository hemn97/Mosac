package com.example.administrator.mosac_android.view;

import com.example.administrator.mosac_android.bean.User;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public interface GetUserMsgView extends BaseView {
    /**
     * 当数据请求成功后，调用此接口显示数据
     * @param data 数据源
     */
    void onGetUserMsg(User user);
}