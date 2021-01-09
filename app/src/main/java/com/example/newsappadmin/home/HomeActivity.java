package com.example.newsappadmin.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.newsappadmin.R;
import com.example.newsappadmin.auth.LogInActivity;
import com.example.newsappadmin.home.AllPostsActivity;
import com.example.newsappadmin.home.UsersActivity;
import com.example.newsappadmin.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context context = HomeActivity.this;
    private LinearLayout linearLayoutUsers,linearLayoutPost,linearLayoutAddPost;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        initViews();
        if(firebaseAuth.getCurrentUser()==null)
        {
            Intent intent = new Intent(context, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        } // if closed

        linearLayoutUsers.setOnClickListener(this);
        linearLayoutPost.setOnClickListener(this);
        linearLayoutAddPost.setOnClickListener(this);



    } // onCreate closed



    private void initViews()
    {
        toolbar = findViewById(R.id.toolbarHomeActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin");
        linearLayoutUsers = findViewById(R.id.layoutUsers);
        linearLayoutPost = findViewById(R.id.layoutPosts);
        linearLayoutAddPost = findViewById(R.id.layoutAddPost);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layoutUsers:
                goTo(UsersActivity.class);
                break;
            case R.id.layoutPosts:
                inflateDialogue(AllPostsActivity.class);
                break;
            case R.id.layoutAddPost:
                inflateDialogue(AddPostActivity.class);
                break;
        } // switch closed
    } // onClick closed

    private void inflateDialogue(final Class activity)
    {
        final String[] options =
                {
                  Constants.top ,
                  Constants.latest ,
                  Constants.world ,
                  Constants.business ,
                  Constants.sports ,
                  Constants.entertainment ,
                  Constants.pakistan ,
                  Constants.opinion ,
                  Constants.magazine
                };
        AlertDialog.Builder dialogue = new AlertDialog.Builder(context);
        dialogue.setTitle("Choose Category");

        dialogue.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                for(int i=0;i<options.length;i++)
                {
                    if(which==i)
                    {
                        goTo(options[i],activity);
                        return;
                    }
                } // for closed

            } // onClick closed
        });
        dialogue.create().show();


    } //


    private void goTo(Class activity)
    {
        Intent intent = new Intent(context,activity);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    private void goTo(String child,Class activity)
    {
        Intent intent = new Intent(context,activity);
        intent.putExtra(Constants.child,child);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }



} // HomeActivity closed