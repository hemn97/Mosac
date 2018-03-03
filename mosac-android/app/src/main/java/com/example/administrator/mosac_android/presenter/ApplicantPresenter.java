package com.example.administrator.mosac_android.presenter;

import com.example.administrator.mosac_android.bean.User;
import com.example.administrator.mosac_android.callback.Callback;
import com.example.administrator.mosac_android.model.ApplicantModel;
import com.example.administrator.mosac_android.model.DataModelManager;
import com.example.administrator.mosac_android.model.TeammateModel;
import com.example.administrator.mosac_android.view.ApplicantView;
import com.example.administrator.mosac_android.view.TeammateView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class ApplicantPresenter extends BasePresenter<ApplicantView> {
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
        DataModelManager.newInstance(ApplicantModel.class.getName()).setParams(params)
                .execute(new Callback<List<User>>() {
                    @Override
                    public void onSuccess(List<User> data) {
                        getView().showApplicants(data);
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
