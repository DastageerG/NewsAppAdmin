package com.example.newsappadmin.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsappadmin.R;
import com.example.newsappadmin.model.Post;

import java.util.Date;
import java.util.List;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.ViewHolder>
{
    private Context context;
    private List<Post>postList;

    public PostsRecyclerViewAdapter(Context context, List<Post> postList)
    {
        this.context = context;
        this.postList = postList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewPostTitle, textViewPostTime;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewPostTitle = itemView.findViewById(R.id.textViewLayoutPostsRecycleViewPostTitle);
            textViewPostTime = itemView.findViewById(R.id.textViewLayoutPostsRecycleViewPostTime);
        }
    } /// ViewHolder closed

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layuot_posts_recycleview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Post post = postList.get(position);

        holder.textViewPostTitle.setText(post.getPostTitle());
        holder.textViewPostTime.setText(""+getRelationTime(post.getTimestamp()));

    }

    @Override
    public int getItemCount()
    {
        return postList.size();
    }

    public static final long AVERAGE_MONTH_IN_MILLIS = DateUtils.DAY_IN_MILLIS * 30;

    public static String getRelationTime(long time)
    {
        final long now = new Date().getTime();
        final long delta = now - time;
        long resolution;
        if (delta <= DateUtils.MINUTE_IN_MILLIS)
        {
            resolution = DateUtils.SECOND_IN_MILLIS;
        } else if (delta <= DateUtils.HOUR_IN_MILLIS)
        {
            resolution = DateUtils.MINUTE_IN_MILLIS;
        } else if (delta <= DateUtils.DAY_IN_MILLIS)
        {
            resolution = DateUtils.HOUR_IN_MILLIS;
        } else if (delta <= DateUtils.WEEK_IN_MILLIS)
        {
            resolution = DateUtils.DAY_IN_MILLIS;
        } else if (delta <= AVERAGE_MONTH_IN_MILLIS)
        {
            return Integer.toString((int) (delta / DateUtils.WEEK_IN_MILLIS)) + " weeks(s) ago";
        } else if (delta <= DateUtils.YEAR_IN_MILLIS)
        {
            return Integer.toString((int) (delta / AVERAGE_MONTH_IN_MILLIS)) + " month(s) ago";
        } else
        {
            return Integer.toString((int) (delta / DateUtils.YEAR_IN_MILLIS)) + " year(s) ago";
        }
        return DateUtils.getRelativeTimeSpanString(time, now, resolution).toString();
    } // getRelation Time closed



} // UsersRecycleView closed
