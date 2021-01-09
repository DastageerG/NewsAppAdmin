package com.example.newsappadmin.home;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.newsappadmin.model.Post;
import com.example.newsappadmin.model.User;
import com.example.newsappadmin.utils.Constants;
import com.example.newsappadmin.utils.PostsCallBack;
import com.example.newsappadmin.utils.UsersCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FirebaseMethods  // firebaseMethod is for adding retrieving or updating data
{
    public static final String TAG = "1111";
    private Context context;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,postRef;
    private StorageReference storageReference;
    private StorageReference imagePath , thumbNailPath;
    private ProgressBar progressBar;
    private Activity activity;
    public static String category;





    // constructor for add post activity for posting data
    // also used for getting posts with categoryName
    public FirebaseMethods(Context context,ProgressBar progressBar ,String category)
    {
        this.context = context;
        this.activity = (Activity) context;
        this.progressBar = progressBar;
        this.category = category;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);
        postRef  = databaseReference.child(Constants.Posts).child(category);
        storageReference = FirebaseStorage.getInstance().getReference();
    } // constructor closed


    /// constructor  for Users activity for getting All users
    public FirebaseMethods(Context context,ProgressBar progressBar)
    {
        this.context = context;
        this.activity = (Activity) context;
        this.progressBar = progressBar;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);
        //postRef  = databaseReference.child(Constants.Posts).child(category);
        storageReference = FirebaseStorage.getInstance().getReference();
    } // constructor closed


                    ////  Methods and their Activity name



    // Add Post Activity
    public void createPost(final String imageDescription, final String postTitle, final String postDescription, final Uri postImageUri, final byte[] thumbnail)
    {
        progressBar.setVisibility(View.VISIBLE);
        final String uid = firebaseAuth.getCurrentUser().getUid(); // current user id
        // upload image First
        imagePath = storageReference.child(Constants.postImage).child(uid)
                .child("image"+System.currentTimeMillis()+".jpg");

        imagePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            final String postImageUrl = uri.toString(); // imageUrl
                            thumbNailPath = storageReference.child(Constants.postThumbNail).child(uid)
                                    .child("image"+System.currentTimeMillis()+".jpg");
                            thumbNailPath.putBytes(thumbnail).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Log.d(TAG, "onComplete: Thumbnail uploaded");
                                        thumbNailPath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                                        {
                                            @Override
                                            public void onSuccess(Uri uri)
                                            {
                                                String thumbNailUrl = uri.toString();
                                                addPostToDataBase(imageDescription,postTitle,postDescription,uid,postImageUrl,thumbNailUrl);
                                            } // onSuccess closed
                                        }); /// onSuccessListener closed

                                    } // if closed
                                    else
                                    {
                                        Log.d(TAG, "onComplete: ThumbNail Error : "+task.getException().getMessage());
                                    } // else closed
                                } // on Complete closed
                            });


                        } // on Success closed
                    });
                } // if closed
                else
                {
                    Log.d(TAG, "onComplete: Image Uploading error "+task.getException().getMessage());
                    Toast.makeText(context, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } // else closed
            } // onComplete closed
        });

    } // create Post closed

/// method is used in Create Post (method above)
    private void addPostToDataBase(String imageDescription, String postTitle, String postDescription, String uid, String postImageUrl, final String thumbNailUrl)
    {
        final String postId = postRef.push().getKey();
        final Post post = new Post(postId,uid,postTitle,postDescription,postImageUrl,imageDescription,thumbNailUrl,category,System.currentTimeMillis());

        postRef.child(postId).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    databaseReference.child("All").child(postId).setValue(post);
                    activity.finish();
                } // if closed
                else
                {
                    Toast.makeText(context, ""+task, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onComplete: "+task.getException().getMessage());
                } // else closed
            } // onComplete closed
        }); // addOnCompleteListener closed
    }

    //


    /// Users Activity
    public void getAllUsers(final UsersCallBack usersCallBack)
    {
        final List<User>userList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child(Constants.USERS).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                progressBar.setVisibility(View.GONE);

                if(snapshot.exists())
                {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        User user = dataSnapshot.getValue(User.class);
                        userList.add(user);
                    }
                    usersCallBack.getAllUsers(userList);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onDataChange: Snapshot does not exists");
                }
            } // onDataChange
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        }); // addValue Event listener closed
    } // getAllUsers


    //// User Details Activity

    // get Specific User by id
    public void getUserProfile(final String uid, final UsersCallBack usersCallBack)
    {
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child(Constants.USERS).child(uid).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                progressBar.setVisibility(View.GONE);
                if(snapshot.exists())
                {
                    User user = snapshot.getValue(User.class);
                    usersCallBack.getUserData(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onCancelled: "+error.getMessage());
            }
        });

    } // getUser profile closed


    // used in AllPostsActivity
    // getAll Post with respective to their category

    public void getPosts(final PostsCallBack callBack)
    {
        final List<Post>postList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child(Constants.Posts).child(category).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                progressBar.setVisibility(View.GONE);
                if(snapshot.exists())
                {
                    /// get All posts in category and add them in postsList
                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        Post post = dataSnapshot.getValue(Post.class);
                        postList.add(post);
                    } // for loop closed

                    // provide posts list to the CallBack
                    callBack.getPosts(postList);
                } // if closed
                else
                {
                    Log.d(TAG, "onDataChange: SnapShot does not exists");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.d(TAG, "onCancelled: "+error.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });

    }




} // FirebaseMethods closed
