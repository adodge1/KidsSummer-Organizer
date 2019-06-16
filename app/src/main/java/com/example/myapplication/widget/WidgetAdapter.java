package com.example.myapplication.widget;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.util.Log;

import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.myapplication.R;
import com.example.myapplication.Utils.FirebaseDBUtil;
import com.example.myapplication.model.Camp;
import com.example.myapplication.model.Child;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory  {
    private Context mContext;

    private DatabaseReference childrenRef;
    private ValueEventListener valueEventListener;

    private ArrayList<Camp> camps = new ArrayList<>();
    private ArrayList<Child> kids = new ArrayList<>();

    private  DatabaseReference rootRef = FirebaseDBUtil.getDatabase().getReference();


   //https://medium.com/@darshankawar/displaying-data-in-app-widget-screen-with-firebase-ed7e892b290c




    public WidgetAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        initializeData();
    }

    @Override
    public void onDataSetChanged() {
      //  initializeData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d("TAG", "Total count is " +kids.size());
        return kids == null ? 0 : kids.size();
    }

    @Override
    public RemoteViews getViewAt(final int position) {

       /* if (position == AdapterView.INVALID_POSITION ) {
            return null;
        }*/

        final RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

        remoteViews.setTextViewText(R.id.widget_tv,kids.get(position).getChildName());
         Intent intent =new Intent();
        intent.putExtra(WidgetProvider.WIDGET_KEY_ITEM,kids.get(position).getChildName());
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


    private void initializeData(){


        childrenRef= rootRef.child("kids");
        childrenRef.keepSynced(true);

        Log.d("TAG", "childrenRef " +childrenRef.toString());


        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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





}
