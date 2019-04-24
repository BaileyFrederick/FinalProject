package com.example.finalproject;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class DateActivity extends AppCompatActivity {
    FrameLayout v;

    Display display;
    int width;
    int height;
    boolean open = false;
    TextView e;
    TextView loc;
    TextView te;
    TextView d;

    FrameLayout l;
    LayoutInflater inflater;
    View customView;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_date);
        Bundle bundle = getIntent().getExtras();
        Date d = (Date) bundle.get("Date");
        SimpleDateFormat formater = new SimpleDateFormat("MMMM dd");
        String day = formater.format(d);
        SimpleDateFormat dbformater = new SimpleDateFormat("yyyy/MM/dd");
        String date = dbformater.format(d);
        TextView dayText = (TextView) findViewById(R.id.dayTV);
        dayText.setText(day);

        final DatabaseReference myRef = mDatabase.getReference("Calendar");
        final List<Event> list = new ArrayList<Event>();
        myRef.child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Event e = noteDataSnapshot.getValue(Event.class);
                    addEventView(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        l  = findViewById(R.id.main);
        inflater  = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView  = inflater.inflate(R.layout.activity_event, null);

        //Get the info for the day

    }

    public void setUp(String d){

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
        double dur = (((double)(event.duration))/60)*190;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(750,(int)dur);
        //top shows time
        //height duration
        String temp = event.time.replace(":","");
        int time = Integer.parseInt(temp);
        double t = ((double)time)/100;
        t = t*190;
        t = t+30;
        if(time>=300 && time<1000){
            t = t+10;
        }else if(time>=1000 && time<1200){
            t = t+20;
        }else if(time>=1200 && time<1700){
            t = t+30;
        }else if(time>=1700 && time<1900){
            t = t+40;
        }else if(time>=1900 && time<2400){
            t = t+50;
        }



        params.setMargins(75, (int)t, 0, 0);
        customView.setLayoutParams(params);
        TextView desc= new TextView(this);
        desc.setText(event.desc);
        desc.setPadding(85, (int)t+10, 0, 0);
        desc.setTypeface(null, Typeface.BOLD);
        desc.measure(0,0);
        TextView timeView = new TextView(this);
        timeView.setText(event.time);
        timeView.setPadding(15+desc.getMeasuredWidth(), (int)t+10, 0, 0);

        TextView loc= new TextView(this);
        loc.setText(event.loc);
        loc.setPadding(85, (int)t+60, 0, 0);
        loc.measure(0,0);
        TextView durView = new TextView(this);
        durView.setText(event.duration+" minutes");
        durView.setPadding(15+loc.getMeasuredWidth(), (int)t+60, 0, 0);

        v.addView(customView);
        v.addView(desc);
        v.addView(timeView);
        v.addView(loc);
        v.addView(durView);



        /*e = (TextView) findViewById(R.id.eventTV);
        loc = (TextView) findViewById(R.id.locTV);
        te = (TextView) findViewById(R.id.timeTV);
        d = (TextView) findViewById(R.id.durTV);
        e.setText(event.desc);
        loc.setText(event.loc);

        if(time<=1200){
            event.time = event.time+"AM";
        }else{
            int temp =time-1200;
            String s = ""+temp;
            if(s.length() == 3){
                s = "0"+s;
            }
            s = s.substring(0,2)+":"+s.substring(2,4)+"PM";
            event.time = s;
        }
        te.setText(event.time);
        d.setText(""+event.duration+" minutes");
        */
    }


    public void addE(View view) {

    }
}
