package com.techactivity.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


import com.techactivity.myapplication.Utils.FirebaseDBUtil;
import com.techactivity.myapplication.model.Child;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raywenderlich.android.validatetor.ValidateTor;


public class AddChildActivity extends AppCompatActivity {



    /**
     * We use this key to reference the list of messages in Firebase.
     */
    public static final String CHILD_FIREBASE_KEY = "kids";


    /**
     * This is a reference to the root of our Firebase. With this object, we can access any child
     * information in the database.
     */
    private FirebaseDatabase firebaseDB = FirebaseDBUtil.getDatabase();
    /**
     * Using the key, "child_info", we can access a reference to the list of messages. We will be
     * listening to changes to the children of this reference in this Activity.
     */
    private DatabaseReference childInfoReference = firebaseDB.getReference(CHILD_FIREBASE_KEY);




    private EditText childNameEntry;
    private EditText childAgeEntry;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        childNameEntry =findViewById(R.id.nameInput);
        childAgeEntry =findViewById(R.id.ageInput);
    }

    /**
     * Called when the "Send Message" button is clicked.
     *
     * @param view The button that was clicked.
     */
    public void addChild(View view) {

        //https://github.com/nisrulz/validatetor?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=7072
        //validate first then add or edit
        ValidateTor validateTor = new ValidateTor();
        Boolean mIsValid = true;
        String mFieldEmptyText = getString(R.string.validate_empty_field);

        // Check if password field is empty
        if (validateTor.isEmpty(childNameEntry.getText().toString())) {
            childNameEntry.setError(mFieldEmptyText);
            mIsValid = false;
        }


        if (validateTor.isEmpty(childAgeEntry.getText().toString())) {
            childAgeEntry.setError(mFieldEmptyText);
            mIsValid = false;
        }


        if(mIsValid) {
            pushDataToFirebase();
            finish();
        }
    }



    /**
     * Sends a message to Firebase
     */
    private void pushDataToFirebase() {
        String childNameContent = childNameEntry.getText().toString();
        String childAgeContent = childAgeEntry.getText().toString();

        Child childInfoToAdd = new Child(childNameContent,childAgeContent);
        childInfoReference.child(childNameContent).setValue(childInfoToAdd);
    }




}
