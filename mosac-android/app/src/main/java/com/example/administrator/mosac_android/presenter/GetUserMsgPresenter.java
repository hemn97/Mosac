package com.example.administrator.mosac_android.presenter;

import android.util.Log;

import com.example.administrator.mosac_android.bean.User;
import com.example.administrator.mosac_android.callback.Callback;
import com.example.administrator.mosac_android.model.DataModelManager;
import com.example.administrator.mosac_android.model.GetUserMsgModel;
import com.example.administrator.mosac_android.model.UserModel;
import com.example.administrator.mosac_android.view.FindPasswdView;
import com.example.administrator.mosac_android.view.GetUserMsgView;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class GetUserMsgPresenter extends BasePresenter<GetUserMsgView> {
    /**
     * 获取网络数据
     * @param params 参数
     */
    public void getData(String... params){
        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        // 调用Model请求数据
        DataModelManager.newInstance(GetUserMsgModel.class.getName()).setParams(params)
                .execute(new Callback<User>() {
                    @Override
                    public void onSuccess(User user) {
                        getView().onGetUserMsg(user);
                    }
                    @Override
                    public void onFailure(String msg) {
                        // 调用view接口提示失败信息
                        if(isViewAttached()){
                            getView().showToast(msg);
                        }
                    }
                    @Override
                    public void onError() {
                        //调用view接口提示请求异常
                        if(isViewAttached()){
                            getView().showErr();
                        }
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
}