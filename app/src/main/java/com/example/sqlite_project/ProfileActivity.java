package com.example.sqlite_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView addimage;
    private CircleImageView profileimage;
    private TextInputLayout name,phone, email;
    private FloatingActionButton updatebutton;
    private ProgressBar Profileprogress;
    private ProgressDialog Mprogress;
    final  int RESULT_LOAD_IMG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Mprogress = new ProgressDialog(ProfileActivity.this);
        Profileprogress = findViewById(R.id.ProfileProgressbarID);
        Profileprogress.setVisibility(View.INVISIBLE);

        addimage = findViewById(R.id.AddImageID);
        profileimage = findViewById(R.id.ProfileImageID);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        name = findViewById(R.id.UsernameID);
        phone = findViewById(R.id.PhoneID);
        email = findViewById(R.id.EmailID);
        updatebutton = findViewById(R.id.UpdateButtonID);

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nametext = name.getEditText().getText().toString();
                String phonetext = phone.getEditText().getText().toString();
                String emailtext = email.getEditText().getText().toString();


                if(nametext.isEmpty()){
                    name.getEditText().setError("name empty");
                }
                else if(phonetext.isEmpty()){
                    phone.getEditText().setError("phone empty");
                }
                else if(emailtext.isEmpty()){
                    email.getEditText().setError("email empty");
                }
                else {
                    Mprogress.setTitle("Please wait ...");
                    Mprogress.setMessage("Please wait your profile is setup");
                    Mprogress.setCanceledOnTouchOutside(false);
                    Mprogress.show();

                    Profileprogress.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profileimage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
        }
    }

}