package com.example.administrator.mosac_android.presenter;

import com.example.administrator.mosac_android.callback.Callback;
import com.example.administrator.mosac_android.model.DataModelManager;
import com.example.administrator.mosac_android.model.UserModel;
import com.example.administrator.mosac_android.view.UserView;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class UserPresenter extends BasePresenter<UserView> {
    /**
     * 获取网络数据
     * @param params 参数
     */
    public void getData(String... params){
        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        // 显示正在加载进度条
        getView().showLoading();
        // 调用Model请求数据
        DataModelManager.newInstance(UserModel.class.getName()).setParams(params)
                .execute(new Callback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        getView().onOperationSuccess();
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
                        // 隐藏正在加载进度条
                        if(isViewAttached()){
                            getView().hideLoading();
                        }
                    }

                });
    }
}

