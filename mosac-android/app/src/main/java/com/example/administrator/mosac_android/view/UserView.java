package com.example.administrator.mosac_android.view;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public interface UserView extends BaseView{
    /**
     * 当数据请求成功后，调用此接口显示数据
     * @param data 数据源
     */
    void onOperationSuccess();
}