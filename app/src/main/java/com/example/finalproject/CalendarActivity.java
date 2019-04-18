package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    int width;
    int height;
    Display display;
    boolean open = false;
    LinearLayout layout;
    CalendarView c;
    FrameLayout l;
    LayoutInflater inflater;
    View customView;
    TimePicker timePicker;
    NumberPicker hourNP;
    NumberPicker minNP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        c = (CalendarView) findViewById(R.id.cv);
        l  = findViewById(R.id.calendarFL);
        inflater  = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView  = inflater.inflate(R.layout.activity_event, null);


        layout = findViewById(R.id.calLL);
        display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size. x;
        height = size. y;


        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                //Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                month++;
                try {

                    Date date = format.parse("" + year + "-" + month + "-" + dayOfMonth);
                    Date d = Calendar.getInstance().getTime();
                    String temp1 = ""+d.getMonth()+d.getDate();
                    String temp2 = ""+date.getMonth()+date.getDate();
                    boolean bool = temp1.equals(temp2);
                    if(!date.before(d) || bool){
                        selectDay(date);
                    }else{
                        Toast.makeText(getApplicationContext(),"Cannot select past dates", Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){

                }
            }
        });

    }

    public void selectDay(Date d){
        Intent x = new Intent(this, DateActivity.class);
        x.putExtra("Date", d);
        startActivity(x);
    }


    public void Add(View v){
        //add to db
        //add view

        if(!open) {
            c.setEnabled(false);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(800, 1300);
            //top shows time
            //height duration
            params.setMargins(((width / 2) - 400), 194, 0, 0);
            customView.setLayoutParams(params);
            l.addView(customView);
            open = true;

            timePicker = (TimePicker) findViewById(R.id.tp);
            hourNP = (NumberPicker) findViewById(R.id.hour);
            minNP = (NumberPicker) findViewById(R.id.minute);
            hourNP.setMinValue(0);
            hourNP.setMaxValue(24);
            minNP.setMinValue(0);
            minNP.setMaxValue(59);
            hourNP.setWrapSelectorWheel(true);
            minNP.setWrapSelectorWheel(true);


        }else{
            c.setEnabled(true);
            open = false;
            l.removeView(customView);
        }

    }

}
