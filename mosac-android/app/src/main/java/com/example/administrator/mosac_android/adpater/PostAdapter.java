package com.example.administrator.mosac_android.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mosac_android.R;
import com.example.administrator.mosac_android.model.Post;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private List<Post> mDatas;
    private OnItemClickListener mOnItemClickListener;
    public PostAdapter(Context context, int layoutId, List<Post> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
    }
    @Override
    public ViewHolder onCreateViewHolder (final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder (final ViewHolder holder, int position) {
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

    public void convert(ViewHolder holder, Post post) {
        TextView lightNum = holder.getView(R.id.lightNum);
        lightNum.setText(Integer.toString(post.getLightNum()));
        TextView commentNum = holder.getView(R.id.commentNum);
        commentNum.setText(Integer.toString(post.getCommentNum()));
        TextView title = holder.getView(R.id.title);
        title.setText(post.getTitle());
        TextView author = holder.getView(R.id.author);
        author.setText(post.getAuthor());
        TextView time = holder.getView(R.id.time);
        time.setText(post.getTime());
    }

    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
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
        public static ViewHolder get(Context context, ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            ViewHolder holder = new ViewHolder(context, itemView, parent);
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
