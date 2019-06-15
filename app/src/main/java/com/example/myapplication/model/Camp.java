package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Camp implements Parcelable {

    public static final Creator<Camp> CREATOR = new Creator<Camp>() {
        @Override
        public Camp createFromParcel(Parcel in) {
            return new Camp(in);
        }

        @Override
        public Camp[] newArray(int size) {
            return new Camp[size];
        }
    };


    private String mName;
    private String mContact;
    private String mPhone;
    private String mStreet;
    private String mCity;
    private String mState;
    private String mZip;
    private String mWeekFrom;
    private String mWeekTo;
    private String mHoursFrom;
    private String mHoursTo;
    private String mProvidesLunch;
    private String mNotes;





    /**
     * No args constructor for use in serialization
     */
    public Camp() {
    }

    public Camp(String campName) {
        mName = campName;

    }

    public Camp(String campName,String campWeekFrom) {
        mName = campName;
        mWeekFrom = campWeekFrom;

    }

    // Constructor
    public Camp( String campName,String campContactName,String campContactPhone,String street , String city, String state, String zip ,String campWeekFrom, String campWeekTo,String campHrsFrom, String campHrsTo,String hasLunch,String campNotes ){

        mName = campName;
        mContact = campContactName;
        mPhone = campContactPhone;
        mStreet = street;
         mCity = city;
        mState = state;
         mZip = zip;
        mWeekFrom = campWeekFrom;
        mWeekTo  =campWeekTo;
        mHoursFrom = campHrsFrom;
        mHoursTo = campHrsTo;
        mProvidesLunch = hasLunch;
        mNotes = campNotes;

    }

    public Camp( Parcel in){

        mName = in.readString();
        mContact = in.readString();
        mPhone = in.readString();
        mStreet = in.readString();
        mCity = in.readString();
        mState = in.readString();
        mZip = in.readString();
        mWeekFrom =in.readString();
        mWeekTo  = in.readString();
        mHoursFrom = in.readString();
        mHoursTo = in.readString();
        mProvidesLunch = in.readString();
        mNotes = in.readString();

    }




    @Override
    public String toString() {
        return "Camp{" +
                ", Camp Name='" + mName + '\'' +
                ", Contact='" + mContact + '\'' +
                ", Phone='" + mPhone + '\'' +
                ", street='" + mStreet + '\'' +
                ", City='" + mCity + '\'' +
                ", State='" + mState + '\'' +
                ", Zip='" + mZip + '\'' +
                ", WeekFrom='" + mWeekFrom + '\'' +
                ", WeekTo='" + mWeekTo + '\'' +
                ", HrsFrom='" + mHoursFrom + '\'' +
                ", HrsTo='" + mHoursTo + '\'' +
                ", Lunch='" + mProvidesLunch + '\'' +
                ", Notes='" + mNotes + '\'' +
                '}';
    }



    public String getCampName() {
        return mName;
    }

    public void setCampName(String name) {
        this.mName = name;
    }


    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        this.mContact = contact;
    }


    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }


    public String getStreet() {
        return mStreet;
    }

    public void setStreet(String street) {
        this.mStreet = street;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }


    public String getState() {
        return mState;
    }

    public void setState(String state) {
        this.mState = state;
    }


    public String getZip() {    return mZip;    }

    public void setZip(String zip) {
        this.mZip = zip;
    }



    public String getWeekFrom() {
        return mWeekFrom;
    }

    public void setWeekFrom(String weekFrom) {
        this.mWeekFrom = weekFrom;
    }


    public String getWeekTo() {
        return mWeekTo;
    }

    public void setWeekTo(String weekTo) {
        this.mWeekTo = weekTo;
    }

    public String getHrsFrom() {
        return mHoursFrom;
    }

    public void setHrsFrom(String hrsFrom) {
        this.mHoursFrom = hrsFrom;
    }

    public String getHrsTo() {
        return mHoursTo;
    }

    public void setHrsTo(String hrsTo) {
        this.mHoursTo = hrsTo;
    }



    public String getProvidesLunch() {
        return mProvidesLunch;
    }

    public void setProvidesLunch(String hasLunch) {
        this.mProvidesLunch = hasLunch;
    }


    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        this.mNotes = notes;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mContact);
        dest.writeString(mPhone);
        dest.writeString(mStreet);
        dest.writeString(mCity);
        dest.writeString(mState);
        dest.writeString(mZip);
        dest.writeString(mWeekFrom);
        dest.writeString(mWeekTo);
        dest.writeString(mHoursFrom);
        dest.writeString(mHoursTo);
        dest.writeString(mProvidesLunch);
        dest.writeString(mNotes);

    }

    @Override
    public int describeContents() {
        return 0;
    }






}
