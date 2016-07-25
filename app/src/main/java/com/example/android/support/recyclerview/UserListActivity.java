package com.example.android.support.recyclerview;

import com.example.android.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by 14110105 on 2016-07-11.
 */
public class UserListActivity extends AppCompatActivity {

    ArrayList<Contact> mContacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recyclerview);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mContacts = Contact.creatContactsList(20);

        ContactsAdapter adapter = new ContactsAdapter(mContacts, this);

        recyclerView.setAdapter(adapter);


    }
}
