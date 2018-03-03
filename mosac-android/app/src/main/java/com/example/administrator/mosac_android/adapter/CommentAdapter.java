package com.example.administrator.mosac_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.bean.Comment;
import com.example.administrator.mosac_android.bean.Post;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private List<Comment> mDatas;
    private CommentAdapter.OnItemClickListener mOnItemClickListener;
    public CommentAdapter(Context context, int layoutId, List<Comment> datas) {
        mContext = context;
        mLayoutId = layoutId;
        //判断是否为空
        if(datas == null){
            mDatas = new ArrayList<>();
        }else{
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }
    public void updateAdapter(List<Comment> datas) {
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
    public CommentAdapter.ViewHolder onCreateViewHolder (final ViewGroup parent, int viewType) {
        CommentAdapter.ViewHolder viewHolder = CommentAdapter.ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder (final CommentAdapter.ViewHolder holder, int position) {
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

    public void convert(CommentAdapter.ViewHolder holder, Comment comment) {
        TextView name = holder.getView(R.id.name);
        TextView time = holder.getView(R.id.time);
        TextView content = holder.getView(R.id.comment);
        name.setText(comment.getName());
        time.setText(comment.getTime());
        content.setText(comment.getContent());
    }

    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(CommentAdapter.OnItemClickListener onItemClickListener) {
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
        public static CommentAdapter.ViewHolder get(Context context, ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            CommentAdapter.ViewHolder holder = new CommentAdapter.ViewHolder(context, itemView, parent);
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
