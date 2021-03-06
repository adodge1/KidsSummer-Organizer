package com.techactivity.myapplication;



import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.techactivity.myapplication.Utils.FirebaseDBUtil;
import com.techactivity.myapplication.Utils.InternetCheck;
import com.techactivity.myapplication.model.Child;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements ChildrenRecyclerAdapter.ChildrenRecyclerAdapterOnClickHandler {

    private DatabaseReference childrenRef;
    private ValueEventListener valueEventListener;
    private ArrayList<Child> children = new ArrayList<>();
    private ChildrenRecyclerAdapter mChildAdapter;
    private Boolean mHasInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DatabaseReference rootRef = FirebaseDBUtil.getDatabase().getReference();
         childrenRef = rootRef.child("kids");
        childrenRef.keepSynced(true);

        setContentView(R.layout.activity_main);
        //Children
        RecyclerView  mChildRecyclerView = findViewById(R.id.rv_children);
        // use a layout manager
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mChildRecyclerView.setLayoutManager(layoutManager);
        // use this setting to improve performance
        mChildRecyclerView.setHasFixedSize(true);
        // specify an adapter
         mChildAdapter = new ChildrenRecyclerAdapter(this);
        //set Adapter to recyclerView
        mChildRecyclerView.setAdapter(mChildAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                children.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String childName = ds.child("childName").getValue(String.class);
                    String childAge = ds.child("age").getValue(String.class);

                    Child newKid = new Child(childName,childAge);
                    children.add(newKid);
                    mChildAdapter.notifyDataSetChanged(); // refresh
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };

        new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void accept(Boolean connected) {
                if (connected) {
                    Log.d("TAG", "Internet is connected");
                } else {
                    Toast.makeText(MainActivity.this,"No Internet detected ,this app has Offline Capabilities Enabled :)", Toast.LENGTH_LONG).show();
                }
            }
        });

        childrenRef.addValueEventListener(valueEventListener);

        mChildAdapter.setChildData(children);


    }

    @Override
    protected void onStop() {
        super.onStop();
        childrenRef.removeEventListener(valueEventListener);

    }

    @Override
    public void onClick(Child selectedChild) {




       // String name = selectedChild.getChildName();
        // Toast.makeText(this, "Item Clicked"+name, Toast.LENGTH_LONG).show();
        Intent intentToStartDetailActivity = new Intent(MainActivity.this, DetailActivityChild.class);
        intentToStartDetailActivity.putExtra("Child.Details",selectedChild);
        startActivity(intentToStartDetailActivity);

    }



    public void getAddChildActivity(View view)
    {
        Intent intent = new Intent(this, AddChildActivity.class);
        startActivity(intent);
    }



}
