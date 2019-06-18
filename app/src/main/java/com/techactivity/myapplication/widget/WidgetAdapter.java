package com.techactivity.myapplication.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.util.Log;

import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.techactivity.myapplication.R;
import com.techactivity.myapplication.Utils.FirebaseDBUtil;
import com.techactivity.myapplication.model.Camp;
import com.techactivity.myapplication.model.Child;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory  {
    private Context mContext;

    private int appWidgetId;


    private ArrayList<Child> kids = new ArrayList<>();

    private ArrayList<Camp> kidCamps = new ArrayList<>();
    private String mCapmInfo = "";


    //https://medium.com/@darshankawar/displaying-data-in-app-widget-screen-with-firebase-ed7e892b290c




    public WidgetAdapter(Context context,Intent intent){
        this.mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        getKidsNamesDB();


    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        getKidsNamesDB();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        try {
            Thread.currentThread();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("TAG", "Total count is " +kids.size());
        return kids == null ? 0 : kids.size();
    }

    @Override
    public RemoteViews getViewAt(final int position) {

        final RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        remoteViews.setTextViewText(R.id.widget_tv,kids.get(position).getChildName());
        Intent intent =new Intent();


        getCampsOfKidDB(kids.get(position).getChildName());
        mCapmInfo ="";
        Log.d("TAG", "*****************************KIDS CAMP************************** " +kidCamps.size());
        for (int i = 0; i < kidCamps.size(); i++) {

            // accessing each element of array
            mCapmInfo = mCapmInfo +" "+ kidCamps.get(i).getCampName() +" - " +kidCamps.get(i).getWeekFrom()+"\n";

        }



        intent.putExtra(WidgetProvider.WIDGET_KEY_ITEM, mCapmInfo);
        mCapmInfo ="";
        remoteViews.setOnClickFillInIntent(R.id.widget_rv, intent);
        return remoteViews;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }




    private void getKidsNamesDB() {


        DatabaseReference rootRef = FirebaseDBUtil.getDatabase().getReference();
        DatabaseReference childrenRef= rootRef.child("kids");
        childrenRef.keepSynced(true);


        Log.d("TAG", "childrenRef " +childrenRef.toString());


        ValueEventListener  valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kids.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String childName = ds.child("childName").getValue(String.class);
                    String childAge = ds.child("age").getValue(String.class);

                    Child newKid = new Child(childName,childAge);
                    kids.add(newKid);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "databaseError " +databaseError);
            }
        };

        childrenRef.addValueEventListener(valueEventListener);
    }

    private void getCampsOfKidDB(String kidName) {




        DatabaseReference rootRef = FirebaseDBUtil.getDatabase().getReference();
        DatabaseReference kidsCampsRef = rootRef.child("kid_camps").child(kidName);
        kidsCampsRef.orderByValue();
        kidsCampsRef.keepSynced(true);


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kidCamps.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String campName = ds.getKey();
                    String campWeekFrom = ds.getValue(String.class);

                    Camp newCamp = new Camp(campName,campWeekFrom);
                    kidCamps.add(newCamp);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };

        kidsCampsRef.addValueEventListener(valueEventListener);

    }

}