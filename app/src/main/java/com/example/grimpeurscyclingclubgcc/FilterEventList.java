package com.example.grimpeurscyclingclubgcc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FilterEventList extends ArrayAdapter<Event> {
    private Activity context;
    List<Event> events;
    private String participantName;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");

    public FilterEventList(Activity context, List<Event> events, String participant) {
        super(context, R.layout.activity_club_owner_event_list, events);
        this.context = context;
        this.events = events;
        this.participantName = participant;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_filter_event_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.eventName);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.date);
        TextView textViewClubOwnerName = (TextView) listViewItem.findViewById(R.id.clubOwnerName);
        Button registerButton = (Button) listViewItem.findViewById(R.id.enrollButton);

        Event event = events.get(position);

        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, EnrollToEventActivity.class);
                intent.putExtra("clubOwnerName", event.getClubOwnerName());
                intent.putExtra("eventType", event.getEventName());
                intent.putExtra("date", event.getDate());
                intent.putExtra("participantName" , participantName);
                context.startActivity(intent);
            }
        });



        textViewName.setText(event.getEventName());
        textViewDate.setText(event.getDate());
        textViewClubOwnerName.setText(event.getClubOwnerName());
        return listViewItem;
    }
}
