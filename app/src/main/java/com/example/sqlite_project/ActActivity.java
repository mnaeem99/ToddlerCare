package com.example.sqlite_project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_project.model.Baby;
import com.example.sqlite_project.sql.SQLiteDB;

import java.util.Calendar;

public class ActActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText personName;
    private EditText issue;
    private EditText dob;

    private Button btnAdd, btnEdit, btnDelete;

    private SQLiteDB sqLiteDB;
    private Baby baby;

    private DatePickerDialog picker;
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
        dob = (EditText) findViewById(R.id.dobText);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ActActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

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
            String value1 = personName.getText().toString().trim();
            String value2 = issue.getText().toString().trim();
            if (value1.isEmpty() || value2.isEmpty()) {
                Toast.makeText(this, "Enter all fields!", Toast.LENGTH_SHORT).show();
            } else {
                baby.setName(personName.getText().toString());
                baby.setIssue(issue.getText().toString());
                sqLiteDB.create(baby);
                Toast.makeText(this, "Inserted!", Toast.LENGTH_SHORT).show();
                finish();
            }
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
