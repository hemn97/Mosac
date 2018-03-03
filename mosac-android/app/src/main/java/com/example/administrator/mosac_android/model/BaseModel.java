package com.example.administrator.mosac_android.model;

import com.example.administrator.mosac_android.callback.Callback;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public abstract class BaseModel<T> {
    //数据请求参数
    protected String[] mParams;
    // WSDL文档中的URL
    protected final String WSDL="http://172.18.92.229:55879/MosacDBWebService.asmx";
    protected final String targetNameSpace="http://tempuri.org/";
    /**
     * 设置数据请求参数
     * @param args 参数数组
     */
    public BaseModel setParams(String... args){
        mParams = args;
        return this;
    }
    // 添加Callback并执行数据请求
    // 具体的数据请求由子类实现
    public abstract void execute(Callback<T> callback);

}
