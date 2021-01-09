package com.example.newsappadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsappadmin.R;
import com.example.newsappadmin.home.UserDetailsActivity;
import com.example.newsappadmin.model.Post;
import com.example.newsappadmin.model.User;
import com.example.newsappadmin.utils.Constants;

import java.util.List;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>
{
    private Context context;
    private List<User>usersList;

    public UsersRecyclerViewAdapter(Context context, List<User> usersList)
    {
        this.context = context;
        this.usersList = usersList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewName;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewLayoutUsersRecycleViewName);
        }
    } /// ViewHolder closed

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_users_recycleview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final User user = usersList.get(position);
        holder.textViewName.setText(user.getUserName());


        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, UserDetailsActivity.class);
                intent.putExtra(Constants.uid,user.getUserID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return usersList.size();
    }
} // UsersRecycleView closed
