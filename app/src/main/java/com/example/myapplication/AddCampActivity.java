package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.Utils.FirebaseDBUtil;
import com.example.myapplication.model.Camp;

import com.example.myapplication.model.Child;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raywenderlich.android.validatetor.ValidateTor;

import java.util.ArrayList;
import java.util.Calendar;


public class AddCampActivity extends AppCompatActivity implements
        View.OnClickListener{

    /**
     * We use this key to reference the list of messages in Firebase.
     */
    public static final String CAMP_FIREBASE_KEY = "camps";
    public static final String CAMP_KID_FIREBASE_KEY = "kid_camps";
    /**
     * This is a reference to the root of our Firebase. With this object, we can access any child
     * information in the database.
     */
    private FirebaseDatabase firebaseDB = FirebaseDBUtil.getDatabase();
    /**
     * Using the key, "child_info", we can access a reference to the list of messages. We will be
     * listening to changes to the children of this reference in this Activity.
     */
    private DatabaseReference campInfoReference = firebaseDB.getReference().child(CAMP_FIREBASE_KEY);
    private DatabaseReference campskidReference = firebaseDB.getReference(CAMP_KID_FIREBASE_KEY);


    /*to read the camps related to the kid selected*/
    private DatabaseReference campsRef;



    private ValueEventListener valueEventListener;


    private TextView titlePage;
    private EditText campName;
    private EditText campContact;
    private EditText campPhone;
    private EditText campStreet;
    private EditText campCity;
    private EditText campState;
    private EditText campZip;
    private Boolean mIsValid;

    DatePickerDialog picker;
    private EditText campWeekFrom; //eText
    Button btnGetStartDate;
    Button btnGetStartTime;
    Button btnGetEndDate;
    Button btnGetEndTime;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private EditText campWeekTo;
    private EditText campHoursFrom;
    private EditText campHoursTo;
    private EditText campNotes;

    private Child mChildSelectedInfo;
    private Camp mCampInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_camp);


        final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        try{
            mChildSelectedInfo  = intent.getParcelableExtra("Child.Selected");

          mCampInfo  = intent.getParcelableExtra("Camp.Info");


            if (mChildSelectedInfo == null) {
                closeOnError();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        titlePage = findViewById(R.id.tv_titleMain);
        campName = findViewById(R.id.campNameInput);
        campContact = findViewById(R.id.contactInput);
        campPhone = findViewById(R.id.phoneInput);
        campStreet = findViewById(R.id.streetInput);
        campCity = findViewById(R.id.cityInput);
        campState = findViewById(R.id.stateInput);
        campZip = findViewById(R.id.zipInput);
        campWeekFrom = findViewById(R.id.dateFromInput);
        btnGetStartDate=findViewById(R.id.btn_startDate);
        btnGetEndDate=findViewById(R.id.btn_endDate);
        campWeekTo = findViewById(R.id.dateToInput);
        campHoursFrom = findViewById(R.id.hrsFromInput);
        btnGetStartTime=findViewById(R.id.btn_startTime);
        btnGetEndTime=findViewById(R.id.btn_endTime);
        campHoursTo = findViewById(R.id.hrsToInput);
        campNotes = findViewById(R.id.notesInput);


        btnGetStartDate.setOnClickListener(this);
        btnGetStartTime.setOnClickListener(this);

        btnGetEndDate.setOnClickListener(this);
        btnGetEndTime.setOnClickListener(this);

        Spinner spinner =  findViewById(R.id.hasLunch_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hasLunch_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }


//https://www.journaldev.com/9976/android-date-time-picker-dialog

    @Override
    public void onClick(View v) {

        if (v == btnGetStartDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            campWeekFrom.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnGetEndDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            campWeekTo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }


       if (v == btnGetStartTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            campHoursFrom.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == btnGetEndTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            campHoursTo.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

}




    @Override
    protected void onStart() {
        super.onStart();
        if(mCampInfo != null) {
            titlePage.setText("Edit Camp");
            campName.setEnabled(false);

            DatabaseReference rootRef = FirebaseDBUtil.getDatabase().getReference();
            campsRef = rootRef.child("camps");
            campsRef.keepSynced(true);


            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(ds.getKey().equalsIgnoreCase(mCampInfo.getCampName())){

                            mCampInfo.setContact(ds.child("contact").getValue(String.class));
                            mCampInfo.setPhone(ds.child("phone").getValue(String.class));
                            mCampInfo.setStreet(ds.child("street").getValue(String.class));
                            mCampInfo.setCity(ds.child("city").getValue(String.class));
                            mCampInfo.setState(ds.child("state").getValue(String.class));
                            mCampInfo.setZip(ds.child("zip").getValue(String.class));
                            mCampInfo.setWeekFrom(ds.child("weekFrom").getValue(String.class));
                            mCampInfo.setWeekTo(ds.child("weekTo").getValue(String.class));
                            mCampInfo.setHrsFrom(ds.child("hrsFrom").getValue(String.class));
                            mCampInfo.setHrsTo(ds.child("hrsTo").getValue(String.class));
                            mCampInfo.setNotes(ds.child("notes").getValue(String.class));

                        }

                    }

                    campName.setText(mCampInfo.getCampName());
                    campContact.setText(mCampInfo.getContact());
                    campPhone.setText(mCampInfo.getPhone());
                    campStreet.setText(mCampInfo.getStreet());
                    campCity.setText(mCampInfo.getCity());
                    campState.setText(mCampInfo.getState());
                    campZip.setText(mCampInfo.getZip());
                    campWeekFrom.setText(mCampInfo.getWeekFrom());
                    campWeekTo.setText(mCampInfo.getWeekTo());
                    campHoursFrom.setText(mCampInfo.getHrsFrom());
                    campHoursTo.setText(mCampInfo.getHrsTo());
                    campNotes.setText(mCampInfo.getNotes());


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };

            campsRef.addValueEventListener(valueEventListener);

        }

    }


    /**
     * Called when the "Send Message" button is clicked.
     *
     * @param view The button that was clicked.
     */
    public void addCamp(View view) {
        //https://github.com/nisrulz/validatetor?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=7072
        //validate first then add or edit
        ValidateTor validateTor = new ValidateTor();
        mIsValid = true;


        // Check if password field is empty
        if (validateTor.isEmpty(campName.getText().toString())) {
            campName.setError("Field is empty!");
            mIsValid = false;
        }


        if (validateTor.isEmpty(campContact.getText().toString())) {
            campContact.setError("Field is empty!");
            mIsValid = false;
        }



        if (validateTor.isEmpty(campPhone.getText().toString())) {
            campPhone.setError("Field is empty!");
            mIsValid = false;
        }



        if (validateTor.isEmpty(campStreet.getText().toString())) {
            campStreet.setError("Field is empty!");
            mIsValid = false;
        }



        if (validateTor.isEmpty(campCity.getText().toString())) {
            campCity.setError("Field is empty!");
            mIsValid = false;
        }
        if (validateTor.isEmpty(campState.getText().toString())) {
            campState.setError("Field is empty!");
            mIsValid = false;
        }
        if (validateTor.isEmpty(campZip.getText().toString())) {
            campZip.setError("Field is empty!");
            mIsValid = false;
        }
        if (validateTor.isEmpty(campWeekFrom.getText().toString())) {
            campWeekFrom.setError("Field is empty!");
            mIsValid = false;
        }
        if (validateTor.isEmpty(campWeekTo.getText().toString())) {
            campWeekTo.setError("Field is empty!");
            mIsValid = false;
        }
        if (validateTor.isEmpty(campHoursFrom.getText().toString())) {
            campHoursFrom.setError("Field is empty!");
            mIsValid = false;
        }
        if (validateTor.isEmpty(campHoursTo.getText().toString())) {
            campHoursTo.setError("Field is empty!");
            mIsValid = false;
        }





        if(mIsValid){
            pushDataToFirebase();
            //resetInputField();
            //hideKeyboard();
            finish();
        }


    }


    /**
     * Sends a message to Firebase
     */
    private void pushDataToFirebase() {


        if(mCampInfo != null) {
           //EDIT
           // campInfoReference.child(mCampInfo.getCampName()).child("campName").setValue(campName.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("contact").setValue(campContact.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("phone").setValue(campPhone.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("street").setValue(campStreet.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("city").setValue(campCity.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("state").setValue(campState.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("zip").setValue(campZip.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("weekFrom").setValue(campWeekFrom.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("weekTo").setValue(campWeekTo.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("hrsFrom").setValue(campHoursFrom.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("hrsTo").setValue(campHoursTo.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("notes").setValue(campNotes.getText().toString());

        }else{
            //ADD
            String campNameData = campName.getText().toString();
            String campContactData = campContact.getText().toString();
            String campPhoneData = campPhone.getText().toString();
            String campStreetData = campStreet.getText().toString();
            String campCityData = campCity.getText().toString();
            String campStateData = campState.getText().toString();
            String campZipData = campZip.getText().toString();
            String campWeekFromData = campWeekFrom.getText().toString();
            String campWeekToData = campWeekTo.getText().toString();
            String campHoursFromData = campHoursFrom.getText().toString();
            String campHoursToData = campHoursTo.getText().toString();
            String campNotesData = campNotes.getText().toString();
            //TODO set hasLunch to the real dropdown value
            Camp campInfoToAdd = new Camp(campNameData,campContactData,campPhoneData,campStreetData,campCityData,campStateData,campZipData,campWeekFromData,campWeekToData,campHoursFromData,campHoursToData,"YES",campNotesData);
            // campInfoReference.push().setValue(campInfoToAdd);
            campInfoReference.child(campNameData).setValue(campInfoToAdd);
            String childName = mChildSelectedInfo.getChildName();
            //TODO set the value of the camp to the start week
            campskidReference.child(childName).child(campNameData).setValue(true);
        }



    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }



}
