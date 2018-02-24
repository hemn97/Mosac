package com.example.administrator.mosac_android.webservice;

import android.os.AsyncTask;

import com.example.administrator.mosac_android.event.AddTeamEvent;
import com.example.administrator.mosac_android.event.CaptainEvent;
import com.example.administrator.mosac_android.event.CommentListEvent;
import com.example.administrator.mosac_android.event.CommentPostEvent;
import com.example.administrator.mosac_android.event.DeletePostEvent;
import com.example.administrator.mosac_android.event.DeleteTeamEvent;
import com.example.administrator.mosac_android.event.FindPasswordEvent;
import com.example.administrator.mosac_android.event.GameTeamListEvent;
import com.example.administrator.mosac_android.event.InTeamEvent;
import com.example.administrator.mosac_android.event.JoinListEvent;
import com.example.administrator.mosac_android.event.JoinResponseEvent;
import com.example.administrator.mosac_android.event.JoinTeamEvent;
import com.example.administrator.mosac_android.event.LeaveTeamEvent;
import com.example.administrator.mosac_android.event.LightupPostEvent;
import com.example.administrator.mosac_android.event.MyPostListEvent;
import com.example.administrator.mosac_android.event.OutdoorTeamListEvent;
import com.example.administrator.mosac_android.event.PostListEvent;
import com.example.administrator.mosac_android.event.RegisterEvent;
import com.example.administrator.mosac_android.event.SportsTeamListEvent;
import com.example.administrator.mosac_android.event.StudyTeamListEvent;
import com.example.administrator.mosac_android.event.TeamListEvent;
import com.example.administrator.mosac_android.event.TeamlistDialogEvent;
import com.example.administrator.mosac_android.event.TeammatesListEvent;
import com.example.administrator.mosac_android.event.UpdatePostListEvent;
import com.example.administrator.mosac_android.event.UserEvent;
import com.example.administrator.mosac_android.event.VerifyEvent;
import com.example.administrator.mosac_android.model.Comment;
import com.example.administrator.mosac_android.model.Post;
import com.example.administrator.mosac_android.model.Team;
import com.example.administrator.mosac_android.model.User;

import org.greenrobot.eventbus.EventBus;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class WebserviceHelper {
    //WSDL文档中的命名空间
    private static final String targetNameSpace="http://tempuri.org/";
    //WSDL文档中的URL
    private static final String WSDL="http://172.18.92.229:51234/AndroidFinalWebService.asmx";
    //调用的方法名
    private static final String queryUserWithUserId="QueryUserWithUserId";
    private static final String registerUser ="RegisterUser";
    private static final String verifyUser="VerifyUser";
    private static final String queryUserWithNumber="QueryUserWithNumber";
    private static final String queryPost="QueryPost";
    private static final String updatePostViews="UpdatePostViews";
    private static final String insertPost="InsertPost";
    private static final String queryComment="QueryComment";
    private static final String lightupPost="LightupPost";
    private static final String commentPost="CommentPost";
    private static final String deletePost="DeletePost";
    private static final String queryMyPost="QueryMyPost";
    private static final String findPassword="FindPassword";
    private static final String queryTeam="QueryTeam";
    private static final String addTeam="AddTeam";
    private static final String deleteTeam="DeleteTeam";
    private static final String queryTeammates="QueryTeammates";
    private static final String leaveTeam="LeaveTeam";
    private static final String joinTeam="JoinTeam";
    private static final String agreeJoin="AgreeJoin";
    private static final String denyJoin="DenyJoin";
    private static final String queryJoinlist="QueryJoinlist";
    private static final String ifInTeam="IfInTeam";
    private static final String queryTeamByType="QueryTeamByType";
    private static final String queryJoinTeam="QueryJoinTeam";
    private static final String queryAddTeam="QueryAddTeam";
    // User
    private int user_id;
    private String number;
    private String password;
    private String username;
    private String contact_number;
    private String email;
    private String department;
    // Post
    private String keyword;
    private int post_id;
    private String title;
    private String content;
    private int author_id;
    private int views;
    private String time;
    private int lightNum;
    private int commentNum;
    // Comment
    private int comment_id;
    // Team
    private int team_id;
    private String teamname;
    private String place;
    private int nownumber;
    private int maxnumber;
    private String description;
    private int captain_id;
    private String captain_name;
    private String type;

    public void verifyUser(String number, String password){
        this.number = number;
        this.password = password;
        new verifyUserAsyncTask().execute();
    }
    public void registerUser(String username, String number, String password, String contact_number, String email, String department){
        this.number = number;
        this.password = password;
        this.username = username;
        this.contact_number = contact_number;
        this.email = email;
        this.department = department;
        new registerUserAsyncTask().execute();
    }
    public void queryUserWithNumber(String number){
        this.number = number;
        new queryUserWithNumberAsyncTask().execute();
    }
    public void queryUserWithId(int captain_id){
        this.captain_id = captain_id;
        new queryUserWithIdAsyncTask().execute();
    }
    public void queryPostList(String keyword){
        this.keyword = keyword;
        new queryPostAsyncTask().execute();
    }
    public void updatePostViews(int post_id){
        this.post_id = post_id;
        new updatePostViewsAsyncTask().execute();
    }
    public void insertPost(String title, String content, int author_id, String time){
        this.title = title;
        this.content = content;
        this.author_id = author_id;
        this.time = time;
        new insertPostAsyncTask().execute();
    }
    public void getCommentList(int post_id){
        this.post_id = post_id;
        new queryCommentAsyncTask().execute();
    }
    public void lightupPost(int post_id, int user_id){
        this.post_id = post_id;
        this.user_id = user_id;
        new lightupPostAsyncTask().execute();
    }
    public void commentPost(int post_id, int user_id, String content, String time){
        this.post_id = post_id;
        this.user_id = user_id;
        this.content = content;
        this.time = time;
        new commentPostAsyncTask().execute();
    }
    public void deletePost(int post_id){
        this.post_id = post_id;
        new deletePostAsyncTask().execute();
    }
    public void queryMyPost(int author_id){
        this.author_id = author_id;
        new queryMyPostAsyncTask().execute();
    }
    public void findPassword(String number, String username, String contact_number, String email){
        this.number = number;
        this.username = username;
        this.contact_number = contact_number;
        this.email = email;
        new findPasswordAsyncTask().execute();
    }
    public void queryTeam(){
        new queryTeamAsyncTask().execute();
    }
    public void queryTeamByType(String type){
        this.type = type;
        new queryTeamByTypeAsyncTask().execute();
    }
    public void queryJoinTeam(int teammate_id){
        this.user_id = teammate_id;
        new queryJoinTeamAsyncTask().execute();
    }
    public void queryAddTeam(int captain_id){
        this.captain_id = captain_id;
        new queryAddTeamAsyncTask().execute();
    }
    public void deleteTeam(int team_id){
        this.team_id = team_id;
        new deleteTeamAsyncTask().execute();
    }
    public void addTeam(String teamname, String type, String description, String time, String place, int number, int captain_id){
        this.teamname = teamname;
        this.type = type;
        this.description = description;
        this.time = time;
        this.place = place;
        this.maxnumber = number;
        this.captain_id = captain_id;
        new addTeamAsyncTask().execute();
    }
    public void queryTeammates(int team_id){
        this.team_id = team_id;
        new queryTeammatesAsyncTask().execute();
    }
    public void leaveTeam(int team_id, int teammate_id){
        this.team_id = team_id;
        this.user_id = teammate_id;
        new leaveTeamAsyncTask().execute();
    }
    public void joinTeam(int team_id, int join_id){
        this.team_id = team_id;
        this.user_id = join_id;
        new joinTeamAsyncTask().execute();
    }
    public void agreeJoin(int team_id, int join_id){
        this.team_id = team_id;
        this.user_id = join_id;
        new agreeJoinAsyncTask().execute();
    }
    public void denyJoin(int team_id, int join_id){
        this.team_id = team_id;
        this.user_id = join_id;
        new denyJoinAsyncTask().execute();
    }
    public void queryJoinlist(int team_id){
        this.team_id = team_id;
        new queryJoinlistAsyncTask().execute();
    }
    public void ifInTeam(int team_id, int teammate_id){
        this.team_id = team_id;
        this.user_id = teammate_id;
        new ifInTeamAsyncTask().execute();
    }
    class verifyUserAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,verifyUser);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("number", number);
            soapObject.addProperty("password", password);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;

            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+verifyUser, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new VerifyEvent(true));
                }
                else {
                    EventBus.getDefault().post(new VerifyEvent(false));
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class registerUserAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,registerUser);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("username", username);
            soapObject.addProperty("number", number);
            soapObject.addProperty("password", password);
            soapObject.addProperty("contact_number", contact_number);
            soapObject.addProperty("email", email);
            soapObject.addProperty("department", department);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;

            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+registerUser, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new RegisterEvent(true));
                }
                else {
                    EventBus.getDefault().post(new RegisterEvent(false));
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryUserWithNumberAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryUserWithNumber);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("number", number);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;

            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryUserWithNumber, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                int count = result.getPropertyCount();
                for(int index=0;index<count;index=index+6){
                    user_id = Integer.parseInt(result.getProperty(index).toString());
                    username = result.getProperty(index+1).toString();
                    number = result.getProperty(index+2).toString();
                    contact_number = result.getProperty(index+3).toString();
                    email = result.getProperty(index+4).toString();
                    department = result.getProperty(index+5).toString();
                    User user = new User(user_id, username, number, contact_number, email, department);
                    EventBus.getDefault().post(new UserEvent(user));
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryUserWithIdAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryUserWithUserId);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("user_id", captain_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;

            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryUserWithUserId, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                int count = result.getPropertyCount();
                for(int index=0;index<count;index=index+5){
                    String captain_name = result.getProperty(index).toString();
                    String captain_number = result.getProperty(index+1).toString();
                    String captain_contact_number = result.getProperty(index+2).toString();
                    String captain_email = result.getProperty(index+3).toString();
                    String captain_department = result.getProperty(index+4).toString();
                    User user = new User(captain_name, captain_email, captain_department);
                    EventBus.getDefault().post(new CaptainEvent(user));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryPostAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryPost);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("keyword", keyword);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryPost, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                List<Post> postList = new ArrayList<>();
                int count = result.getPropertyCount();
                for(int index=0;index<count;index=index+9){
                    post_id = Integer.parseInt(result.getProperty(index).toString());
                    title = result.getProperty(index+1).toString();
                    content = result.getProperty(index+2).toString();
                    author_id = Integer.parseInt(result.getProperty(index+3).toString());
                    views = Integer.parseInt(result.getProperty(index+4).toString());
                    time = result.getProperty(index+5).toString();
                    username = result.getProperty(index+6).toString();
                    if (!result.getProperty(index+7).toString().equals("anyType{}")) {
                        lightNum = Integer.parseInt(result.getProperty(index+7).toString());
                    }
                    else {
                        lightNum = 0;
                    }
                    if(!result.getProperty(index+8).toString().equals("anyType{}")) {
                        commentNum = Integer.parseInt(result.getProperty(index+8).toString());
                    }
                    else {
                        commentNum = 0;
                    }
                    Post post = new Post(post_id, title, content, author_id, views, time, username, lightNum, commentNum);
                    postList.add(post);
                }
                EventBus.getDefault().post(new PostListEvent(postList));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class updatePostViewsAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,updatePostViews);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("post_id", post_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+updatePostViews, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class insertPostAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,insertPost);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("title", title);
            soapObject.addProperty("content", content);
            soapObject.addProperty("author_id", author_id);
            soapObject.addProperty("time", time);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+insertPost, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                EventBus.getDefault().post(new UpdatePostListEvent());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryCommentAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryComment);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("post_id", post_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryComment, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                List<Comment> commentList = new ArrayList<>();
                int count = result.getPropertyCount();
                for(int index=0;index<count;index=index+6){
                    comment_id = Integer.parseInt(result.getProperty(index).toString());
                    post_id = Integer.parseInt(result.getProperty(index+1).toString());
                    user_id = Integer.parseInt(result.getProperty(index+2).toString());
                    content = result.getProperty(index+3).toString();
                    time = result.getProperty(index+4).toString();
                    username = result.getProperty(index+5).toString();
                    Comment comment = new Comment(comment_id, post_id, user_id, content, time, username);
                    commentList.add(comment);
                }
                EventBus.getDefault().post(new CommentListEvent(commentList));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class lightupPostAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,lightupPost);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("post_id", post_id);
            soapObject.addProperty("user_id", user_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+lightupPost, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new LightupPostEvent(true));
                }
                else {
                    EventBus.getDefault().post(new LightupPostEvent(false));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class commentPostAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,commentPost);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("post_id", post_id);
            soapObject.addProperty("user_id", user_id);
            soapObject.addProperty("content", content);
            soapObject.addProperty("time", time);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+commentPost, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new CommentPostEvent(true));
                }
                else {
                    EventBus.getDefault().post(new CommentPostEvent(false));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class deletePostAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,deletePost);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("post_id", post_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+deletePost, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new DeletePostEvent(true));
                }
                else {
                    EventBus.getDefault().post(new DeletePostEvent(false));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryMyPostAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryMyPost);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("author_id", author_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryMyPost, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                List<Post> postList = new ArrayList<>();
                int count = result.getPropertyCount();
                for(int index=0;index<count;index=index+9){
                    post_id = Integer.parseInt(result.getProperty(index).toString());
                    title = result.getProperty(index+1).toString();
                    content = result.getProperty(index+2).toString();
                    author_id = Integer.parseInt(result.getProperty(index+3).toString());
                    views = Integer.parseInt(result.getProperty(index+4).toString());
                    time = result.getProperty(index+5).toString();
                    username = result.getProperty(index+6).toString();
                    if (!result.getProperty(index+7).toString().equals("anyType{}")) {
                        lightNum = Integer.parseInt(result.getProperty(index+7).toString());
                    }
                    else {
                        lightNum = 0;
                    }
                    if(!result.getProperty(index+8).toString().equals("anyType{}")) {
                        commentNum = Integer.parseInt(result.getProperty(index+8).toString());
                    }
                    else {
                        commentNum = 0;
                    }
                    Post post = new Post(post_id, title, content, author_id, views, time, username, lightNum, commentNum);
                    postList.add(post);
                }
                EventBus.getDefault().post(new MyPostListEvent(postList));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class findPasswordAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,findPassword);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("number", number);
            soapObject.addProperty("username", username);
            soapObject.addProperty("contact_number", contact_number);
            soapObject.addProperty("email", email);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+findPassword, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                int count = result.getPropertyCount();
                if(count == 0) {
                    password = "";
                }
                else {
                    password = result.getProperty(0).toString();
                }
                EventBus.getDefault().post(new FindPasswordEvent(password));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryTeamAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryTeam);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryTeam, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                int count = result.getPropertyCount();
                List<Team> teamList = new ArrayList<>();
                for(int index=0;index<count;index=index+10){
                    team_id = Integer.parseInt(result.getProperty(index).toString());
                    type = result.getProperty(index+1).toString();
                    description = result.getProperty(index+2).toString();
                    teamname = result.getProperty(index+3).toString();
                    time = result.getProperty(index+4).toString();
                    place = result.getProperty(index+5).toString();
                    maxnumber = Integer.parseInt(result.getProperty(index+6).toString());
                    captain_id = Integer.parseInt(result.getProperty(index+7).toString());
                    captain_name = result.getProperty(index+8).toString();
                    if(result.getProperty(index+9).toString().equals("anyType{}")) {
                        nownumber = 1;
                    } else {
                        nownumber = Integer.parseInt(result.getProperty(index+9).toString());
                    }
                    Team team = new Team(team_id, teamname, time, place, nownumber, maxnumber, description, captain_id, captain_name, type);
                    teamList.add(team);
                }
                EventBus.getDefault().post(new TeamListEvent(teamList));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryTeamByTypeAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryTeamByType);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("type", type);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryTeamByType, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                int count = result.getPropertyCount();
                List<Team> teamList = new ArrayList<>();
                for(int index=0;index<count;index=index+10){
                    team_id = Integer.parseInt(result.getProperty(index).toString());
                    type = result.getProperty(index+1).toString();
                    description = result.getProperty(index+2).toString();
                    teamname = result.getProperty(index+3).toString();
                    time = result.getProperty(index+4).toString();
                    place = result.getProperty(index+5).toString();
                    maxnumber = Integer.parseInt(result.getProperty(index+6).toString());
                    captain_id = Integer.parseInt(result.getProperty(index+7).toString());
                    captain_name = result.getProperty(index+8).toString();
                    if(result.getProperty(index+9).toString().equals("anyType{}")) {
                        nownumber = 1;
                    } else {
                        nownumber = Integer.parseInt(result.getProperty(index+9).toString());
                    }
                    Team team = new Team(team_id, teamname, time, place, nownumber, maxnumber, description, captain_id, captain_name, type);
                    teamList.add(team);
                }
                if(type.equals("学习")) {
                    EventBus.getDefault().post(new StudyTeamListEvent(teamList));
                } else if(type.equals("运动")) {
                    EventBus.getDefault().post(new SportsTeamListEvent(teamList));
                } else if(type.equals("户外")) {
                    EventBus.getDefault().post(new OutdoorTeamListEvent(teamList));
                } else if(type.equals("游戏")) {
                    EventBus.getDefault().post(new GameTeamListEvent(teamList));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryJoinTeamAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryJoinTeam);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("teammate_id", user_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryJoinTeam, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                int count = result.getPropertyCount();
                List<Team> teamList = new ArrayList<>();
                for(int index=0;index<count;index=index+10){
                    team_id = Integer.parseInt(result.getProperty(index).toString());
                    type = result.getProperty(index+1).toString();
                    description = result.getProperty(index+2).toString();
                    teamname = result.getProperty(index+3).toString();
                    time = result.getProperty(index+4).toString();
                    place = result.getProperty(index+5).toString();
                    maxnumber = Integer.parseInt(result.getProperty(index+6).toString());
                    captain_id = Integer.parseInt(result.getProperty(index+7).toString());
                    captain_name = result.getProperty(index+8).toString();
                    if(result.getProperty(index+9).toString().equals("anyType{}")) {
                        nownumber = 1;
                    } else {
                        nownumber = Integer.parseInt(result.getProperty(index+9).toString());
                    }
                    Team team = new Team(team_id, teamname, time, place, nownumber, maxnumber, description, captain_id, captain_name, type);
                    teamList.add(team);
                }
                EventBus.getDefault().post(new TeamlistDialogEvent(teamList));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryAddTeamAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryAddTeam);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("captain_id", captain_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryAddTeam, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                int count = result.getPropertyCount();
                List<Team> teamList = new ArrayList<>();
                for(int index=0;index<count;index=index+10){
                    team_id = Integer.parseInt(result.getProperty(index).toString());
                    type = result.getProperty(index+1).toString();
                    description = result.getProperty(index+2).toString();
                    teamname = result.getProperty(index+3).toString();
                    time = result.getProperty(index+4).toString();
                    place = result.getProperty(index+5).toString();
                    maxnumber = Integer.parseInt(result.getProperty(index+6).toString());
                    captain_id = Integer.parseInt(result.getProperty(index+7).toString());
                    captain_name = result.getProperty(index+8).toString();
                    if(result.getProperty(index+9).toString().equals("anyType{}")) {
                        nownumber = 1;
                    } else {
                        nownumber = Integer.parseInt(result.getProperty(index+9).toString());
                    }
                    Team team = new Team(team_id, teamname, time, place, nownumber, maxnumber, description, captain_id, captain_name, type);
                    teamList.add(team);
                }
                EventBus.getDefault().post(new TeamlistDialogEvent(teamList));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class deleteTeamAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,deleteTeam);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("team_id", team_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+deleteTeam, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new DeleteTeamEvent(true));
                }
                else {
                    EventBus.getDefault().post(new DeleteTeamEvent(false));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class addTeamAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,addTeam);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("team_name", teamname);
            soapObject.addProperty("type", type);
            soapObject.addProperty("description", description);
            soapObject.addProperty("time", time);
            soapObject.addProperty("place", place);
            soapObject.addProperty("number", maxnumber);
            soapObject.addProperty("captain_id", captain_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+addTeam, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new AddTeamEvent(true));
                }
                else {
                    EventBus.getDefault().post(new AddTeamEvent(false));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryTeammatesAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryTeammates);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("team_id", team_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryTeammates, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                int count = result.getPropertyCount();
                List<User> teammatesList = new ArrayList<>();
                for(int index=0;index<count;index=index+3){
                    username = result.getProperty(index).toString();
                    email = result.getProperty(index+1).toString();
                    department = result.getProperty(index+2).toString();
                    User teammates = new User(username, email, department);
                    teammatesList.add(teammates);
                }
                EventBus.getDefault().post(new TeammatesListEvent(teammatesList));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class leaveTeamAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,leaveTeam);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("team_id", team_id);
            soapObject.addProperty("teammate_id", user_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+leaveTeam, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new LeaveTeamEvent(true));
                }
                else {
                    EventBus.getDefault().post(new LeaveTeamEvent(false));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class joinTeamAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,joinTeam);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("team_id", team_id);
            soapObject.addProperty("join_id", user_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+joinTeam, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new JoinTeamEvent(true));
                }
                else {
                    EventBus.getDefault().post(new JoinTeamEvent(false));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class agreeJoinAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,agreeJoin);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("team_id", team_id);
            soapObject.addProperty("join_id", user_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+agreeJoin, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new JoinResponseEvent(true));
                }
                else {
                    EventBus.getDefault().post(new JoinResponseEvent(false));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class denyJoinAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,denyJoin);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("team_id", team_id);
            soapObject.addProperty("join_id", user_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+denyJoin, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new JoinResponseEvent(true));
                }
                else {
                    EventBus.getDefault().post(new JoinResponseEvent(false));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class queryJoinlistAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,queryJoinlist);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("team_id", team_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+queryJoinlist, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                //下面对结果进行解析
                int count = result.getPropertyCount();
                List<User> joinList = new ArrayList<>();
                for(int index=0;index<count;index=index+4){
                    user_id = Integer.parseInt(result.getProperty(index).toString());
                    username = result.getProperty(index+1).toString();
                    department = result.getProperty(index+2).toString();
                    email = result.getProperty(index+3).toString();
                    User teammates = new User(user_id, username, department, email);
                    joinList.add(teammates);
                }
                EventBus.getDefault().post(new JoinListEvent(joinList));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class ifInTeamAsyncTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object... params)
        {
            SoapObject soapObject=new SoapObject(targetNameSpace,ifInTeam);
            //request.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
            soapObject.addProperty("team_id", team_id);
            soapObject.addProperty("teammate_id", user_id);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;
            HttpTransportSE httpTranstation=new HttpTransportSE(WSDL);
            try {
                httpTranstation.call(targetNameSpace+ifInTeam, envelope);
                Object object = (Object)envelope.getResponse();
                //下面对结果进行解析
                if(object.toString().equals("true")) {
                    EventBus.getDefault().post(new InTeamEvent(true));
                }
                else {
                    EventBus.getDefault().post(new InTeamEvent(false));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
}
