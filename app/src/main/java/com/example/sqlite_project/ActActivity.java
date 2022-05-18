package com.example.sqlite_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_project.model.Baby;
import com.example.sqlite_project.sql.SQLiteDB;

public class ActActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText personName;
    private EditText issue;

    private Button btnAdd, btnEdit, btnDelete;

    private SQLiteDB sqLiteDB;
    private Baby baby;

    public static void start(Context context){
        Intent intent = new Intent(context, ActActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, Baby baby){
        Intent intent = new Intent(context, ActActivity.class);
        intent.putExtra(ActActivity.class.getSimpleName(), baby);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);

        personName = (EditText) findViewById(R.id.personText);
        issue = (EditText) findViewById(R.id.phoneText);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        baby = getIntent().getParcelableExtra(ActActivity.class.getSimpleName());
        if(baby != null){
            btnAdd.setVisibility(View.GONE);

            personName.setText(baby.getName());
            issue.setText(baby.getIssue());
        }else{
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        sqLiteDB = new SQLiteDB(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdd){
            baby = new Baby();
            baby.setName(personName.getText().toString());
            baby.setIssue(issue.getText().toString());
            sqLiteDB.create(baby);

            Toast.makeText(this, "Inserted!", Toast.LENGTH_SHORT).show();
            finish();
        }else if(v == btnEdit){
            baby.setName(personName.getText().toString());
            baby.setIssue(issue.getText().toString());
            sqLiteDB.update(baby);

            Toast.makeText(this, "Edited!", Toast.LENGTH_SHORT).show();
            finish();
        }else if(v == btnDelete){
            sqLiteDB.delete(baby.getId());

            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
