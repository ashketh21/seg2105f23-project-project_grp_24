package com.example.grimpeurscyclingclubgcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {

    //conecting to database
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAdminButtonClick(View views){

        //getting all the elements by ids
        Switch admin = findViewById(R.id.adminSwitch);
        EditText firstNameEditText = findViewById(R.id.firstName);
        EditText lastNameEditText = findViewById(R.id.lastName);
        Spinner roleSpinner = findViewById(R.id.role);
        EditText emailEditText = findViewById(R.id.email);
        TextView selectRoleText = findViewById(R.id.roleSelectText);

        //if admin hiding other fields
        if(admin.isChecked()){
            firstNameEditText.setVisibility(View.GONE);
            firstNameEditText.setText("");
            lastNameEditText.setVisibility((View.GONE));
            roleSpinner.setVisibility(View.GONE);
            emailEditText.setVisibility(View.GONE);
            selectRoleText.setVisibility(View.GONE);
        } else{

            //else making all fields visible
            firstNameEditText.setVisibility(View.VISIBLE);
            lastNameEditText.setVisibility(View.VISIBLE);
            roleSpinner.setVisibility(View.VISIBLE);
            emailEditText.setVisibility((View.VISIBLE));
            selectRoleText.setVisibility(View.VISIBLE);

        }
    }

    public void onRegisterButtonClick(View views){
        EditText firstNameEditText = findViewById(R.id.firstName);
        EditText lastNameEditText = findViewById(R.id.lastName);
        Spinner roleSpinner = findViewById(R.id.role);
        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        EditText userNameEditText = findViewById(R.id.userName);


        //getting values from all the fields in the register form
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String role = roleSpinner.getSelectedItem().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString().trim();
        String userName = userNameEditText.getText().toString().trim();

        //admin login logic
        Switch admin = findViewById(R.id.adminSwitch);
        if(admin.isChecked()){
            String user = "admin";
            if(!userName.equals(user) || !password.equals(user) ){
                userNameEditText.setError("Username is wrong");
                userNameEditText.requestFocus();
                passwordEditText.setError("Password is wrong");
                passwordEditText.requestFocus();
                return;
            }else{
                Intent intent = new Intent(this, WelcomeUserActivity.class);
                intent.putExtra("username", "admin");
                intent.putExtra("role", "admin");
                startActivity(intent);
            }
        }

        // boolean to keep track of the validation status in the registration form
        boolean valid = true;

        //username validation
        if(userName.isEmpty()){
            userNameEditText.setError("Username is required");
            userNameEditText.requestFocus();
            valid = false;
        }

        //firstname validation
        if (firstName.isEmpty()) {
            firstNameEditText.setError("First name is required");
            firstNameEditText.requestFocus();
            valid = false;
        }

        //lastname validation
        if (lastName.isEmpty()) {
            lastNameEditText.setError("Last name is required");
            lastNameEditText.requestFocus();
            valid = false;
        }

        // email validations
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            valid = false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid email address");
            emailEditText.requestFocus();
            valid = false;
        }

        //basic password validation
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            valid = false;
        }

        //checking if any of the validation was failed
        if(!valid){
            return;
        }




        //setting values of a user in database
        database.getReference("users/" + userName + "/username").setValue(userName);
        database.getReference("users/" + userName + "/firstName").setValue(firstName);
        database.getReference("users/" + userName + "/lastName").setValue(lastName);
        database.getReference("users/" + userName + "/role").setValue(role);
        database.getReference("users/" + userName + "/email").setValue(email);
        database.getReference("users/" + userName + "/password").setValue(password);


        //starting a new intent
        Intent intent = new Intent(this, WelcomeUserActivity.class);
        intent.putExtra("username", firstName);
        intent.putExtra("role", role);
        startActivity(intent);
    }




}
