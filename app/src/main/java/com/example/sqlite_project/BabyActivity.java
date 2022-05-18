package com.example.sqlite_project;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite_project.adapter.BabyListAdapter;
import com.example.sqlite_project.listener.RecyclerItemClickListener;
import com.example.sqlite_project.model.Baby;
import com.example.sqlite_project.sql.SQLiteDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BabyActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView lvContact;
    private FloatingActionButton btnAdd;

    private BabyListAdapter babyListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private SQLiteDB sqLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby);

        lvContact = (RecyclerView) findViewById(R.id.lvContact);
        btnAdd = (FloatingActionButton) findViewById(R.id.add);

        linearLayoutManager = new LinearLayoutManager(this);
        babyListAdapter = new BabyListAdapter(this);
        babyListAdapter.setOnItemClickListener(this);

        lvContact.setLayoutManager(linearLayoutManager);
        lvContact.setAdapter(babyListAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActActivity.start(BabyActivity.this);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    void loadData(){
        sqLiteDB = new SQLiteDB(this);

        List<Baby> babyList = new ArrayList<>();

        Cursor cursor = sqLiteDB.retrieve();
        Baby baby;

        if (cursor.moveToFirst()) {
            do {

                baby = new Baby();

                baby.setId(cursor.getInt(0));
                baby.setName(cursor.getString(1));
                baby.setGender(cursor.getString(2));
                baby.setDob(cursor.getString(3));
                baby.setIssue(cursor.getString(4));

                babyList.add(baby);
            }while (cursor.moveToNext());
        }

        babyListAdapter.clear();
        babyListAdapter.addAll(babyList);
    }

    @Override
    public void onItemClick(int position, View view) {
        ActActivity.start(this, babyListAdapter.getItem(position));
    }
}
