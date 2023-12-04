package com.example.grimpeurscyclingclubgcc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EnrolledEventList extends ArrayAdapter<String> {
    private Activity context;
    List<String> events;
    String username;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");

    public EnrolledEventList(Activity context, List<String> events, String username) {
        super(context, R.layout.activity_enrolled_event_list, events);
        this.context = context;
        this.events = events;
        this.username = username;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_enrolled_event_list, null, true);

        TextView textView = (TextView) listViewItem.findViewById(R.id.eventText);

        String event = events.get(position);
        textView.setText(event);

        Button reviewbutton = (Button) listViewItem.findViewById(R.id.reviewButton);

        reviewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = context.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.review_event, null);
                dialogBuilder.setView(dialogView);

                final Button buttonConfirm = (Button) dialogView.findViewById(R.id.confirmButtonReview);
                final Button buttonCancel = (Button) dialogView.findViewById(R.id.cancelButtonReview);

                EditText descrReviewText = dialogView.findViewById(R.id.descriptionOfReview);
                EditText ratingText = dialogView.findViewById(R.id.ratingOfReview);


                final AlertDialog b = dialogBuilder.create();
                b.show();

                buttonConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String descr = descrReviewText.getText().toString();
                        String rating = ratingText.getText().toString();

                        boolean valid = true;

                        if(descr.isEmpty()){
                            descrReviewText.setError("Description is required");
                            descrReviewText.requestFocus();
                            valid = false;
                        }

                        if(rating.equals("1") || rating.equals("2") || rating.equals("3") || rating.equals("4") || rating.equals("5")){

                            valid = true;
                        }
                        else{
                            ratingText.setError("You must enter a number between 1 and 5");
                            ratingText.requestFocus();
                            valid = false;
                        }
                        if(!valid){
                            return;
                        }

                        if(valid) {
                            database.getReference("users/" + username + "/Reviews/" + event + "/rating").setValue(rating);
                            database.getReference("users/" + username + "/Reviews/" + event + "/Description").setValue(descr);
                        }

                        //database.getReference("users/"+username+"/enrolledEvents/" +position+"/"+event+"/rating" ).setValue(rating);
                        b.dismiss();
                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        b.dismiss();
                    }
                });

            }
        });


        return listViewItem;
    }
}