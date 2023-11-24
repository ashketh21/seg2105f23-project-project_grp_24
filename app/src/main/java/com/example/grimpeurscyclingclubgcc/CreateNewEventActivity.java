package com.example.grimpeurscyclingclubgcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class CreateNewEventActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_event);
    }

    public void addEvent(View view){
        EditText eventNameEditText = findViewById(R.id.eventTypeName);
        EditText minimumAgeEditText = findViewById(R.id.minimumAge);
        EditText feeEditText = findViewById(R.id.fee);
        EditText descriptionEditText = findViewById(R.id.eventDescription);
        EditText difficultyEditText = findViewById(R.id.difficulty);
        EditText regionEditText = findViewById(R.id.region);
        EditText paceEditText = findViewById(R.id.pace);
        EditText participantsAllowedEditText = findViewById(R.id.participantsAllowed);

        String eventName = eventNameEditText.getText().toString();
        String minimumAge = minimumAgeEditText.getText().toString();
        String fee = feeEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String difficulty = difficultyEditText.getText().toString();
        String region = regionEditText.getText().toString();
        String pace = paceEditText.getText().toString();
        String participants = participantsAllowedEditText.getText().toString();

        boolean valid = true;


        if(eventName.isEmpty()){
            eventNameEditText.setError("Event name is required");
            eventNameEditText.requestFocus();
            valid = false;
        }
        if(region.isEmpty()){
            regionEditText.setError("Region is required");
            regionEditText.requestFocus();
            valid = false;
        }

        if(minimumAge.isEmpty()){
            minimumAgeEditText.setError("age is required");
            minimumAgeEditText.requestFocus();
            valid = false;
        }
        if(fee.isEmpty()){
            feeEditText.setError("fee is required");
            feeEditText.requestFocus();
            valid = false;
        }
        if(description.isEmpty()){
            descriptionEditText.setError("Description is required");
            descriptionEditText.requestFocus();
            valid = false;
        }
        if(pace.isEmpty()){
            paceEditText.setError("pace is required");
            paceEditText.requestFocus();
            valid = false;
        }
        if(difficulty.isEmpty()){
            difficultyEditText.setError("difficulty level is required");
            difficultyEditText.requestFocus();
            valid = false;
        }
        if(participants.isEmpty()){
            participantsAllowedEditText.setError("participants number is required");
            participantsAllowedEditText.requestFocus();
            valid = false;
        }

        if(!valid){
            return;
        }

        database.getReference("events/" + eventName + "/eventName").setValue(eventName);
        database.getReference("events/" + eventName + "/minimumAge").setValue(minimumAge);
        database.getReference("events/" + eventName + "/fee").setValue(fee);
        database.getReference("events/" + eventName + "/description").setValue(description);
        database.getReference("events/" + eventName + "/pace").setValue(pace);
        database.getReference("events/" + eventName + "/region").setValue(region);
        database.getReference("events/" + eventName + "/difficulty").setValue(difficulty);
        database.getReference("events/" + eventName + "/participants").setValue(participants);
        database.getReference("events/" + eventName + "/date").setValue("");
        database.getReference("events/" + eventName + "/distance").setValue(0.0);
        database.getReference("events/" + eventName + "/elevation").setValue(0.0);
        database.getReference("events/" + eventName + "/landmark").setValue("");


        Toast.makeText(this, "New event added!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, AdministratorActivity.class);
        startActivity(intent);
    }
}