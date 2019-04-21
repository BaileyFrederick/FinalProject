package com.example.finalproject;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public class FirebaseHandler{
// ...
public FirebaseDatabase mDatabase;
public List<EventHealth> eventHealthList = new ArrayList<EventHealth>();
public TextView t;

    public FirebaseHandler(){
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference("Test");
        myRef.child("Bailey").setValue("Hello, Taylor");


        // Read from the database
        /*
        myRef.child("Test").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("MYTAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MYTAG", "Failed to read value.", error.toException());
            }
        });
        */
    }

    public void addEvent(Event e){
        DatabaseReference myRef = mDatabase.getReference("Calendar");

        myRef.child(e.d.toString()).setValue(e);
    }


    public void addHealthEvent(EventHealth e){
        Log.v("MY_TAG", "IN ADD HEALTH EVENT");
        DatabaseReference myRef = mDatabase.getReference("Health");
        Log.v("MY_TAG", "E.formattedDate= "+e.formattedDate);
        myRef.child(e.formattedDate).setValue(e);
    }

    public void getHealthEvent(String formattedDate){

        final ArrayList<EventHealth> result = new ArrayList<EventHealth>();
        final DatabaseReference myRef = mDatabase.getReference("Health");

        Log.v("MY_TAG", "RESULT SIZE AT BEGINNING "+ eventHealthList.size());

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
        Query healthQuery = myRef.orderByChild(order);

        healthQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    //Log.v("MY_TAG", "IN DATA SNAPSHOT FORLOOP");
                    //Log.v("MY_TAG", "DATA SNAPSHOT2 COUNT= "+noteDataSnapshot.getChildrenCount());
                    //for(DataSnapshot noteDataSnapshot2 : noteDataSnapshot.getChildren()){
                    // Log.v("MY_TAG", "IN DATA SNAPSHOT2 FORLOOP");
                    // Log.v("MY_TAG", "NOTEDATASNAPSHOT2 VAL= " +noteDataSnapshot2.getValue().toString());
                    EventHealth e = noteDataSnapshot.getValue(EventHealth.class);
                    //Log.v("MY_TAG", dashedFormatDay);
                    if(e.formattedDate!=null && e.formattedDate.equals(dashedFormatDay)) {
                        Log.v("MY_TAG", "E DATE=" + e.getFormattedDate());
                        Log.v("MY_TAG", "E MILES= " + e.getMiles());
                        Log.v("MY_TAG", "E STEPS= " + e.getSteps());
                        eventHealthList.add(e);



                        Log.v("MY_TAG", "EVENTHEALTHLIST SIZE "+ eventHealthList.size());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MY_TAG", "onCancelled", databaseError.toException());
            }
        });


    }

}

