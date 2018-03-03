package com.example.administrator.mosac_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.bean.Post;
import com.example.administrator.mosac_android.bean.User;
import com.example.administrator.mosac_android.event.JoinEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class JoinitemAdapter extends RecyclerView.Adapter<JoinitemAdapter.ViewHolder>  {
    private Context mContext;
    private int mLayoutId;
    private List<User> mDatas;
    private JoinitemAdapter.OnItemClickListener mOnItemClickListener;
    public JoinitemAdapter(Context context, int layoutId, List<User> datas) {
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
    @Override
    public JoinitemAdapter.ViewHolder onCreateViewHolder (final ViewGroup parent, int viewType) {
        JoinitemAdapter.ViewHolder viewHolder = JoinitemAdapter.ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder (final JoinitemAdapter.ViewHolder holder, final int position) {
        final User user = mDatas.get(position);
        TextView name = holder.getView(R.id.name);
        TextView department = holder.getView(R.id.department);
        TextView email = holder.getView(R.id.email);
        Button agree = holder.getView(R.id.agree);
        Button deny = holder.getView(R.id.deny);
        name.setText(user.getUsername());
        department.setText(user.getDepartment());
        email.setText(user.getEmail());
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new JoinEvent(true, user.getUser_id(), position));
            }
        });
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new JoinEvent(false, user.getUser_id(), position));
            }
        });
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

    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(JoinitemAdapter.OnItemClickListener onItemClickListener) {
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
        public static JoinitemAdapter.ViewHolder get(Context context, ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            JoinitemAdapter.ViewHolder holder = new JoinitemAdapter.ViewHolder(context, itemView, parent);
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
