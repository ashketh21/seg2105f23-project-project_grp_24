package com.example.grimpeurscyclingclubgcc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClubOwnerEventList extends ArrayAdapter<Event> {
    private Activity context;
    List<Event> events;

    public ClubOwnerEventList(Activity context, List<Event> events) {
        super(context, R.layout.activity_club_owner_event_list, events);
        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_club_owner_event_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.eventName);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.date);

        Event event = events.get(position);
        textViewName.setText(event.getEventName());
        textViewDate.setText(event.getDate());
        return listViewItem;
    }
}
