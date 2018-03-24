package com.example.administrator.mosac_android.model;

import android.util.Log;

import com.example.administrator.mosac_android.bean.Comment;
import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.callback.Callback;
import com.example.administrator.mosac_android.utils.ThreadPoolUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class CommentModel extends BaseModel<List<Comment>>{
    @Override
    public void execute(final Callback<List<Comment>> callback) {
        ThreadPoolUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SoapObject soapObject = new SoapObject(targetNameSpace, mParams[0]);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
                    HttpTransportSE httpTranstation = new HttpTransportSE(WSDL);
                    switch (mParams[0]){
                        case "FindAllComment":
                            soapObject.addProperty("post_id", mParams[1]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object result = (Object) envelope.getResponse();
                            // 解析json
                            String json = result.toString();
                            if(json.equals("anyType{}")) {
                                callback.onSuccess(null);
                                break;
                            }
                            JSONArray jsonArray = new JSONArray(json);
                            List<Comment> commentList = new ArrayList<>(jsonArray.length());
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int comment_id = jsonObject.optInt("comment_id");
                                int post_id = jsonObject.optInt("post_id");
                                String name = jsonObject.optString("name");
                                String comment = jsonObject.optString("comment");
                                String time = jsonObject.optString("time");
                                commentList.add(new Comment(comment_id, post_id, name, comment, time));
                            }
                            callback.onSuccess(commentList);
                            break;
                    }
                    callback.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
