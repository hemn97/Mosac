package com.example.administrator.mosac_android.model;

import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.bean.User;
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

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class TeammateModel extends BaseModel<List<User>> {
    @Override
    public void execute(final Callback<List<User>> callback) {
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
                        case "FindAllTeammates":
                            soapObject.addProperty("team_id", mParams[1]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object findAllTeammatesRes = (Object) envelope.getResponse();
                            // 解析json
                            String json = findAllTeammatesRes.toString();
                            if(json.equals("anyType{}")) {
                                callback.onSuccess(null);
                                break;
                            }
                            JSONArray jsonArray = new JSONArray(json);
                            List<User> userList = new ArrayList<>(jsonArray.length());
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int user_id = jsonObject.optInt("user_id");
                                String name = jsonObject.optString("name");
                                String contact_number = jsonObject.optString("contact_number");
                                String email = jsonObject.optString("email");
                                String department = jsonObject.optString("department");
                                String number = jsonObject.optString("number");
                                User user = new User(user_id, name, number, contact_number, email, department);
                                userList.add(user);
                            }
                            callback.onSuccess(userList);
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
