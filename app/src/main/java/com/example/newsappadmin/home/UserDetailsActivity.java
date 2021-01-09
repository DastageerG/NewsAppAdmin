package com.example.newsappadmin.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.newsappadmin.R;
import com.example.newsappadmin.model.User;
import com.example.newsappadmin.utils.Constants;
import com.example.newsappadmin.utils.UsersCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener
{

    private Context context = UserDetailsActivity.this;
    public static final String TAG = "1111";
    FirebaseMethods firebaseMethods;
    private ProgressBar progressBar;
    private String uid;

    private TextView textViewName,textViewContact,textViewEmail,textViewAddress;
    private ImageView imageViewCall , imageViewMessage , imageViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        initViews();

        firebaseMethods = new FirebaseMethods(context,progressBar);
        if(getIntent().getExtras()!=null)
        {
            uid = getIntent().getExtras().getString(Constants.uid);
        }


    firebaseMethods.getUserProfile(uid, new UsersCallBack()
    {
        @Override
        public void getUserData(User user)
        {
            /// This is for getting one User data

            if(user!=null)
            {
                textViewName.setText(user.getUserName());
                textViewContact.setText(user.getUserContact());
                textViewEmail.setText(user.getUserEmail());
                textViewAddress.setText(user.getUserAddress());
            }
            else
            {
                Log.d(TAG, "getUserData: user null ");
            }
        }

        @Override
        public void getAllUsers(List<User> userList)
        {
            // This is for getting All User List
        }
    });
    } // onCreate closed

    private void initViews()
    {
        progressBar = findViewById(R.id.progressBarUserDetailsActivity);

        textViewName = findViewById(R.id.textViewUserDetailsActivityName);
        textViewContact = findViewById(R.id.textViewUserDetailsActivityContact);
        textViewEmail = findViewById(R.id.textViewUserDetailsActivityEmail);
        textViewAddress = findViewById(R.id.textViewUserDetailsActivityAddress);
        imageViewCall = findViewById(R.id.imageViewUserDetailsActivityCall);
        imageViewMessage = findViewById(R.id.imageViewUserDetailsActivityMessage);
        imageViewEmail = findViewById(R.id.imageViewUserDetailsActivityEmail);

        imageViewCall.setOnClickListener(this);
        imageViewMessage.setOnClickListener(this);
        imageViewEmail.setOnClickListener(this);


    }

    @Override
    public void onClick(View v)
    {

        String number = textViewContact.getText().toString();
        switch (v.getId())
        {

            case R.id.imageViewUserDetailsActivityCall:

                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number));
                startActivity(call);

                break;
            case R.id.imageViewUserDetailsActivityMessage:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",number, null)));
                break;

            case R.id.imageViewUserDetailsActivityEmail:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("message/rfc822");
                intent.setData(Uri.parse("mailto:"+textViewEmail.getText().toString())); // only email apps should handle this
                if (intent.resolveActivity(getPackageManager()) != null)
                {
                    startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                }
                break;

        }

    }
}