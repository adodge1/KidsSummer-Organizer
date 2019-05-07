package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Child implements Parcelable {


    private String mName;
    private String mAge;

    /**
     * No args constructor for use in serialization
     */
    public Child() {
    }


    // Constructor
    public Child(  String name, String age){

        mName = name;
        mAge = age;
    }



    public String getChildName() {
        return mName;
    }

    public void setChildName(String name) {
        mName = name;
    }

    public String getAge() {
        return mAge;
    }

    public void setAge(String age) {
        mAge = age;
    }



    // Parcelling part
    public Child(Parcel parcel_in){

        mName = parcel_in.readString();
        mAge =  parcel_in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mName);
        dest.writeString(mAge);

    }

    @Override
    public String toString() {
        return "Child{" +
                ", Name='" + mName + '\'' +
                ", Age='" + mAge + '\'' +
                '}';
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Child createFromParcel(Parcel in) {
            return new Child(in);
        }

        public Child[] newArray(int size) {
            return new Child[size];
        }
    };


}
