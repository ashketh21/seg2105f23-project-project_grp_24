package com.example.grimpeurscyclingclubgcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    ListView listViewUsers;
    List<Event> eventList = new ArrayList<>();
    List<User> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);


       listViewEvents = findViewById(R.id.listViewEvents);
       listViewUsers = findViewById(R.id.listViewUsers);


       DatabaseReference eventsRef = database.getReference("events/");
        DatabaseReference usersRef = database.getReference("users/");
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    eventList.add(event);
                }

                EventList eventAdaptor = new EventList(AdministratorActivity.this, eventList);
                listViewEvents.setAdapter(eventAdaptor);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors here
            }
        });
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataUserSnapshot) {
                userList.clear();
                for (DataSnapshot userSnapshot : dataUserSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    userList.add(user);
                }

                UserList userAdaptor = new UserList(AdministratorActivity.this, userList);
                listViewUsers.setAdapter(userAdaptor);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors here
            }
        });





        listViewEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Event event = eventList.get(position);
                showUpdateDeleteDialog(event);

                return true;
            }
        });

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private void showUpdateDeleteDialog(final Event event){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);


        EditText eventNameEditText = dialogView.findViewById(R.id.eventTypeName);
        EditText minimumAgeEditText = dialogView.findViewById(R.id.minimumAge);
        EditText feeEditText = dialogView.findViewById(R.id.fee);
        EditText descriptionEditText = dialogView.findViewById(R.id.eventDescription);
        EditText difficultyEditText = dialogView.findViewById(R.id.difficulty);
        EditText paceEditText = dialogView.findViewById(R.id.pace);
        EditText regionEditText = dialogView.findViewById(R.id.region);

        EditText participantsAllowedEditText = dialogView.findViewById(R.id.participantsAllowed);



        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateButton);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteButton);

        dialogBuilder.setTitle(event.getEventName());


        eventNameEditText.setText(event.getEventName());
        minimumAgeEditText.setText(event.getMinimumAge());
        feeEditText.setText(event.getFee());
        descriptionEditText.setText(event.getDescription());
        difficultyEditText.setText(event.getDifficulty());
        paceEditText.setText(event.getPace());
        regionEditText.setText(event.getRegion());
        participantsAllowedEditText.setText(event.getParticipants());


        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = eventNameEditText.getText().toString();
                String minimumAge = minimumAgeEditText.getText().toString();
                String fee = feeEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String difficulty = difficultyEditText.getText().toString();
                String pace = paceEditText.getText().toString();
                String region = regionEditText.getText().toString();
                String participants = participantsAllowedEditText.getText().toString();
                boolean valid = true;


                if(name.isEmpty()){
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
                if (!TextUtils.isEmpty(name)) {
                    updateEvent( event.getEventName(),name,minimumAge,fee, description,difficulty,region,pace,participants);
                    b.dismiss();
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(event.getEventName());
                b.dismiss();
            }
        });
    }

    private void updateEvent( String eventName,String name,String minimumAge,String fee,String description,String difficulty,String region, String pace, String participants) {
        DatabaseReference eventsRef = database.getReference("events/" + eventName);
        Event event = new Event(name,minimumAge,fee, description,difficulty,region, pace,participants);
        eventsRef.setValue(event);

        Toast.makeText(getApplicationContext(),"Product updated", Toast.LENGTH_LONG).show();
    }
    private boolean deleteEvent(String eventName) {
        DatabaseReference eventsRef = database.getReference("events/" + eventName);
        eventsRef.removeValue();
        Toast.makeText(getApplicationContext(), "Product deleted", Toast.LENGTH_LONG).show();
        return true;
    }




    public void createEvent(View views){
        Intent intent = new Intent(this, CreateNewEventActivity.class);
        startActivity(intent);
    }
}