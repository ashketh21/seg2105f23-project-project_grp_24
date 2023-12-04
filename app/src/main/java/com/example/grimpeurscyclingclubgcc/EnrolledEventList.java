package com.example.grimpeurscyclingclubgcc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EnrolledEventList extends ArrayAdapter<String> {
    private Activity context;
    List<String> events;

    public EnrolledEventList(Activity context, List<String> events) {
        super(context, R.layout.activity_enrolled_event_list, events);
        this.context = context;
        this.events = events;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_enrolled_event_list, null, true);

        TextView textView = (TextView) listViewItem.findViewById(R.id.eventText);


        String event = events.get(position);
        textView.setText(event);
        return listViewItem;
    }
}