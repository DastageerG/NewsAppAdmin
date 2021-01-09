package com.example.newsappadmin.auth;

import androidx.annotation.NonNull;
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


public class SignUpActivity extends AppCompatActivity
{

    private static final String TAG ="1111" ;
    private Context context = SignUpActivity.this;
    private EditText editTextName,editTextContact,editTextAddress,editTextEmail,editTextPassword;
    private Button buttonSignUp,buttonToLogInScreen;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        initViews();
        buttonSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = editTextName.getText().toString().trim();
                String contact = editTextContact.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String pass = editTextPassword.getText().toString().trim();

                if(name.isEmpty())
                {
                    Toast.makeText(context, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if(contact.isEmpty())
                {
                    Toast.makeText(context, "Please Enter Valid Number", Toast.LENGTH_SHORT).show();
                }
                else if(address.isEmpty())
                {
                    Toast.makeText(context, "Please Enter Address", Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty())
                {
                    Toast.makeText(context, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(pass.isEmpty())
                {
                    Toast.makeText(context, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseAuthMethods firebaseMethods = new FirebaseAuthMethods(context);
                    firebaseMethods.createUser(email,pass,name,contact,address);
                } // else closed
            } // onClick closed
        }); // button Sign Up onClick Listener

        buttonToLogInScreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               finish();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    Log.d(TAG, "onAuthStateChanged: Account Created");
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                }
            } // onAuthChanged
        };
    } // onCreate closed

    private void initViews()
    {
        editTextName = findViewById(R.id.editTextSignUpActivityName);
        editTextContact = findViewById(R.id.editTextSignUpActivityNumber);
        editTextAddress = findViewById(R.id.editTextSignUpActivityAddress);
        editTextEmail =findViewById(R.id.editTextSignUpActivityEmail);
        editTextPassword = findViewById(R.id.editTextSignUpActivityPassword);
        buttonSignUp = findViewById(R.id.buttonSignUpActivitySignUp);
        buttonToLogInScreen = findViewById(R.id.buttonSignUpActivityGoToLogInScreen);
        progressBar = findViewById(R.id.progressBarSignUpActivity);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}