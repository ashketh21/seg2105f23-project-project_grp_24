package com.example.grimpeurscyclingclubgcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class EnrollToEventActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");

    private TextView fullNameTextView;
    private EditText ageEditText;

    private DatabaseReference eventsRef;
    private DatabaseReference participantRef;

    private Participant participant;

    private Intent intent;

    private String clubOwnerName;

    private String date;

    private String eventType;

    private List<Event> eventList;

    private Event selectedEvent;

    private Button EnrollButton;
    private String participantName;
    private String enteredAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_to_event);
        intent = getIntent();

        //getting intent values
        clubOwnerName = intent.getStringExtra("clubOwnerName");
        date = intent.getStringExtra("date");
        eventType = intent.getStringExtra("eventType");
        participantName = intent.getStringExtra("participantName");


        fullNameTextView = findViewById(R.id.fullName);
        ageEditText = findViewById(R.id.age);

        fullNameTextView.setText(participantName);
        participantRef = database.getReference("users/" + participantName);
        participantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                participant = snapshot.getValue(Participant.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fullNameTextView = findViewById(R.id.fullName);
        eventsRef = database.getReference("users/" + clubOwnerName);

    }

    public void enroll(View view){
        enteredAge = ageEditText.getText().toString();


        if(enteredAge.isEmpty()){
            ageEditText.setError("please enter your age!");
            ageEditText.requestFocus();
            return;
        }

        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ClubOwner clubOwner = snapshot.getValue(ClubOwner.class);
                eventList = clubOwner.getEvents();
                for(Event event : eventList){
                    if(event.getEventName().equals(eventType) && event.getDate().equals(date)){
                        if(Integer.parseInt(enteredAge) < Integer.parseInt(event.getMinimumAge())){
                            ageEditText.setError("minimum age is "+ event.getMinimumAge());
                            ageEditText.requestFocus();
                            return;
                        }
                        //participant.addEnrolledEvent(event);
                        Event enrolledEvent = event;
                        event.addParticipants(participant);
                        clubOwner.setEvents(eventList);

                        eventsRef.setValue(clubOwner);
                        addToParticipant(participant,enrolledEvent);
                        break;
                    }
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addToParticipant(Participant participant,Event enrolledEvent) {
       DatabaseReference partRef = database.getReference("users/" + participant.getUsername());
        participant.addEnrolledEvent(enrolledEvent.getEventName()+ ", club Owner: " + enrolledEvent.getClubOwnerName());
        partRef.setValue(participant);
        Toast.makeText(EnrollToEventActivity.this, "enrolled!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EnrollToEventActivity.this, ParticipantActivity.class);
        intent.putExtra("username", participantName);
        startActivity(intent);
    }

}