package com.example.finalproject;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DateActivity extends AppCompatActivity {
    FrameLayout v;

    Display display;
    int width;
    int height;
    boolean open = false;

    FrameLayout l;
    LayoutInflater inflater;
    View customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        l  = findViewById(R.id.main);
        inflater  = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView  = inflater.inflate(R.layout.activity_event, null);

        //Get the info for the day
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


}
