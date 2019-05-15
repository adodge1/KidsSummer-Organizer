package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Utils.FirebaseDBUtil;
import com.example.myapplication.model.Camp;
import com.example.myapplication.model.Child;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DetailActivityChild extends AppCompatActivity implements CampsRecyclerAdapter.CampsRecyclerAdapterOnClickHandler {


    private Child mChildInfo;

    /*to read the camps related to the kid selected*/
    private DatabaseReference kidsCampsRef;
    private ValueEventListener valueEventListener;
    private ArrayList<Camp> camps = new ArrayList<>();
    private CampsRecyclerAdapter mCampAdapter;
    private int mNumberColumns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_detail_child);
        final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        try{
            mChildInfo  = intent.getParcelableExtra("Child.Details");
            if (mChildInfo == null) {
                    closeOnError();

            }else{
                TextView childNameTitle = findViewById(R.id.tv_titleChildDetail);
                childNameTitle.setText(mChildInfo.getChildName());
                DatabaseReference rootRef = FirebaseDBUtil.getDatabase().getReference();
                kidsCampsRef = rootRef.child("kid_camps").child(mChildInfo.getChildName());
                kidsCampsRef.keepSynced(true);

                RecyclerView mCampRecyclerView = findViewById(R.id.rv_camps);

                mNumberColumns = calculateNoOfColumns(this);
                // use a grid layout manager
                GridLayoutManager layoutManager = new GridLayoutManager(this, mNumberColumns, GridLayoutManager.VERTICAL, false);


                // use a layout manager
               // LinearLayoutManager layoutManager
                    //    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                mCampRecyclerView.setLayoutManager(layoutManager);
                // use this setting to improve performance
                mCampRecyclerView.setHasFixedSize(true);
                // specify an adapter
                 mCampAdapter = new CampsRecyclerAdapter(this);
                //set Adapter to recyclerView
                mCampRecyclerView.setAdapter(mCampAdapter);

            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();


        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                camps.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String campName = ds.getKey();


                    Camp newCamp = new Camp(campName);
                    camps.add(newCamp);
                    mCampAdapter.notifyDataSetChanged(); // refresh
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };

        kidsCampsRef.addValueEventListener(valueEventListener);


        mCampAdapter.setCampData(camps);

    }


    @Override
    protected void onStop() {
        super.onStop();
        kidsCampsRef.removeEventListener(valueEventListener);

    }


    @Override
    public void onClick(Camp selectedCamp) {
         String name = selectedCamp.getCampName();
         Toast.makeText(this, "Item Clicked"+name, Toast.LENGTH_LONG).show();
       // Intent intentToStartDetailActivity = new Intent(this, DetailActivityChild.class);
      //  intentToStartDetailActivity.putExtra("Camp.Details",selectedCamp);
      //  startActivity(intentToStartDetailActivity);

    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    public void getAddCampActivity(View view)
    {
        Intent intentToStartDetailActivity = new Intent(this, AddCampActivity.class);
        intentToStartDetailActivity.putExtra("Child.Selected",mChildInfo);
        startActivity(intentToStartDetailActivity);


    }


    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 3)
            noOfColumns = 3;
        return noOfColumns;
    }




}
