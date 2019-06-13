package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Utils.FirebaseDBUtil;
import com.example.myapplication.model.Camp;

import com.example.myapplication.model.Child;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCampActivity extends AppCompatActivity {

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

    private DatabaseReference campsDBRef;

    private ValueEventListener valueEventListener;
    private ArrayList<Camp> camps = new ArrayList<>();



    private EditText campName;
    private EditText campContact;
    private EditText campPhone;
    private EditText campStreet;
    private EditText campCity;
    private EditText campState;
    private EditText campZip;
    private EditText campWeekFrom;
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

        campName = findViewById(R.id.campNameInput);
        campContact = findViewById(R.id.contactInput);
        campPhone = findViewById(R.id.phoneInput);
        campStreet = findViewById(R.id.streetInput);
        campCity = findViewById(R.id.cityInput);
        campState = findViewById(R.id.stateInput);
        campZip = findViewById(R.id.zipInput);
        campWeekFrom = findViewById(R.id.dateFromInput);
        campWeekTo = findViewById(R.id.dateToInput);
        campHoursFrom = findViewById(R.id.hrsFromInput);
        campHoursTo = findViewById(R.id.hrsToInput);
        campNotes = findViewById(R.id.notesInput);




        Spinner spinner =  findViewById(R.id.hasLunch_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hasLunch_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mCampInfo != null) {
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
        pushDataToFirebase();
        //resetInputField();
        //hideKeyboard();
        finish();
    }


    /**
     * Sends a message to Firebase
     */
    private void pushDataToFirebase() {


        if(mCampInfo != null) {
           //push data to edit!!!

            campInfoReference.child(mCampInfo.getCampName()).child("campName").setValue(campName.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("contact").setValue(campContact.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("phone").setValue(campPhone.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("street").setValue(campStreet.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("city").setValue(campCity.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("state").setValue(campState.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("zip").setValue(campZip.getText().toString());
            campInfoReference.child(mCampInfo.getCampName()).child("notes").setValue(campNotes.getText().toString());


            //https://www.firebase.com/docs/android/examples.html



          //  String name = campName.getText().toString();
          // Toast.makeText(this, name, Toast.LENGTH_LONG).show();

        }else{

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
