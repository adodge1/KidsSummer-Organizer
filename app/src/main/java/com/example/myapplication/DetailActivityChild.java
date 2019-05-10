package com.example.myapplication;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.Child;

public class DetailActivityChild extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_child);
        final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        try{
            Child childInfo  = intent.getParcelableExtra("Child.Details");
            if (childInfo == null) {
                    closeOnError();

            }else{
                TextView childNameTitle = findViewById(R.id.tv_titleChildDetail);
                childNameTitle.setText(childInfo.getChildName());
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    public void getAddCampActivity(View view)
    {
        Intent intent = new Intent(this, AddCampActivity.class);
        startActivity(intent);
    }




}
