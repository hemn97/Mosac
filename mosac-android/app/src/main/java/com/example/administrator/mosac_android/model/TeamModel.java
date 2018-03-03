package com.example.administrator.mosac_android.model;

import android.util.Log;

import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.bean.Team;
import com.example.administrator.mosac_android.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class TeamModel extends BaseModel<List<Team>>{
    @Override
    public void execute(final Callback<List<Team>> callback) {
        // 获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SoapObject soapObject = new SoapObject(targetNameSpace, mParams[0]);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
                    HttpTransportSE httpTranstation = new HttpTransportSE(WSDL);
                    switch (mParams[0]){
                        case "FindAllTeam":
                            soapObject.addProperty("type", mParams[1]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object result = (Object) envelope.getResponse();
                            // 解析json
                            String json = result.toString();
                            if(json.equals("anyType{}")) {
                                callback.onSuccess(null);
                                break;
                            }
                            JSONArray jsonArray = new JSONArray(json);
                            List<Team> teamList = new ArrayList<>(jsonArray.length());
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int team_id = jsonObject.optInt("team_id");
                                int captain_id = jsonObject.optInt("captain_id");
                                int teammatesCnt = jsonObject.optInt("teammatesCnt");
                                String team_name = jsonObject.optString("team_name");
                                String time = jsonObject.optString("time");
                                String description = jsonObject.optString("description");
                                String place = jsonObject.optString("place");
                                String captain_name = jsonObject.optString("captain_name");
                                String type = jsonObject.optString("type");
                                Team team = new Team(team_id, team_name, time, place, teammatesCnt, description, captain_id, captain_name, type);
                                teamList.add(team);
                            }
                            callback.onSuccess(teamList);
                            break;
                        case "FindMyTeam":
                            soapObject.addProperty("captain_id", mParams[1]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object result2 = (Object) envelope.getResponse();
                            // 解析json
                            String json2 = result2.toString();
                            if(json2.equals("anyType{}")) {
                                callback.onSuccess(null);
                                break;
                            }
                            JSONArray jsonArray2 = new JSONArray(json2);
                            List<Team> teamList2 = new ArrayList<>(jsonArray2.length());
                            for(int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject jsonObject = jsonArray2.getJSONObject(i);
                                int team_id = jsonObject.optInt("team_id");
                                int captain_id = jsonObject.optInt("captain_id");
                                int teammatesCnt = jsonObject.optInt("teammatesCnt");
                                String team_name = jsonObject.optString("team_name");
                                String time = jsonObject.optString("time");
                                String description = jsonObject.optString("description");
                                String place = jsonObject.optString("place");
                                String captain_name = jsonObject.optString("captain_name");
                                String type = jsonObject.optString("type");
                                Team team = new Team(team_id, team_name, time, place, teammatesCnt, description, captain_id, captain_name, type);
                                teamList2.add(team);
                            }
                            callback.onSuccess(teamList2);
                            break;
                        case "FindJoinTeam":
                            soapObject.addProperty("user_id", mParams[1]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object result3 = (Object) envelope.getResponse();
                            // 解析json
                            String json3 = result3.toString();
                            if(json3.equals("anyType{}")) {
                                callback.onSuccess(null);
                                break;
                            }
                            JSONArray jsonArray3 = new JSONArray(json3);
                            List<Team> teamList3 = new ArrayList<>(jsonArray3.length());
                            for(int i = 0; i < jsonArray3.length(); i++) {
                                JSONObject jsonObject = jsonArray3.getJSONObject(i);
                                int team_id = jsonObject.optInt("team_id");
                                int captain_id = jsonObject.optInt("captain_id");
                                int teammatesCnt = jsonObject.optInt("teammatesCnt");
                                String team_name = jsonObject.optString("team_name");
                                String time = jsonObject.optString("time");
                                String description = jsonObject.optString("description");
                                String place = jsonObject.optString("place");
                                String captain_name = jsonObject.optString("captain_name");
                                String type = jsonObject.optString("type");
                                Team team = new Team(team_id, team_name, time, place, teammatesCnt, description, captain_id, captain_name, type);
                                teamList3.add(team);
                            }
                            callback.onSuccess(teamList3);
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
