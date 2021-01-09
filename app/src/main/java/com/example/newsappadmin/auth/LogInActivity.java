package com.example.newsappadmin.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsappadmin.R;
import com.example.newsappadmin.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity
{

    private Context context = LogInActivity.this;
    private EditText editTextEmail , editTextPassword;
    private Button buttonLogIn,buttonToSignUpScreen;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editTextEmail = findViewById(R.id.editTextLogInActivityEmail);
        editTextPassword =findViewById(R.id.editTextLogInActivityPassword);
        buttonLogIn = findViewById(R.id.buttonLogInActivityLogIn);
        buttonToSignUpScreen = findViewById(R.id.buttonLogInActivityGoToSignUpScreen);
        progressBar = findViewById(R.id.progressBarLogInActivity);
        progressBar.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            Intent intent = new Intent(context, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        } // if closed


        buttonLogIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String email = editTextEmail.getText().toString().trim();
                String pass = editTextPassword.getText().toString().trim();

                if(email.isEmpty())
                {
                    Toast.makeText(context, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(pass.isEmpty())
                {
                    Toast.makeText(context, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LogIntoFirebase(email,pass);
                } // else closed
            } // onClick closed
        }); // button LogIn closed

        buttonToSignUpScreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_from_left);
            } // onclick
        }); // buttonToSignUpScreen onClick Listener closed
    } // onCreate closed

    private void LogIntoFirebase(String email, String pass)
    {
        Toast.makeText(context, email+"Logged In", Toast.LENGTH_SHORT).show();
    } // LogIntoFirebase

}