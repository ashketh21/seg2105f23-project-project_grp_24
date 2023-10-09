package com.example.grimpeurscyclingclubgcc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onbuttonclick(View views){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-gcc-default-rtdb.firebaseio.com\n");
        DatabaseReference myName = database.getReference("user1/name");
        DatabaseReference myage = database.getReference("user1/age");

        DatabaseReference mygender = database.getReference("user1/gender");


        myName.setValue("cody");
        myage.setValue("32");
        mygender.setValue("male");
    }
}
