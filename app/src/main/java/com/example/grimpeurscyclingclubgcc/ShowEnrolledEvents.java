package com.example.grimpeurscyclingclubgcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowEnrolledEvents extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");
    private List<String> eventList = new ArrayList<>();
    private String participantName;
    private ListView enrolledEventList;
    DatabaseReference participantRef;

    Participant participant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_enrolled_events);
        enrolledEventList = findViewById(R.id.enrolledEventList);
        Intent intent = getIntent();
        participantName = intent.getStringExtra("participantName");

        participantRef = database.getReference("users/"+ participantName);

        participantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                participant = snapshot.getValue(Participant.class);
                eventList = participant.getEnrolledEvents();
                if(eventList != null){
                    EnrolledEventList eventAdaptor = new EnrolledEventList(ShowEnrolledEvents.this, eventList, participant.getUsername());
                    enrolledEventList.setAdapter(eventAdaptor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}