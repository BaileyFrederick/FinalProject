package com.example.finalproject;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DateActivity extends AppCompatActivity {
    FrameLayout v;

    Display display;
    int width;
    int height;
    boolean open = false;

    FrameLayout l;
    LayoutInflater inflater;
    View customView;
    FirebaseHandler f = new FirebaseHandler();
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        Bundle bundle = getIntent().getExtras();
        Date d = (Date) bundle.get("Date");
        SimpleDateFormat formater = new SimpleDateFormat("MMMM dd");
        String day = formater.format(d);
        SimpleDateFormat dbformater = new SimpleDateFormat("yyyy/MM/dd");
        String date = dbformater.format(d);
        TextView dayText = (TextView) findViewById(R.id.dayTV);
        dayText.setText(day);

        //setUp(date);

        final DatabaseReference myRef = mDatabase.getReference("Calendar");
        final List<Event> list = new ArrayList<Event>();
        final AtomicBoolean done = new AtomicBoolean(false);
        myRef.child(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Event e = noteDataSnapshot.getValue(Event.class);
                    Log.v("MYTAG",""+e.desc);
                    addEventView(e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        l  = findViewById(R.id.main);
        inflater  = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView  = inflater.inflate(R.layout.activity_event, null);

        //Get the info for the day

    }

    public void Add(View v){
        //add to db
        //add view

        if(!open) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(800, 1200);
            //top shows time
            //height duration
            params.setMargins(((width / 2) - 400), 194, 0, 0);
            customView.setLayoutParams(params);
            l.addView(customView);
            open = true;
        }else{
            l.removeView(customView);
            open = false;
        }

    }

    public void setUp(String d){
        List<Event> e = f.getEvents(d);
        for(int i =0; i < e.size(); i++){
            addEventView(e.get(i));
        }
    }

    public void addEventView(Event event){
        //add views for each one at specific time
        display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size. x;
        height = size. y;
        v = findViewById(R.id.fl);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.activity_custom, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(750,300);
        //top shows time
        //height duration
        params.setMargins(75, 0, 0, 0);
        customView.setLayoutParams(params);
        v.addView(customView);
    }


    public void addE(View view) {

    }
}
