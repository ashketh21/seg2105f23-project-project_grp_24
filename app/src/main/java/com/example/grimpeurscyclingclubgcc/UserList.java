package com.example.grimpeurscyclingclubgcc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
public class UserList extends ArrayAdapter<User>{
    private Activity context;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://seg-project-c0dfe-default-rtdb.firebaseio.com/");


    List<User> users;

    public UserList(Activity context, List<User> users) {
        super(context, R.layout.event_list, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater inflater = context.getLayoutInflater();
        View listViewUser = inflater.inflate(R.layout.user_list, null, true);

        TextView textViewName = (TextView) listViewUser.findViewById(R.id.userName);
        TextView textViewRole = (TextView) listViewUser.findViewById(R.id.role);
        Button deleteButton = (Button) listViewUser.findViewById(R.id.deleteUserButton);




        User user = users.get(position);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = database.getReference("users/" + user.getUsername());
                usersRef.removeValue();
            }
        });

        textViewName.setText(user.getUsername());
        textViewRole.setText(user.getRole());
        return listViewUser;
    }
}
