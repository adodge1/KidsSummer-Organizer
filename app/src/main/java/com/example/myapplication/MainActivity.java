package com.example.myapplication;



import android.content.Intent;



import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.model.Child;


import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements ChildrenRecyclerAdapter.ChildrenRecyclerAdapterOnClickHandler {


    private ChildrenRecyclerAdapter mChildAdapter;
    private RecyclerView mChildRecyclerView;

    private ArrayList<Child> children = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference childrenRef = rootRef.child("child_info");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Trailers
        mChildRecyclerView = findViewById(R.id.rv_children);
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



        //tripsRef.addListenerForSingleValueEvent(valueEventListener);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                children.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String childName = ds.child("childName").getValue(String.class);
                    String childAge = ds.child("age").getValue(String.class);

                    Child newKid = new Child(childName,childAge);
                    children.add(newKid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };

        childrenRef.addValueEventListener(valueEventListener);

        mChildAdapter.setChildData(children);

    }


    @Override
    public void onClick(Child selectedChild) {
        String name = selectedChild.getChildName();

         Toast.makeText(this, "Item Clicked"+name, Toast.LENGTH_LONG).show();
    }



    public void getAddChildActivity(View view)
    {
        Intent intent = new Intent(this, AddChildActivity.class);
        startActivity(intent);
    }



}
