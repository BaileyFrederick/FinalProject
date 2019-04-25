package com.example.finalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DirectionsActivity extends AppCompatActivity {

    FirebaseHandler fb;
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_directions);

        fb = new FirebaseHandler();
        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = fb.mDatabase.getReference("Directions");
        EventDirections ed = new EventDirections("sitterson", "eddie smith field house", "Go down East Cameron Ave , Turn Right on Raleigh St , Turn Left on South Road , Track will be on the right");
        fb.addDirections(ed);

        EventDirections ed2 = new EventDirections("murphey", "sitterson", "Walk outside and straight toward quad , Continue past Carroll Hall , Walk straight between Phillips and Chapman Hall, Sitterson will be in front of you");
        fb.addDirections(ed2);

        EventDirections ed3 = new EventDirections("home", "murphey", "Walk south on MLK for 1 mile , Turn left on Cameron Ave , Turn right on Raleigh St, Turn right on South Rd, Turn right toward the quad and Murphey is on the right");
        fb.addDirections(ed3);


        Date d = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("MMMM dd");
        //String day = formater.format(d);
        SimpleDateFormat dbformater = new SimpleDateFormat("yyyy/MM/dd");
        String date = dbformater.format(d);
        SimpleDateFormat formatterHr = new SimpleDateFormat("HH");
        String currHour = formatterHr.format(d);
        final Integer currHourInt = Integer.valueOf(currHour);


        final TextView currDirections = (TextView) findViewById(R.id.directions);
        final TextView toDirections = (TextView) findViewById(R.id.toDirections);
        final TextView fromDirections = (TextView) findViewById(R.id.fromDirections);
        TextView nextLocation = (TextView) findViewById(R.id.nexteventisat);
        final DatabaseReference myRef2 = mDatabase.getReference("Calendar");
        final List<Event> list = new ArrayList<>();
        myRef2.child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    final Event e = noteDataSnapshot.getValue(Event.class);
                    String timeofevent=e.time;
                    int colon = timeofevent.indexOf(':');
                    String timeofeventHour = timeofevent.substring(0,colon);
                    int timeofeventHourInt = Integer.valueOf(timeofeventHour);
                    int diff = currHourInt-timeofeventHourInt;
                    Log.v("MY_TAG", "diff = " + diff);
                    if(diff<0){
                        //nextEventIsAt.setText(e.desc);
                        //locationofnextevent.setText(" at "+ e.loc + " at " + e.time);
                        final DatabaseReference myRef = fb.mDatabase.getReference("Directions");
                        Query healthQuery = myRef.orderByChild(e.loc);

                        healthQuery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                    EventDirections er = noteDataSnapshot.getValue(EventDirections.class);
                                    Log.v("MY_TAG", er.toString());
                                    if(e.loc.equals(er.getToLoc())) {
                                        currDirections.setText(er.getDirections());
                                        toDirections.setText("To: " + er.getToLoc());
                                        fromDirections.setText(" From: " + er.getFromLoc());
                                        break;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e("MY_TAG", "onCancelled", databaseError.toException());
                            }
                        });
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
