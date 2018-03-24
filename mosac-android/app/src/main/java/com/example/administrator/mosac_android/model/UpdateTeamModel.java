package com.example.administrator.mosac_android.model;

import com.example.administrator.mosac_android.callback.Callback;
import com.example.administrator.mosac_android.utils.ThreadPoolUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class UpdateTeamModel extends BaseModel<String> {
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
                        case "DeleteTeam":
                            soapObject.addProperty("team_id", mParams[1]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object deleteRes = (Object) envelope.getResponse();
                            if (deleteRes.toString().equals("true")) {
                                callback.onSuccess("delete");
                            } else {
                                callback.onFailure("解散队伍失败！");
                            }
                            break;
                        case "LeaveTeam":
                            soapObject.addProperty("team_id", mParams[1]);
                            soapObject.addProperty("user_id", mParams[2]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object leaveRes = (Object) envelope.getResponse();
                            if (leaveRes.toString().equals("true")) {
                                callback.onSuccess("leave");
                            } else {
                                callback.onFailure("离开队伍失败！");
                            }
                            break;
                        case "InsertTeam":
                            soapObject.addProperty("captain_id", mParams[1]);
                            soapObject.addProperty("team_name", mParams[2]);
                            soapObject.addProperty("time", mParams[3]);
                            soapObject.addProperty("description", mParams[4]);
                            soapObject.addProperty("place", mParams[5]);
                            soapObject.addProperty("type", mParams[6]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object insertRes = (Object) envelope.getResponse();
                            if (insertRes.toString().equals("true")) {
                                callback.onSuccess("create");
                            } else {
                                callback.onFailure("创建队伍失败！");
                            }
                            break;
                        case "IfInTeam":
                            soapObject.addProperty("team_id", mParams[1]);
                            soapObject.addProperty("user_id", mParams[2]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object queryRes = (Object) envelope.getResponse();
                            if (queryRes.toString().equals("true")) {
                                callback.onSuccess("true");
                            } else {
                                callback.onSuccess("false");
                            }
                            break;
                        case "AgreeJoinTeam":
                            soapObject.addProperty("team_id", mParams[1]);
                            soapObject.addProperty("user_id", mParams[2]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object manageRes1 = (Object) envelope.getResponse();
                            if (manageRes1.toString().equals("true")) {
                                callback.onSuccess("true");
                            } else {
                                callback.onSuccess("false");
                            }
                            break;
                        case "DenyJoinTeam":
                            soapObject.addProperty("team_id", mParams[1]);
                            soapObject.addProperty("user_id", mParams[2]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object manageRes2 = (Object) envelope.getResponse();
                            if (manageRes2.toString().equals("true")) {
                                callback.onSuccess("true");
                            } else {
                                callback.onSuccess("false");
                            }
                            break;
                        case "JoinTeam":
                            soapObject.addProperty("team_id", mParams[1]);
                            soapObject.addProperty("user_id", mParams[2]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object joinRes = (Object) envelope.getResponse();
                            if (joinRes.toString().equals("true")) {
                                callback.onSuccess("join");
                            } else {
                                callback.onFailure("你已经申请过了，请耐心等待队长审核");
                            }
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
