package com.example.administrator.mosac_android.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.activity.CreateTeamActivity;
import com.example.administrator.mosac_android.activity.InsertPostActivity;
import com.example.administrator.mosac_android.activity.LoginActivity;
import com.example.administrator.mosac_android.activity.PostActivity;
import com.example.administrator.mosac_android.activity.TeamActivity;
import com.example.administrator.mosac_android.adpater.PostAdapter;
import com.example.administrator.mosac_android.adpater.TeamAdapter;
import com.example.administrator.mosac_android.event.AddTeamEvent;
import com.example.administrator.mosac_android.event.MyPostListEvent;
import com.example.administrator.mosac_android.event.TeamlistDialogEvent;
import com.example.administrator.mosac_android.model.Post;
import com.example.administrator.mosac_android.model.Team;
import com.example.administrator.mosac_android.model.User;
import com.example.administrator.mosac_android.webservice.WebserviceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class Tab4Fragment extends Fragment {
    private TextView username;
    private TextView number;
    private TextView insertPost;
    private TextView myPostList;
    private TextView myCreateTeam;
    private TextView myJoinTeam;
    private TextView createTeam;
    private Button exitButton;
    private User user;
    private List<Post> postList;
    private PostAdapter postAdapter;
    private RecyclerView postlistview;
    private WebserviceHelper webserviceHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4, container,false);
        // 注册EventBus
        EventBus.getDefault().register(this);
        user = (User) getArguments().getSerializable("user");
        webserviceHelper = new WebserviceHelper();
        bindViews(view);
        return view;
    }
    public void bindViews(View v) {
        username = (TextView) v.findViewById(R.id.username);
        number = (TextView) v.findViewById(R.id.number);
        myPostList = (TextView) v.findViewById(R.id.myPostList) ;
        myCreateTeam = (TextView) v.findViewById(R.id.myCreateTeam) ;
        myJoinTeam = (TextView) v.findViewById(R.id.myJoinTeam) ;
        createTeam = (TextView) v.findViewById(R.id.CreateTeam) ;
        exitButton = (Button) v.findViewById(R.id.exit);
        username.setText(user.getUsername());
        number.setText(user.getNumber());
        insertPost = v.findViewById(R.id.insertPost);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });
        insertPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InsertPostActivity.class);
                startActivity(intent);
            }
        });
        myPostList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webserviceHelper.queryMyPost(user.getUser_id());
            }
        });
        myCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webserviceHelper.queryAddTeam(user.getUser_id());
            }
        });
        myJoinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webserviceHelper.queryJoinTeam(user.getUser_id());
            }
        });
        createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateTeamActivity.class);
                intent.putExtra("user_id", user.getUser_id());
                startActivity(intent);
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyPostListEvent(MyPostListEvent event) {
        LayoutInflater factor = LayoutInflater.from(getContext());
        View view_in = factor.inflate(R.layout.mypostlist_dialog, null);
        postlistview = view_in.findViewById(R.id.postlist);
        postlistview.setLayoutManager(new LinearLayoutManager(getContext())); // 线性显示
        postList = event.getPostList();
        postAdapter = new PostAdapter(getContext(), R.layout.postitem_ly, postList);
        postlistview.setAdapter(postAdapter);
        postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Post post = postList.get(position);
                post.setViews(post.getViews()+1);
                webserviceHelper.updatePostViews(post.getPost_id());
                Intent intent = new Intent(getContext(), PostActivity.class);
                intent.putExtra("post", post);
                intent.putExtra("user", user);
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {
            }
        });
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
//        alertDialog.setView(view_in);
//        alertDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        alertDialog.show();


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(view_in);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth;
        params.height = screenHeigh / 2 ;
        alertDialog.getWindow().setAttributes(params);//设置大小
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTeamlistDialogEvent(TeamlistDialogEvent event) {
        LayoutInflater factor = LayoutInflater.from(getContext());
        View view_in = factor.inflate(R.layout.teamlist_ly, null);
        RecyclerView teamlist = view_in.findViewById(R.id.teamlist);
        teamlist.setLayoutManager(new LinearLayoutManager(getContext())); // 线性显示
        final List<Team> teamList = event.getTeamList();
        TeamAdapter teamAdapter = new TeamAdapter(getContext(), R.layout.teamitem_ly, teamList);
        teamlist.setAdapter(teamAdapter);
        teamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), TeamActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("team", teamList.get(position));
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {
            }
        });
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
//        alertDialog.setMessage("我的队伍");
//        alertDialog.setView(view_in);
//        alertDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        alertDialog.show();


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(view_in);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth;
        params.height = screenHeigh / 2 ;
        alertDialog.getWindow().setAttributes(params);//设置大小
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddTeamEvent(AddTeamEvent event) {
        if(event.getSuccess() == true) {
            Toast.makeText(getContext(), "创建活动队伍成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "创建活动队伍失败", Toast.LENGTH_LONG).show();
        }
    }
}
