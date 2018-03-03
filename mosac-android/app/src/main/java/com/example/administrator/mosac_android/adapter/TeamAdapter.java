package com.example.administrator.mosac_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.bean.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder>  {
    private Context mContext;
    private int mLayoutId;
    private List<Team> mDatas;
    private TeamAdapter.OnItemClickListener mOnItemClickListener;
    public TeamAdapter(Context context, int layoutId, List<Team> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
        //判断是否为空
        if(datas == null){
            mDatas = new ArrayList<>();
        }else{
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }
    public void updateAdapter(List<Team> datas) {
        if (datas == null) {
            mDatas = new ArrayList<>();
        }
        else {
            mDatas.clear();
            mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder (final ViewGroup parent, int viewType) {
        TeamAdapter.ViewHolder viewHolder = TeamAdapter.ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder (final TeamAdapter.ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
        if(mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void convert(TeamAdapter.ViewHolder holder, Team team) {
        ImageView typeImg = holder.getView(R.id.type);
        TextView time = holder.getView(R.id.time);
        TextView place = holder.getView(R.id.place);
        TextView teamname = holder.getView(R.id.teamname);
        TextView number = holder.getView(R.id.number);
        TextView captainname = holder.getView(R.id.captainname);
        if (team.getType().equals("学习")) {
            typeImg.setImageResource(R.drawable.study);
        } else if (team.getType().equals("运动")) {
            typeImg.setImageResource(R.drawable.sports);
        } else if (team.getType().equals("户外")) {
            typeImg.setImageResource(R.drawable.outdoor);
        } else if (team.getType().equals("游戏")) {
            typeImg.setImageResource(R.drawable.game);
        }
        time.setText(team.getTime());
        place.setText(team.getPlace());
        teamname.setText(team.getTeamname());
        number.setText(Integer.toString(team.getNownumber()));
        captainname.setText(team.getCaptain_name());
    }

    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(TeamAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;
        private View mConvertView;
        public ViewHolder(Context context, View itemView, ViewGroup parent) {
            super(itemView);
            mConvertView = itemView;
            mViews = new SparseArray<View>();
        }
        public static TeamAdapter.ViewHolder get(Context context, ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            TeamAdapter.ViewHolder holder = new TeamAdapter.ViewHolder(context, itemView, parent);
            return holder;
        }
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }
}
