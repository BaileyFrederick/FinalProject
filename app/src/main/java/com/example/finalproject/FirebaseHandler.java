package com.example.finalproject;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class FirebaseHandler{
// ...
private FirebaseDatabase mDatabase;

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


    public void addHealthEvent(Event e){
        DatabaseReference myRef = mDatabase.getReference("Health");
        myRef.child(e.d.toString()).setValue(e);
    }


}

