package com.example.administrator.mosac_android.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.model.User;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class TeammateAdapter extends RecyclerView.Adapter<TeammateAdapter.ViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private List<User> mDatas;
    private TeammateAdapter.OnItemClickListener mOnItemClickListener;
    public TeammateAdapter(Context context, int layoutId, List<User> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
    }
    @Override
    public TeammateAdapter.ViewHolder onCreateViewHolder (final ViewGroup parent, int viewType) {
        TeammateAdapter.ViewHolder viewHolder = TeammateAdapter.ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder (final TeammateAdapter.ViewHolder holder, int position) {
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

    public void convert(TeammateAdapter.ViewHolder holder, User user) {
        TextView name = holder.getView(R.id.name);
        TextView department = holder.getView(R.id.department);
        TextView email = holder.getView(R.id.email);
        name.setText(user.getUsername());
        department.setText(user.getDepartment());
        email.setText(user.getEmail());
    }

    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(TeammateAdapter.OnItemClickListener onItemClickListener) {
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
        public static TeammateAdapter.ViewHolder get(Context context, ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            TeammateAdapter.ViewHolder holder = new TeammateAdapter.ViewHolder(context, itemView, parent);
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
