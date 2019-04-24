package com.example.finalproject;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class Rammy {

    /*
    Directions to Location
    What is my next appointment
    How many steps have I walked
    How many calories have I burned
     */

    public ArrayList<String> arrayList= new ArrayList<>();
    private FirebaseDatabase mDatabase;
    FirebaseHandler f = new FirebaseHandler();

    public Rammy(){
        mDatabase = FirebaseDatabase.getInstance();
    }

    public String read(String input){
        input.toLowerCase().trim();
        String[] sentence = input.split(" ");
        ArrayList<String> words = new ArrayList<String>(sentence.length);
        for(int i = 0; i < sentence.length;i++){
            words.add(sentence[i]);
        }
        if(words.get(0).equals("rammy")){
            words.remove(0);
        }
        for(int i = 0; i < words.size()-1; i++){
            switch (words.get(i)){
                case "add":
                    MainActivity.temp = input;
                    return "What time and date?";
                case "Directions":

                    return null;
                case "next":
                    final DatabaseReference myRef = mDatabase.getReference("Calendar");
                    final List<Event> list = new ArrayList<Event>();
                    Date d = new Date();
                    SimpleDateFormat dbformater = new SimpleDateFormat("yyyy/MM/dd");
                    String date = dbformater.format(d);
                    final String output = "";
                    myRef.child(date).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                Event e = noteDataSnapshot.getValue(Event.class);
                                String str = "Your next event is at " + e.desc+" at "+e.time;
                                showNext(str);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    return null;
                case "steps":
                    final DatabaseReference mysRef = mDatabase.getReference("Health");

                    Calendar now = Calendar.getInstance();
                    final int year = now.get(Calendar.YEAR);
                    final String yearInString = String.valueOf(year);
                    int month = now.get(Calendar.MONTH)+1;
                    String monthInString = String.valueOf(month);
                    if(month<=9){
                        monthInString="0"+monthInString;
                    }
                    final String monthInStringFinal=monthInString;
                    int day = now.get(Calendar.DAY_OF_MONTH);
                    final String dayInString = String.valueOf(day);
                    final String dashedFormatDay = monthInStringFinal + "-" + dayInString + "-" + yearInString;

                    String order = yearInString + "/" + monthInString + "/" + dayInString;
                    Query healthQuery = mysRef.orderByChild(order);

                    healthQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                EventHealth e = noteDataSnapshot.getValue(EventHealth.class);

                                if(e.formattedDate!=null && e.formattedDate.equals(dashedFormatDay)) {
                                    showNext("You have walked " + e.getSteps() + " steps");
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("MY_TAG", "onCancelled", databaseError.toException());
                        }
                    });

                    return null;
                case "calories":
                    final DatabaseReference mycRef = mDatabase.getReference("Health");

                    Calendar cnow = Calendar.getInstance();
                    final int cyear = cnow.get(Calendar.YEAR);
                    final String cyearInString = String.valueOf(cyear);
                    int cmonth = cnow.get(Calendar.MONTH)+1;
                    String cmonthInString = String.valueOf(cmonth);
                    if(cmonth<=9){
                        cmonthInString="0"+cmonthInString;
                    }
                    final String cmonthInStringFinal=cmonthInString;
                    int cday = cnow.get(Calendar.DAY_OF_MONTH);
                    final String cdayInString = String.valueOf(cday);
                    final String cdashedFormatDay = cmonthInStringFinal + "-" + cdayInString + "-" + cyearInString;

                    String corder = cyearInString + "/" + cmonthInString + "/" + cdayInString;
                    Query chealthQuery = mycRef.orderByChild(corder);

                    chealthQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                EventHealth e = noteDataSnapshot.getValue(EventHealth.class);

                                if(e.formattedDate!=null && e.formattedDate.equals(cdashedFormatDay)) {
                                    showNext("You have burned " + e.getCalories() + " calories");
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("MY_TAG", "onCancelled", databaseError.toException());
                        }
                    });
                    return null;
            }
        }
        return input;
    }

    public void showNext(String s){
        MainActivity.txtSpeechInput.setText(s);
    }

    public void addEvent(String Command, String temp) {
        String s = temp.toLowerCase().replace("add", "").replace("to my calendar", "");
        String time = "";
        s.trim();
        String d = Command;
        d = d.replace("at ", "");
        d = d.replace("p.m", "");
        d = d.replace("a.m", "");
        d = d.replace("th", "");
        d = d.replace("rd", "");
        d = d.replace("st", "");
        d = d.replace("nd", "");
        int a = d.lastIndexOf(" ");
        time = d.substring(a,d.length());
        d = d.substring(0,a);
        d = d.trim();
        time = time.trim();
        d = "2019 "+d;
        d = d.replace(" ","/");
        d = d.toLowerCase();

        d = d.replace("january","01");
        d = d.replace("febuary","02");
        d = d.replace("march","03");
        d = d.replace("april","04");
        d = d.replace("may","05");
        d = d.replace("june","06");
        d = d.replace("july","07");
        d = d.replace("august","08");
        d = d.replace("september","09");
        d = d.replace("october","10");
        d = d.replace("november","11");
        d = d.replace("december","12");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date dateA = null;
        try {
            dateA = formatter.parse(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String date = dateFormat.format(dateA);
        DateFormat timeFormat = new SimpleDateFormat("hh:mm");
        if (time.contains(":")) {
            if (time.length() == 4) {
                time = "0" + time;
            }
        } else {
            if (time.length() == 1) {
                time = "0" + time + ":00";
            } else if (time.length() == 2) {
                time = time + ":00";
            }
        }
        date = date;
        Event e = new Event(date, s, "", 60, time);
        f.addEvent(e);
    }

    public boolean issueCommand(String command){
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        if(command.trim().startsWith("date")){
            Date date = new Date();
            int time = 0;
            command.substring(5).trim();
            String[] words = command.split(" ");
            for(int i = 0; i < words.length;i++){
                try
                {
                   time = Integer.parseInt(words[0]);
                }
                catch (NumberFormatException nfe)
                {
                    try{
                        date = df.parse(command);
                    }catch (Exception e){
                        Log.v("MYTAG","" + e.getLocalizedMessage());
                    }

                }
            }
            //add event to database
            int i = 0;
        }
        switch (command){
            case "directions":

                return true;
        }
        return false;
    }

}
