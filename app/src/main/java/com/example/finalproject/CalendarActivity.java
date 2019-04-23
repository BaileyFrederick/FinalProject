package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    EditText description;
    EditText location;
    DatePicker datePicker;
    FirebaseHandler f = new FirebaseHandler();
    CheckBox sun;
    CheckBox m;
    CheckBox t;
    CheckBox w;
    CheckBox th;
    CheckBox fr;
    CheckBox s;
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
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(800, 1400);
            //top shows time
            //height duration
            params.setMargins(((width / 2) - 400), 194, 0, 0);
            customView.setLayoutParams(params);
            l.addView(customView);
            open = true;

            timePicker = (TimePicker) findViewById(R.id.tp);
            hourNP = (NumberPicker) findViewById(R.id.hour);
            minNP = (NumberPicker) findViewById(R.id.minute);
            description = (EditText) findViewById(R.id.desET);
            location = (EditText) findViewById(R.id.locET);
            datePicker = (DatePicker) findViewById(R.id.dp);
            hourNP.setMinValue(0);
            hourNP.setMaxValue(24);
            minNP.setMinValue(0);
            minNP.setMaxValue(59);
            hourNP.setWrapSelectorWheel(true);
            minNP.setWrapSelectorWheel(true);
            sun = (CheckBox) findViewById(R.id.checkSun);
            m = (CheckBox) findViewById(R.id.checkM);
            t = (CheckBox) findViewById(R.id.checkT);
            w = (CheckBox) findViewById(R.id.checkW);
            th = (CheckBox) findViewById(R.id.checkTh);
            fr = (CheckBox) findViewById(R.id.checkF);
            s = (CheckBox) findViewById(R.id.checkS);

        }else{
            c.setEnabled(true);
            open = false;
            l.removeView(customView);
        }


    }
    public void addE(View v){
        String des = description.getText().toString();
        String loc = location.getText().toString();
        String tempDate;
        if(datePicker.getMonth() < 10){
            tempDate = "" + datePicker.getYear() + "/0" + (datePicker.getMonth()+1) + "/" + datePicker.getDayOfMonth();
        }else {
            tempDate = "" + datePicker.getYear() + "/" + (datePicker.getMonth()+1) + "/" + datePicker.getDayOfMonth();
        }
        int mins = hourNP.getValue()*60 + minNP.getValue();

        String time;
        if(Build.VERSION.SDK_INT < 23){
            int getHour = timePicker.getCurrentHour();
            int getMinute = timePicker.getCurrentMinute();
            if(getMinute == 0){
                time = getHour+":"+getMinute+"0";
            }else{
                time = getHour+":"+getMinute;
            }

        } else{
            int getHour = timePicker.getHour();
            int getMinute = timePicker.getMinute();
            if(getMinute == 0){
                time = getHour+":"+getMinute+"0";
            }else{
                time = getHour+":"+getMinute;
            }
        }

        List<String> list = new ArrayList<String>();
        if(sun.isChecked()){
            list.add("Sun");
        }
        if(m.isChecked()){
            list.add("M");
        }
        if(t.isChecked()){
            list.add("T");
        }
        if(w.isChecked()){
            list.add("W");
        }
        if(th.isChecked()){
            list.add("Th");
        }
        if(fr.isChecked()){
            list.add("F");
        }
        if(s.isChecked()){
            list.add("S");
        }

        Event e = new Event(tempDate, des, loc, mins,time,list);
        f.addEvent(e);
    }

}
