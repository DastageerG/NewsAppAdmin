package com.example.newsappadmin.auth;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.newsappadmin.model.User;
import com.example.newsappadmin.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseAuthMethods
{
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Context context;
    public static final String TAG = "1111";


    public FirebaseAuthMethods(Context context)
    {
        this.firebaseAuth = firebaseAuth = FirebaseAuth.getInstance();
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void createUser(final String email, String pass, final String name, final String contact, final String address)
    {

       firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    if(firebaseAuth.getCurrentUser()!=null)
                    {
                        String id = firebaseAuth.getCurrentUser().getUid();
                        setupUser(id,name,contact,address,email);
                    }
                } //
                else
                {
                    Log.d(TAG, "onFailure: "+task.getException().getMessage());
                    Toast.makeText(context, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            } // onComplete closed
        }); //
    } // create user closed

    private void setupUser(String id, String name, String contact, String address,String email)
    {
        User user = new User(id,name,contact,address,email);
        databaseReference.child(Constants.USERS).child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Log.d(TAG, "onComplete:User Account Setup Successful ");

                } // if closed
                else
                {
                    Log.d(TAG, "onComplete:onError "+task.getException().getMessage());
                }
            } // onComplete closed
        });
    } // setupUser closed



} // FirebaseAuthMethods closed
