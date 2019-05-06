package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Child implements Parcelable {

    private String mId;
    private String mName;
    private String mAge;

    /**
     * No args constructor for use in serialization
     */
    public Child() {
    }


    // Constructor
    public Child(String id, String name, String age){
        mId = id;
        mName = name;
        mAge = age;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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
        mId = parcel_in.readString();
        mName = parcel_in.readString();
        mAge =  parcel_in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mAge);

    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + mId + '\'' +
                ", Title='" + mName + '\'' +
                ", overview='" + mAge + '\'' +
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
