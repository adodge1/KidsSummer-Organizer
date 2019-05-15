package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private DatabaseReference campInfoReference = firebaseDB.getReference(CAMP_FIREBASE_KEY);
    private DatabaseReference campskidReference = firebaseDB.getReference(CAMP_KID_FIREBASE_KEY);



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


        Spinner spinner = (Spinner) findViewById(R.id.hasLunch_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hasLunch_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

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


        Camp.Address campAddress = new Camp.Address(campStreetData,campCityData,campStateData,campZipData);

         Camp campInfoToAdd = new Camp(campNameData,campContactData,campPhoneData,campAddress,campWeekFromData,campWeekToData,campHoursFromData,campHoursToData,true,campNotesData);
        // campInfoReference.push().setValue(campInfoToAdd);
        campInfoReference.child(campNameData).setValue(campInfoToAdd);
         String childName = mChildSelectedInfo.getChildName();

        campskidReference.child(childName).child(campNameData).setValue(true);


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }



}
