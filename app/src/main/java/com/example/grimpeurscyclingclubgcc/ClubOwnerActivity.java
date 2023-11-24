package com.example.grimpeurscyclingclubgcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
                showUpdateDeleteDialog(event);

                return true;
            }
        });


    }

    private void showUpdateDeleteDialog(final Event event){
        //Mostafa implement this method
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