package com.example.grimpeurscyclingclubgcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEventActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");
    private Spinner eventSpinner;
    EditText minimumAgeEditText ;
    EditText feeEditText ;
    EditText descriptionEditText ;
    EditText difficultyEditText ;
    EditText regionEditText ;
    EditText paceEditText ;
    EditText participantsAllowedEditText ;

    EditText dateEditText;

    EditText distanceEditText;

    EditText elevationEditText;

    EditText landmarkEditText;
    private DatabaseReference eventsRef;
    private DatabaseReference selectedEventRef;

    Event event;
    Intent intent;

    String clubownerName;

    ClubOwner clubowner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
         intent = getIntent();
        clubownerName = intent.getStringExtra("username");

        eventSpinner = findViewById(R.id.eventSpinner);
        minimumAgeEditText = findViewById(R.id.minimumAge);
        feeEditText = findViewById(R.id.fee);
        descriptionEditText = findViewById(R.id.eventDescription);
        difficultyEditText = findViewById(R.id.difficulty);
        regionEditText = findViewById(R.id.region);
        paceEditText = findViewById(R.id.pace);
        participantsAllowedEditText = findViewById(R.id.participantsAllowed);
        dateEditText = findViewById(R.id.date);
        distanceEditText = findViewById(R.id.distance);
        elevationEditText = findViewById(R.id.elevation);
        landmarkEditText = findViewById(R.id.landmark);

        eventsRef = database.getReference("events/");

        retrieveEvents();
        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Retrieve and display details for the selected event type
                String eventType = eventSpinner.getSelectedItem().toString();
                selectedEventRef = database.getReference("events/" + eventType);

                selectedEventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Event event = snapshot.getValue(Event.class);

                        // Check if event is not null before setting the values
                        if (event != null) {
                            minimumAgeEditText.setText(String.valueOf(event.getMinimumAge()));
                            feeEditText.setText(String.valueOf(event.getFee()));
                            descriptionEditText.setText(event.getDescription());
                            difficultyEditText.setText(event.getDifficulty());
                            regionEditText.setText(event.getRegion());
                            paceEditText.setText(event.getPace());
                            participantsAllowedEditText.setText(String.valueOf(event.getParticipants()));
                            dateEditText.setText(event.getDate());
                            landmarkEditText.setText(event.getLandmarks());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle errors
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
            }
        });
    }

    private void retrieveEvents(){
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> eventNames = new ArrayList<>();

                //gets only the name of the events since only the list of the names will be displayed in the spinner
                for(DataSnapshot eventSnapshot : snapshot.getChildren()){
                    String eventName = eventSnapshot.child("eventName").getValue(String.class);
                    eventNames.add(eventName);
                }

                //populate the spinner with event name
                populateSpinner(eventNames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void populateSpinner(List<String> eventNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(adapter);
    }

    public void addEventToList(View view){


        DatabaseReference clubownerRef = database.getReference("users/" + clubownerName);


        String eventName = eventSpinner.getSelectedItem().toString();
        String minimumAge = minimumAgeEditText.getText().toString();
        String fee = feeEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String difficulty = difficultyEditText.getText().toString();
        String region = regionEditText.getText().toString();
        String pace = paceEditText.getText().toString();
        String participants = participantsAllowedEditText.getText().toString();
        String date = dateEditText.getText().toString();
        Double distance = Double.parseDouble(distanceEditText.getText().toString());
        Double elevation = Double.parseDouble(elevationEditText.getText().toString());
        String landmark = landmarkEditText.getText().toString();

        boolean valid = true;



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

        if(!isValidDate(date)){
            dateEditText.setError("please enter a date in dd-mm-yyyy format");
            dateEditText.requestFocus();
            valid = false;
        }

        if(!valid){
            return;
        }


        if(valid){
            event = new Event(eventName,minimumAge,fee,description,difficulty,region,pace,participants,date,distance,elevation,landmark);
            clubownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    clubowner = snapshot.getValue(ClubOwner.class);

                    if (clubowner != null) {
                        clubowner.addEvent(event);
                        clubownerRef.setValue(clubowner);
                    } else {

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Toast.makeText(this, "New event added!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ClubOwnerActivity.class);
            intent.putExtra("username", clubownerName);
            startActivity(intent);
        }

    }


    // helper method for date check
    private boolean isValidDate(String date){
        String DATE_PATTERN = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
























