package com.example.myapplication.Utils;


import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDBUtil {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }


}