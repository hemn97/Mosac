package com.example.administrator.mosac_android.model;

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
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class PostModel extends BaseModel<List<Post>>{
    @Override
    public void execute(final Callback<List<Post>> callback) {
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
                        case "FindAllPosts":
                            soapObject.addProperty("keyword", mParams[1]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object result = (Object) envelope.getResponse();
                            // 解析json
                            String json = result.toString();
                            if(json.equals("anyType{}")) {
                                callback.onSuccess(null);
                                break;
                            }
                            JSONArray jsonArray = new JSONArray(json);
                            List<Post> postList = new ArrayList<>(jsonArray.length());
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int post_id = jsonObject.optInt("post_id");
                                int author_id = jsonObject.optInt("author_id");
                                int views = jsonObject.optInt("views");
                                int comments = jsonObject.optInt("comments");
                                int ups = jsonObject.optInt("ups");
                                String title = jsonObject.optString("title");
                                String content = jsonObject.optString("content");
                                String name = jsonObject.optString("name");
                                String time = jsonObject.optString("time");
                                Post post = new Post(post_id, title, content, author_id, views, time, name, ups, comments);
                                postList.add(post);
                            }
                            callback.onSuccess(postList);
                            break;
                        case "FindMyPosts":
                            soapObject.addProperty("author_id", mParams[1]);
                            httpTranstation.call(targetNameSpace+mParams[0], envelope);
                            Object result2 = (Object) envelope.getResponse();
                            // 解析json
                            String json2 = result2.toString();
                            if(json2.equals("anyType{}")) {
                                callback.onSuccess(null);
                                break;
                            }
                            JSONArray jsonArray2 = new JSONArray(json2);
                            List<Post> postList2 = new ArrayList<>(jsonArray2.length());
                            for(int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject jsonObject = jsonArray2.getJSONObject(i);
                                int post_id = jsonObject.optInt("post_id");
                                int author_id = jsonObject.optInt("author_id");
                                int views = jsonObject.optInt("views");
                                int comments = jsonObject.optInt("comments");
                                int ups = jsonObject.optInt("ups");
                                String title = jsonObject.optString("title");
                                String content = jsonObject.optString("content");
                                String name = jsonObject.optString("name");
                                String time = jsonObject.optString("time");
                                Post post = new Post(post_id, title, content, author_id, views, time, name, ups, comments);
                                postList2.add(post);
                            }
                            callback.onSuccess(postList2);
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
