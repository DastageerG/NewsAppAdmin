package com.example.newsappadmin.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.newsappadmin.R;
import com.example.newsappadmin.adapters.UsersRecyclerViewAdapter;
import com.example.newsappadmin.model.User;
import com.example.newsappadmin.utils.UsersCallBack;

import java.util.List;

public class UsersActivity extends AppCompatActivity
{

    private Context context = UsersActivity.this;
    public static final String TAG = "1111";
    private FirebaseMethods firebaseMethods;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        progressBar = findViewById(R.id.progressBarUsersActivity);
        firebaseMethods = new FirebaseMethods(context,progressBar);
        recyclerView = findViewById(R.id.recycleViewUserActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);


        firebaseMethods.getAllUsers(new UsersCallBack()
        {
            @Override
            public void getUserData(User user)
            {

            }

            @Override
            public void getAllUsers(List<User> userList)
            {
                if(userList!=null)
                {

                    UsersRecyclerViewAdapter adapter = new UsersRecyclerViewAdapter(context,userList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }// if closed
            }
        }
    );

    } // onCreate closed

} // UserActivity closed