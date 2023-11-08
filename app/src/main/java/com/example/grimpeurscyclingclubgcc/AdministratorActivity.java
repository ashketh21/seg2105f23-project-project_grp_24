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
                showUpdateDeleteDialog(event.getEventName());

                return true;
            }
        });

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private void showUpdateDeleteDialog(final String eventName){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextEventName = (EditText) dialogView.findViewById(R.id.editTextEventName);
        final EditText editTextDescription  = (EditText) dialogView.findViewById(R.id.editTextDescription);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateButton);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteButton);

        dialogBuilder.setTitle(eventName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextEventName.getText().toString().trim();
                String description = editTextDescription.getText().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateEvent( eventName,name, description);
                    b.dismiss();
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(eventName);
                b.dismiss();
            }
        });
    }

    private void updateEvent( String eventName,String name, String description) {
        DatabaseReference eventsRef = database.getReference("events/" + eventName);
        Event event = new Event(name,description);
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