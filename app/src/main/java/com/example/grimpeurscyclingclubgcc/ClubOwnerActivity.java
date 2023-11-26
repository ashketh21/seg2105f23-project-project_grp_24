package com.example.grimpeurscyclingclubgcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

public class ClubOwnerActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");

    private EditText socialMediaLinkEditText;

    private EditText contactPersonNameEditText;
    private EditText phoneNumberEditText ;

    private DatabaseReference ref;

    private ClubOwner user;
    ListView listViewEvents;

    List<Event> eventList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_owner);

        socialMediaLinkEditText = findViewById(R.id.socialMediaLink);
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        contactPersonNameEditText = findViewById(R.id.contactPersonName);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        ref = database.getReference("users/" + username);

        //setting the text on the pop up screen when logging in
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(ClubOwner.class);

                socialMediaLinkEditText.setText(user.getSocialMediaLink());
                contactPersonNameEditText.setText(user.getContactPersonName());
                phoneNumberEditText.setText(user.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //listview logic starts here

        listViewEvents = findViewById(R.id.listViewEvents);

        DatabaseReference clubOwnerRef = database.getReference("users/" + username );

        clubOwnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();

                ClubOwner clubowner = snapshot.getValue(ClubOwner.class);

                eventList = clubowner.getEvents();

                ClubOwnerEventList eventAdaptor = new ClubOwnerEventList(ClubOwnerActivity.this, eventList);
                listViewEvents.setAdapter(eventAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listViewEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Event event = eventList.get(position);
                showUpdateDeleteDialog(event,position, username);

                return true;
            }
        });


    }

    private void showUpdateDeleteDialog(final Event event, int position, String username){
        //Mostafa implement this method
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.co_update_event, null);
        dialogBuilder.setView(dialogView);

        EditText minAge = dialogView.findViewById(R.id.minimumAge);
        EditText feeEditText = dialogView.findViewById(R.id.fee);
        EditText descriptionEditText = dialogView.findViewById(R.id.eventDescription);
        EditText difficultyEditText = dialogView.findViewById(R.id.difficulty);
        EditText regionEditText = dialogView.findViewById(R.id.region);
        EditText paceEditText = dialogView.findViewById(R.id.pace);
        EditText participantsAllowedEditText = dialogView.findViewById(R.id.participantsAllowed);
        EditText dateEditText = dialogView.findViewById(R.id.date);
        EditText distanceEditText = dialogView.findViewById(R.id.distance);
        EditText elevationEditText = dialogView.findViewById(R.id.elevation);
        EditText landmarkEditText = dialogView.findViewById(R.id.landmark);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.UpdateEventButton);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.DeleteEventButton);

        dialogBuilder.setTitle(event.getEventName());

        minAge.setText(String.valueOf(event.getMinimumAge()));
        feeEditText.setText(String.valueOf(event.getFee()));
        descriptionEditText.setText(event.getDescription());
        difficultyEditText.setText(event.getDifficulty());
        regionEditText.setText(event.getRegion());
        paceEditText.setText(event.getPace());
        participantsAllowedEditText.setText(String.valueOf(event.getParticipants()));
        dateEditText.setText(event.getDate());

        String dist = Double.toString(event.getDistance());
        distanceEditText.setText(dist);

        String elevation = Double.toString(event.getElevation());
        elevationEditText.setText(elevation);

        landmarkEditText.setText(event.getLandmarks());

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minimumAge = minAge.getText().toString();
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
                    minAge.setError("age is required");
                    minAge.requestFocus();
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
                    Event updatedEvent = new Event(event.getEventName(),minimumAge,fee,description,difficulty,region,pace,participants,date,distance,elevation,landmark);
                    updateEvent(updatedEvent, position, username);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(position, username);
                b.dismiss();
            }
        });

    }

    private void updateEvent(Event event, int position, String username){
        ref = database.getReference("users/" + username +"/events/"+position);
        ref.setValue(event);
        Toast.makeText(getApplicationContext(),"Event updated", Toast.LENGTH_LONG).show();
    }

    private boolean deleteEvent(int position, String username){
        ref = database.getReference("users/" + username +"/events/"+position);
        ref.removeValue();
        Toast.makeText(getApplicationContext(), "Event deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean isValidDate(String date){
        String DATE_PATTERN = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public void updateProfile(View view){

        String socialMediaLink = socialMediaLinkEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String contactPersonName = contactPersonNameEditText.getText().toString().trim();

        //checking if fields are valid
        if(!isValidLink(socialMediaLink) || !isValidPhoneNumber(phoneNumber)){
            if(!isValidLink(socialMediaLink)){
                socialMediaLinkEditText.setError("Please enter a valid link");
                socialMediaLinkEditText.requestFocus();
            } else {
                phoneNumberEditText.setError("Please enter a valid number without spaces");
                phoneNumberEditText.requestFocus();
            }
        }else{
            user.setContactPersonName(contactPersonName);
            user.setPhoneNumber(phoneNumber);
            user.setSocialMediaLink(socialMediaLink);
            ref.setValue(user);
            Toast.makeText(this, "Profile Updated!!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phonePattern = "^[+]?[0-9]{10,13}$";

        Pattern pattern = Pattern.compile(phonePattern);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    private boolean isValidLink(String socialMediaLink) {
        String socialMediaPattern = "^(https?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(/[a-zA-Z0-9-._?%&=]*)?$";

        Pattern pattern = Pattern.compile(socialMediaPattern);
        Matcher matcher = pattern.matcher(socialMediaLink);
        System.out.println("result=" + matcher.matches());
        return matcher.matches();
    }


    public void addEvent(View view){


        String socialMediaLink = socialMediaLinkEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();

        //checking if the mandatory fields are filled before making a new event
        if(socialMediaLink.isEmpty() || phoneNumber.isEmpty()){
            if(socialMediaLink.isEmpty()){
                socialMediaLinkEditText.setError("Social media link is needed");
                socialMediaLinkEditText.requestFocus();
            } else {
                phoneNumberEditText.setError("phone number is needed");
                phoneNumberEditText.requestFocus();
            }
        } else{
            Intent intent = new Intent(this, AddEventActivity.class);
            intent.putExtra("username" , user.getUsername());
            startActivity(intent);
        }


    }
}