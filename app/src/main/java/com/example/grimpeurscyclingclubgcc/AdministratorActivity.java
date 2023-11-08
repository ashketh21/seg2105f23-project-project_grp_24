package com.example.grimpeurscyclingclubgcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdministratorActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");

    ListView listViewEvents;
    List<Event> eventList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);


       listViewEvents = findViewById(R.id.listViewEvents);
        DatabaseReference eventsRef = database.getReference("events/");
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    eventList.add(event);
                }
                // Update your ListView adapter here
//                ArrayAdapter<Event> adapter = new ArrayAdapter<>(AdministratorActivity.this, android.R.layout.simple_list_item_1, eventList);
//                ListView listView = findViewById(R.id.listViewEvents); // Replace with your ListView ID
//                listView.setAdapter(adapter);

                EventList eventAdaptor = new EventList(AdministratorActivity.this, eventList);
                listViewEvents.setAdapter(eventAdaptor);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors here
            }
        });

    }





    public void createEvent(View views){
        Intent intent = new Intent(this, CreateNewEventActivity.class);
        startActivity(intent);


    }
}