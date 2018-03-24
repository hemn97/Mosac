package com.example.administrator.mosac_android.model;

import android.util.Log;

import com.example.administrator.mosac_android.bean.User;
import com.example.administrator.mosac_android.callback.Callback;
import com.example.administrator.mosac_android.utils.ThreadPoolUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class GetUserMsgModel extends BaseModel<User> {
    @Override
    public void execute(final Callback<User> callback) {
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
                        case "GetUserMessage":
                            soapObject.addProperty("number", mParams[1]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object getUserRes = (Object) envelope.getResponse();
                            // 解析json
                            String json = getUserRes.toString();
                            JSONArray jsonArray = new JSONArray(json);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            int user_id = jsonObject.optInt("user_id");
                            String name = jsonObject.optString("name");
                            String contact_number = jsonObject.optString("contact_number");
                            String email = jsonObject.optString("email");
                            String department = jsonObject.optString("department");
                            String number = jsonObject.optString("number");
                            User user = new User(user_id, name, number, contact_number, email, department);
                            callback.onSuccess(user);
                            break;
                        case "GetUserMessage2":
                            soapObject.addProperty("user_id", mParams[1]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object getUserRes2 = (Object) envelope.getResponse();
                            // 解析json
                            String json2 = getUserRes2.toString();
                            JSONArray jsonArray2 = new JSONArray(json2);
                            JSONObject jsonObject2 = jsonArray2.getJSONObject(0);
                            int user_id2 = jsonObject2.optInt("user_id");
                            String name2 = jsonObject2.optString("name");
                            String contact_number2 = jsonObject2.optString("contact_number");
                            String email2 = jsonObject2.optString("email");
                            String department2 = jsonObject2.optString("department");
                            String number2 = jsonObject2.optString("number");
                            User user2 = new User(user_id2, name2, number2, contact_number2, email2, department2);
                            callback.onSuccess(user2);
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
