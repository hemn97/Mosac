package com.example.administrator.mosac_android.model;

import com.example.administrator.mosac_android.bean.User;
import com.example.administrator.mosac_android.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class UpdatePostModel extends BaseModel<String> {
    @Override
    public void execute(final Callback<String> callback) {
        // 获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SoapObject soapObject = new SoapObject(targetNameSpace, mParams[0]);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(soapObject);
                    HttpTransportSE httpTranstation = new HttpTransportSE(WSDL);
                    switch (mParams[0]){
                        case "LightupPost":
                            soapObject.addProperty("post_id", mParams[1]);
                            soapObject.addProperty("user_id", mParams[2]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object lightupRes = (Object) envelope.getResponse();
                            if (lightupRes.toString().equals("true")) {
                                callback.onSuccess("");
                            } else {
                                callback.onFailure("您已经点亮过了，请勿重复点亮");
                            }
                            break;
                        case "CommentPost":
                            soapObject.addProperty("post_id", mParams[1]);
                            soapObject.addProperty("user_id", mParams[2]);
                            soapObject.addProperty("comment", mParams[3]);
                            soapObject.addProperty("time", mParams[4]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object commentRes = (Object) envelope.getResponse();
                            if (commentRes.toString().equals("true")) {
                                callback.onSuccess("");
                            } else {
                                callback.onFailure("评论失败");
                            }
                            break;
                        case "UpdatePostViews":
                            soapObject.addProperty("post_id", mParams[1]);
                            soapObject.addProperty("user_id", mParams[2]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            break;
                        case "InsertPost":
                            soapObject.addProperty("title", mParams[1]);
                            soapObject.addProperty("content", mParams[2]);
                            soapObject.addProperty("author_id", mParams[3]);
                            soapObject.addProperty("time", mParams[4]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object insertRes = (Object) envelope.getResponse();
                            if (insertRes.toString().equals("true")) {
                                callback.onSuccess("");
                            } else {
                                callback.onFailure("发帖失败");
                            }
                            break;
                    }
                    callback.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
