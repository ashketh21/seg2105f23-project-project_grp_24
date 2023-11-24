package com.example.grimpeurscyclingclubgcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {

    //conecting to database
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void onLoginButtonClick(View views){

        // getting the fields information
        EditText passwordEditText = findViewById(R.id.password);
        EditText userNameEditText = findViewById(R.id.userName);

        //converting input to string
        String password = passwordEditText.getText().toString().trim();
        String userName = userNameEditText.getText().toString().trim();

        //checking if admin
        if(userName.equals("admin")  && password.equals("admin")){
            Intent intent = new Intent(this, AdministratorActivity.class); //start a admin intent and transfer to that activity
            startActivity(intent);
        } else{

            //check for the regular user in database and authenticate the user.
            DatabaseReference ref = database.getReference("users/" + userName);
            ref.addValueEventListener(new ValueEventListener() {  //callback function to impliment the listener and check the user credentials
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);  //getting the user object

                    //checking if username and password are correct as per DB
                    if(user.getUsername().equals(userName) && user.getPassword().equals(password)){

                        if(user.getRole().equals("Club Owner")){
                            Intent intent = new Intent(MainActivity.this, ClubOwnerActivity.class);
                            intent.putExtra("username", userName);
                            startActivity(intent);
                        }
                        else if(user.getRole().equals("Participant")){
                            Intent intent = new Intent(MainActivity.this, ParticipantActivity.class);
                            intent.putExtra("username", userName);
                            intent.putExtra("role", user.getRole());
                            startActivity(intent);
                        }
                    }
                    else{
                        //if not found or incorrect / toast a msg for that
                        Toast.makeText(getApplicationContext(), "User not found/Incorrect credentials ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }


    }


    public void onRegisterButtonClick(View views){

        //starting a new intent
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }




}
