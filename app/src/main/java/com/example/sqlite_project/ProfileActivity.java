package com.example.sqlite_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sqlite_project.model.User;
import com.example.sqlite_project.sql.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView addimage;
    private CircleImageView profileimage;
    private TextInputLayout name,phone, email;
    private FloatingActionButton updatebutton;
    private ProgressBar Profileprogress;
    private ProgressDialog Mprogress;
    final  int RESULT_LOAD_IMG = 1;
    private DatabaseHelper databaseHelper;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        databaseHelper = new DatabaseHelper(ProfileActivity.this);

        Mprogress = new ProgressDialog(ProfileActivity.this);
        Profileprogress = findViewById(R.id.ProfileProgressbarID);
        Profileprogress.setVisibility(View.INVISIBLE);

        addimage = findViewById(R.id.AddImageID);
        profileimage = findViewById(R.id.ProfileImageID);
        Intent intent = getIntent();
        String str = intent.getStringExtra("EMAIL1");
        System.out.println(str);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
        name = (TextInputLayout) findViewById(R.id.UsernameID);
        phone = findViewById(R.id.PhoneID);
        email = findViewById(R.id.EmailID);
        phone.getEditText().setText("0308654654");
        updatebutton = findViewById(R.id.UpdateButtonID);
        List<User> users = databaseHelper.getAllUser();
        for (User user: users){
            if (Objects.equals(str, user.getEmail())){
                email.getEditText().setText(user.getEmail());
                name.getEditText().setText(user.getName());
                phone.getEditText().setText("0308654654");
            }
        }

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
                    Mprogress.setMessage("Please wait your profile is being setup");
                    Mprogress.setCanceledOnTouchOutside(false);
                    Mprogress.show();
                    Profileprogress.setVisibility(View.VISIBLE);
                    for (User user: users){
                        if (user.getEmail().toString() == str.toString()){
                            user.setName(nametext);
                            user.setEmail(emailtext);
                            databaseHelper.updateUser(user);
                        }
                    }
                    Profileprogress.setVisibility(View.INVISIBLE);
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