package com.example.administrator.mosac_android.model;

import android.content.Intent;
import android.util.Log;

import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.callback.Callback;
import com.example.administrator.mosac_android.utils.ThreadPoolUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class UserModel extends BaseModel<String>{
    @Override
    public void execute(final Callback<String> callback) {
        ThreadPoolUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SoapObject soapObject = new SoapObject(targetNameSpace, mParams[0]);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(soapObject);
                    HttpTransportSE httpTranstation = new HttpTransportSE(WSDL);
                    switch (mParams[0]){
                        case "LoginValidate":
                            soapObject.addProperty("number", mParams[1]);
                            soapObject.addProperty("password", mParams[2]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object validateRest = (Object) envelope.getResponse();
                            if (validateRest.toString().equals("true")) {
                                callback.onSuccess("登录成功");
                            } else {
                                callback.onFailure("学号或密码错误，请检查");
                            }
                            break;
                        case "RegisterUser":
                            soapObject.addProperty("username", mParams[1]);
                            soapObject.addProperty("number", mParams[2]);
                            soapObject.addProperty("password", mParams[3]);
                            soapObject.addProperty("contact_number", mParams[4]);
                            soapObject.addProperty("email", mParams[5]);
                            soapObject.addProperty("department", mParams[6]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object registerRes = (Object) envelope.getResponse();
                            if (registerRes.toString().equals("true")) {
                                callback.onSuccess("注册成功");
                            } else {
                                callback.onFailure("注册失败");
                            }
                            break;
                        case "FindPassword":
                            soapObject.addProperty("number", mParams[1]);
                            soapObject.addProperty("username", mParams[2]);
                            soapObject.addProperty("contact_number", mParams[3]);
                            soapObject.addProperty("email", mParams[4]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object findPasswdRes = (Object) envelope.getResponse();
                            // 解析json
                            String json = findPasswdRes.toString();
                            if(json.equals("anyType{}")) {
                                callback.onFailure("您输入的验证信息有误，请检查");
                                break;
                            }
                            JSONArray jsonArray = new JSONArray(json);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String password = jsonObject.optString("password");
                            callback.onSuccess(password);
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
