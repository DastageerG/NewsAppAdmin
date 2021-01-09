package com.example.newsappadmin.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.newsappadmin.R;
import com.example.newsappadmin.adapters.PostsRecyclerViewAdapter;
import com.example.newsappadmin.model.Post;
import com.example.newsappadmin.utils.Constants;
import com.example.newsappadmin.utils.PostsCallBack;

import java.net.ContentHandler;
import java.util.List;

public class AllPostsActivity extends AppCompatActivity
{
    private static final String TAG =  "1111";
    private Context context = AllPostsActivity.this;
    private String child;
    private FirebaseMethods firebaseMethods;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts);
        progressBar = findViewById(R.id.progressBarAllPostsActivity);

        recyclerView = findViewById(R.id.recycleViewAllPostsActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ///getCategory form intent
        if(getIntent().getExtras().getString(Constants.child)!=null)
        {
            child = getIntent().getExtras().getString(Constants.child);
        }
                                                        // put Category into method constructor
        firebaseMethods = new FirebaseMethods(context,progressBar,child);

        firebaseMethods.getPosts(new PostsCallBack()
        {
            @Override
            public void getPosts(List<Post> postList)
            {
                if(postList!=null)
                {
                    PostsRecyclerViewAdapter adapter = new PostsRecyclerViewAdapter(context,postList);
                    recyclerView.setAdapter(adapter);
                }
                else
                {
                    Log.d(TAG, "getPosts: posList is null");
                }
            }
        });


    } // onCreate closed

}