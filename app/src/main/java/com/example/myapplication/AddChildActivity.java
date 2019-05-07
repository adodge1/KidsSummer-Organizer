package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.content.Context;
import com.example.myapplication.model.Child;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class AddChildActivity extends AppCompatActivity {



    /**
     * We use this key to reference the list of messages in Firebase.
     */
    public static final String CHILD_FIREBASE_KEY = "child_info";


    /**
     * This is a reference to the root of our Firebase. With this object, we can access any child
     * information in the database.
     */
    private FirebaseDatabase firebaseDB = FirebaseDatabase.getInstance();
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
        pushMessageToFirebase();
        //resetInputField();
        //hideKeyboard();
        finish();
    }



    /**
     * Sends a message to Firebase
     */
    private void pushMessageToFirebase() {
        String childNameContent = childNameEntry.getText().toString();
        String childAgeContent = childAgeEntry.getText().toString();

        Child childInfoToAdd = new Child(childNameContent,childAgeContent);
        childInfoReference.push().setValue(childInfoToAdd);
    }



    /**
     * Clears the entered text. Used after a data is sent to firebase
     */
    private void resetInputField() {
        childAgeEntry.setText("");
        childNameEntry.setText("");

    }

    /**
     * Hides the soft keyboard. Used after a message is sent.
     */

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(childAgeEntry.getWindowToken(), 0);
    }




}
