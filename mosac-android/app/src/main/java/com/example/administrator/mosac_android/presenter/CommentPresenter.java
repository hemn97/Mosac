package com.example.administrator.mosac_android.presenter;

import android.util.Log;

import com.example.administrator.mosac_android.bean.Comment;
import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.callback.Callback;
import com.example.administrator.mosac_android.model.CommentModel;
import com.example.administrator.mosac_android.model.DataModelManager;
import com.example.administrator.mosac_android.model.PostModel;
import com.example.administrator.mosac_android.view.CommentView;
import com.example.administrator.mosac_android.view.PostView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class CommentPresenter extends BasePresenter<CommentView> {
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
        DataModelManager.newInstance(CommentModel.class.getName()).setParams(params)
                .execute(new Callback<List<Comment>>() {
                    @Override
                    public void onSuccess(List<Comment> data) {
                        getView().showComments(data);
                    }
                    @Override
                    public void onFailure(String msg) {
                        //调用view接口提示失败信息
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
