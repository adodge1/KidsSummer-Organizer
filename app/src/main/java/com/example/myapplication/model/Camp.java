package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
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
    private String mPhone;
    private String mContact;
    private Address address;
    private String mWeekFrom;
    private String mWeekTo;
    private String mHoursFrom;
    private String mHoursTo;
    private Boolean mProvidesLunch;
    private String mNotes;





    /**
     * No args constructor for use in serialization
     */
    public Camp() {
    }

    public Camp(String campName) {
        mName = campName;
    }

    // Constructor
    public Camp( String campName,String campContactName,String campContactPhone, Address addy,String campWeekFrom, String campWeekTo,String campHrsFrom, String campHrsTo,Boolean hasLunch,String campNotes ){

        mName = campName;
        mContact = campContactName;
        mPhone = campContactPhone;
        address = addy;
        mWeekFrom = campWeekFrom;
        mWeekTo  =campWeekTo;
        mHoursFrom = campHrsFrom;
        mHoursTo = campHrsTo;
        mProvidesLunch = hasLunch;
        mNotes = campNotes;

    }
    // Constructor
    public Camp( Parcel in){

        mName = in.readString();
        mContact = in.readString();
        mPhone = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        mWeekFrom =in.readString();
        mWeekTo  = in.readString();
        mHoursFrom = in.readString();
        mHoursTo = in.readString();
        mProvidesLunch = in.readByte() != 0;
        mNotes = in.readString();

    }




    @Override
    public String toString() {
        return "Camp{" +
                ", Camp Name='" + mName + '\'' +
                ", Contact='" + mContact + '\'' +
                ", Phone='" + mPhone + '\'' +
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

    public String getWeekFrom() {
        return mWeekFrom;
    }

    public void setWeekFrom(String weekFrom) {
        this.mWeekFrom = weekFrom;
    }


    public String getWeekTo() {
        return mWeekFrom;
    }

    public void setWeekTo(String weekTo) {
        this.mWeekFrom = weekTo;
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



    public Boolean getProvidesLunch() {
        return mProvidesLunch;
    }

    public void setProvidesLunch(Boolean hasLunch) {
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
        //Writing the nested class to parcel
        dest.writeParcelable(address, flags);
        dest.writeString(mWeekFrom);
        dest.writeString(mWeekTo);
        dest.writeString(mHoursFrom);
        dest.writeString(mHoursTo);
        dest.writeByte((byte) (mProvidesLunch ? 1 : 0));
        dest.writeString(mNotes);

    }

    @Override
    public int describeContents() {
        return 0;
    }


    //Nested Class
    public static class Address implements Parcelable {


        public static final Creator<Address> CREATOR = new Creator<Address>() {
            @Override
            public Address createFromParcel(Parcel in) {
                return new Address(in);
            }

            @Override
            public Address[] newArray(int size) {
                return new Address[size];
            }
        };

        private String mStreet;
        private String mCity;
        private String mState;
        private String mZip;


        protected Address(Parcel in) {
            mStreet = in.readString();
            mCity = in.readString();
            mState = in.readString();
            mZip = in.readString();
        }

        public Address(String street, String city,  String state, String zip) {

            this.mStreet = street;
            this.mCity = city;
            this.mState = state;
            this.mZip = zip;
        }





        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mStreet);
            dest.writeString(mCity);
            dest.writeString(mState);
            dest.writeString(mZip);

        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + mStreet + '\'' +
                    ", City='" + mCity + '\'' +
                    ", State='" + mState + '\'' +
                    ", Zip='" + mZip + '\'' +
                    '}';
        }

    }



}
