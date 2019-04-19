package com.example.finalproject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirebaseHandler{
// ...
private FirebaseDatabase mDatabase;

    public FirebaseHandler(){
        mDatabase = FirebaseDatabase.getInstance();

        // Read from the database
    }

    public void addEvent(Event e){
        DatabaseReference myRef = mDatabase.getReference("Calendar");

        myRef.child(e.d.toString()).child(e.time).setValue(e);
    }

    public List<Event> getEvents(String date){
         final DatabaseReference myRef = mDatabase.getReference("Calendar");
        final List<Event> list = new ArrayList<Event>();
        myRef.child("Calendar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Event e = noteDataSnapshot.getValue(Event.class);
                    list.add(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return list;
    }

}

