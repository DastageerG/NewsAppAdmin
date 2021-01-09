package com.example.newsappadmin.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsappadmin.R;
import com.example.newsappadmin.model.Post;
import com.example.newsappadmin.utils.Constants;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import id.zelory.compressor.Compressor;

public class AddPostActivity extends AppCompatActivity
{

    private static final int PICK_IMAGE =1111 ;
    public static final String TAG = "1111";
    private Context context = AddPostActivity.this;
    private ImageView imageViewPostImage , imageViewSelectImage;
    private EditText editTextImageDescription , editTextPostTitle , editTextPostDescription;
    private Button buttonPost;
    private ProgressBar progressBar;
    private Uri postImageUri;
    private byte[] thumbnail;
    private String child;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        initViews();

        if(getIntent().getExtras().getString(Constants.child)!=null)
        {
            child = getIntent().getExtras().getString(Constants.child);
        }


        imageViewSelectImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                &&
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                )
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                } // if closed
                else
                {
                    ActivityCompat.requestPermissions(AddPostActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            2);
                } // else closed
            } // onClick closed
        });

        buttonPost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(editTextImageDescription.getText().toString().isEmpty())
                {
                    editTextImageDescription.setText("empty");
                }
                String imageDescription = editTextImageDescription.getText().toString().trim();
                String  postTitle = editTextPostTitle.getText().toString().trim();
                String postDescription = editTextPostDescription.getText().toString().trim();

                if(postImageUri == null)
                {
                    Toast.makeText(context, "Please select an Image", Toast.LENGTH_SHORT).show();
                }
                else if(postTitle.isEmpty())
                {
                    Toast.makeText(AddPostActivity.this, "Add a Post Title", Toast.LENGTH_SHORT).show();
                } // if closed
                else if(postDescription.isEmpty())
                {
                    Toast.makeText(AddPostActivity.this, "Please Enter Post Description", Toast.LENGTH_SHORT).show();
                } ///  else if closed
                else
                {
                    FirebaseMethods firebaseMethods = new FirebaseMethods(context,progressBar,child);
                    firebaseMethods.createPost(imageDescription,postTitle,postDescription,postImageUri,thumbnail);
                }
            } // onClick
        }); // onClick listener closed
    } // onCreate closed

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE)
        {
            if(resultCode==RESULT_OK)
            {
                // getImage and Start Cropping Activity
                CropImage.activity(data.getData())
                        .setAspectRatio(16,9)
                        .start(AddPostActivity.this);

            } // if closed
        } // if closed

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                postImageUri = result.getUri(); // cropped Image
                imageViewPostImage.setImageURI(postImageUri);

                // Compressing this image for thumbnail

                File file = new File(postImageUri.getPath());

                Bitmap bitmap = null;
                try
                {
                    bitmap = new Compressor(this)
                            .setMaxWidth(960)
                            .setMaxHeight(540)
                            .setQuality(75)
                            .compressToBitmap(file);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    thumbnail = baos.toByteArray();


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
                Log.d(TAG, "onActivityResult: Crop Image "+error.getMessage());
            }
        }


    } // onActivityResult

    private void initViews()
    {

        imageViewPostImage = findViewById(R.id.imageViewAddPostActivityPostImage);
        imageViewSelectImage = findViewById(R.id.imageViewAddPostActivitySelectImage);
        editTextImageDescription = findViewById(R.id.editTextAddPostActivityImageDescription);
        editTextPostTitle = findViewById(R.id.editTextAddPostActivityPostTitle);
        editTextPostDescription = findViewById(R.id.editTextAddPostActivityPostDescription);
        buttonPost = findViewById(R.id.buttonAddPostActivityPost);
        progressBar = findViewById(R.id.progressBarAddPostActivity);
        progressBar.setVisibility(View.GONE);

    } // initViews closed



} // addPostActivity class closed