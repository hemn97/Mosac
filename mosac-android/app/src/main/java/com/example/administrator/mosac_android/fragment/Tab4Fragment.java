package com.example.administrator.mosac_android.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.activity.CreatePostActivity;
import com.example.administrator.mosac_android.activity.CreateTeamActivity;
import com.example.administrator.mosac_android.activity.LoginActivity;
import com.example.administrator.mosac_android.activity.PostDetailActivity;
import com.example.administrator.mosac_android.activity.TeamDetailActivity;
import com.example.administrator.mosac_android.adapter.PostAdapter;
import com.example.administrator.mosac_android.adapter.TeamAdapter;
import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.bean.Team;
import com.example.administrator.mosac_android.bean.User;
import com.example.administrator.mosac_android.presenter.PostPresenter;
import com.example.administrator.mosac_android.presenter.TeamPresenter;
import com.example.administrator.mosac_android.presenter.UpdatePostPresenter;
import com.example.administrator.mosac_android.view.PostView;
import com.example.administrator.mosac_android.view.TeamView;
import com.example.administrator.mosac_android.view.UpdatePostView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class Tab4Fragment extends BaseFragment implements PostView, TeamView, UpdatePostView {
    private TextView username;
    private TextView number;
    private TextView insertPost;
    private TextView myPostList;
    private TextView myCreateTeam;
    private TextView myJoinTeam;
    private TextView createTeam;
    private Button exitButton;
    private User user;
    private PostPresenter postPresenter;
    private TeamPresenter teamPresenter;
    private UpdatePostPresenter updatePostPresenter;
    private View mView;
    private List<Post> postList;
    private List<Team> teamList;
    private RecyclerView postlistview;
    private PostAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        user = (User) getArguments().getSerializable("user");
        bindViews();
        // getPresenter
        postPresenter = new PostPresenter();
        postPresenter.attachView(this);
        teamPresenter = new TeamPresenter();
        teamPresenter.attachView(this);
        updatePostPresenter = new UpdatePostPresenter();
        updatePostPresenter.attachView(this);
        setListener();

        return mView;
    }

    public void initAllMembersView(Bundle savedInstanceState) {
    }
    public int getContentViewId() {
        return R.layout.fragment_tab4;
    }

    public void bindViews() {
        username = (TextView) mView.findViewById(R.id.username);
        number = (TextView) mView.findViewById(R.id.number);
        myPostList = (TextView) mView.findViewById(R.id.myPostList) ;
        myCreateTeam = (TextView) mView.findViewById(R.id.myCreateTeam) ;
        myJoinTeam = (TextView) mView.findViewById(R.id.myJoinTeam) ;
        createTeam = (TextView) mView.findViewById(R.id.CreateTeam) ;
        exitButton = (Button) mView.findViewById(R.id.exit);
        username.setText(user.getUsername());
        number.setText(user.getNumber());
        insertPost = mView.findViewById(R.id.insertPost);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        postPresenter.detachView();
        teamPresenter.detachView();
        updatePostPresenter.detachView();
    }

    public void setListener() {
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });
        myPostList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPresenter.getData("FindMyPosts", Integer.toString(user.getUser_id()));
            }
        });
        myCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamPresenter.getData("FindMyTeam", Integer.toString(user.getUser_id()));
            }
        });
        myJoinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamPresenter.getData("FindJoinTeam", Integer.toString(user.getUser_id()));
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
        insertPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreatePostActivity.class);
                intent.putExtra("user_id", user.getUser_id());
                startActivity(intent);
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            postList = (List<Post>) msg.getData().getParcelableArrayList("postList").get(0);
            LayoutInflater factor = LayoutInflater.from(getContext());
            View view_in = factor.inflate(R.layout.mypostlist_dialog, null);
            postlistview = view_in.findViewById(R.id.postlist);
            postlistview.setLayoutManager(new LinearLayoutManager(getContext())); // 线性显示
            postAdapter = new PostAdapter(getContext(), R.layout.postitem_ly, null);
            postlistview.setAdapter(postAdapter);
            postAdapter.updateAdapter(postList);
            postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    Post post = postList.get(position);
                    Intent intent = new Intent(getContext(), PostDetailActivity.class);
                    intent.putExtra("post", post);
                    intent.putExtra("user_id", user.getUser_id());
                    updatePostPresenter.getData("UpdatePostViews", Integer.toString(post.getPost_id()), Integer.toString(user.getUser_id()));
                    startActivity(intent);
                }
                @Override
                public void onLongClick(int position) {
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setView(view_in);
            alertDialog.show();
        }
    };

    private Handler teamHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            teamList = (List<Team>) msg.getData().getParcelableArrayList("teamList").get(0);
            LayoutInflater factor = LayoutInflater.from(getContext());
            View view_in = factor.inflate(R.layout.teamlist_ly, null);
            RecyclerView teamlist = view_in.findViewById(R.id.teamlist);
            teamlist.setLayoutManager(new LinearLayoutManager(getContext())); // 线性显示
            TeamAdapter teamAdapter = new TeamAdapter(getContext(), R.layout.teamitem_ly, null);
            teamlist.setAdapter(teamAdapter);
            teamAdapter.updateAdapter(teamList);
            teamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(getContext(), TeamDetailActivity.class);
                    intent.putExtra("user_id", user.getUser_id());
                    intent.putExtra("team", teamList.get(position));
                    startActivity(intent);
                }
                @Override
                public void onLongClick(int position) {
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setView(view_in);
            alertDialog.show();
        }
    };

    @Override
    public void showPosts(List<Post> data) {
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        ArrayList list = new ArrayList();
        list.add(data);
        bundle.putParcelableArrayList("postList", list);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    public void showTeams(List<Team> data) {
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        ArrayList list = new ArrayList();
        list.add(data);
        bundle.putParcelableArrayList("teamList", list);
        msg.setData(bundle);
        teamHandler.sendMessage(msg);
    }

    public void onOperationSuccess() {
        // 后台更新访问记录成功
        // do nothing
    }

}
