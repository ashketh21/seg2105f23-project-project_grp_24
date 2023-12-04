package com.example.grimpeurscyclingclubgcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");

    private Spinner eventTypeSpinner;
    private Spinner clubOwnerSpinner;
    private DatabaseReference eventsRef;
    private DatabaseReference clubOwnerRef;
    ListView listViewOfferedEvents;
    List<Event> eventOfferedList = new ArrayList<>();

    String clubOwnerName = "show all";
    String eventType = "show all";
    String username;
    private List<Event> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        eventTypeSpinner = findViewById(R.id.eventTypeSpinner);
        clubOwnerSpinner = findViewById(R.id.clubOwnerSpinner);

        TextView welcomeParticipantTextView = findViewById(R.id.welcomeParticipantText);
        welcomeParticipantTextView.setText("Welcome "+ username);
        eventsRef = database.getReference("events/");
        clubOwnerRef = database.getReference("users/");

        retrieveEvents();
        retrieveClubOwners();

        listViewOfferedEvents = findViewById(R.id.listViewOfferedEvents);


        filterEventList(eventType, clubOwnerName);
    }

    public void showEnrolledEvents(View view){
        Intent intentToShow = new Intent(this, ShowEnrolledEvents.class);
        intentToShow.putExtra("participantName", username);
        startActivity(intentToShow);
    }

    public void filterEventListAgain(View view){
        eventType = eventTypeSpinner.getSelectedItem().toString();
        clubOwnerName = clubOwnerSpinner.getSelectedItem().toString();
        filterEventList(eventType, clubOwnerName);
    }

    private void filterEventList(String eventType, String clubOwnerName){
        eventOfferedList.clear();


        clubOwnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                //gets only the name of the events since only the list of the names will be displayed in the spinner
                for(DataSnapshot eventSnapshot : snapshot.getChildren()){
                    String role = eventSnapshot.child("role").getValue(String.class);
                    if(role.equals("Club Owner")){
                        ClubOwner clubOwner = eventSnapshot.getValue(ClubOwner.class);

                        if(eventType.equals("show all") && clubOwnerName.equals("show all")){
                            for(Event event : clubOwner.getEvents()) {
                                eventOfferedList.add(event);
                            }
                        }else if(eventType.equals("show all") && !clubOwnerName.equals("show all")){
                            if(clubOwner.getUsername().equals(clubOwnerName)){
                                List<Event> eventList = clubOwner.getEvents();
                                if(eventList != null){
                                    for(Event event : eventList){
                                        eventOfferedList.add(event);
                                    }
                                }
                            }
                        }else if(clubOwnerName.equals("show all") && !eventType.equals("show all")){

                                List<Event> eventList = clubOwner.getEvents();
                                if(eventList != null){
                                    for(Event event : eventList){
                                        if(event.getEventName().equals(eventType)){
                                            eventOfferedList.add(event);
                                        }
                                    }
                                }

                        } else{
                            if(clubOwner.getUsername().equals(clubOwnerName)){
                                List<Event> eventList = clubOwner.getEvents();
                                if(eventList != null){
                                    for(Event event : eventList){
                                        if(event.getEventName().equals(eventType)){
                                            eventOfferedList.add(event);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if(eventOfferedList != null){
                    FilterEventList eventAdaptor = new FilterEventList(ParticipantActivity.this, eventOfferedList,username);
                    listViewOfferedEvents.setAdapter(eventAdaptor);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void retrieveEvents(){
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> eventNames = new ArrayList<>();
                eventNames.add("show all");

                //gets only the name of the events since only the list of the names will be displayed in the spinner
                for(DataSnapshot eventSnapshot : snapshot.getChildren()){
                    String eventName = eventSnapshot.child("eventName").getValue(String.class);
                    eventNames.add(eventName);
                }

                //populate the spinner with event name
                populateSpinner(eventNames,eventTypeSpinner);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrieveClubOwners(){
        clubOwnerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> clubOwnersNames = new ArrayList<>();
                clubOwnersNames.add("show all");
                //gets only the name of the events since only the list of the names will be displayed in the spinner
                for(DataSnapshot clubOwnerSnapshot : snapshot.getChildren()){
                    String role = clubOwnerSnapshot.child("role").getValue(String.class);
                    if(role.equals("Club Owner")){
                        String clubOwnerName = clubOwnerSnapshot.child("username").getValue(String.class);
                        clubOwnersNames.add(clubOwnerName);
                    }

                }

                //populate the spinner with event name
                populateSpinner(clubOwnersNames, clubOwnerSpinner);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void populateSpinner(List<String> eventNames, Spinner spinnerType) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }
}