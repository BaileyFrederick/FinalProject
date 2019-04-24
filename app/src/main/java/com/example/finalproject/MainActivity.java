package com.example.finalproject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    static TextView txtSpeechInput;
    private ImageView btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private DrawerLayout drawerLayout;
  
    private boolean heartOpened = false;

    private Intent healthIntent;

    private HealthActivity h;
    boolean b = false;
    String activity;
    static String temp;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        //mDatabase.child("message").setValue("Te");

        mDatabase = FirebaseDatabase.getInstance();
        // Write a message to the database
        new FirebaseHandler();

        final DatabaseReference myRef = mDatabase.getReference("Calendar");
        final List<Event> list = new ArrayList<Event>();
        Date d = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
        String date = ft.format(d);
        myRef.child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Event e = noteDataSnapshot.getValue(Event.class);
                    String h = e.time.substring(0,e.time.indexOf(":"));
                    String m = e.time.substring(e.time.indexOf(":")+1,e.time.length());
                    int intH = Integer.parseInt(h);
                    int intM = Integer.parseInt(m);
                    Date d = new Date();
                    SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
                    String curS = sf.format(d);
                    String curh = curS.substring(0,curS.indexOf(":"));
                    String curm = curS.substring(curS.indexOf(":")+1,curS.length());
                    int intcurH = Integer.parseInt(curh);
                    int intcurM = Integer.parseInt(curm);
                    if(intcurH<=intH && intcurM<intM) {
                        int a = intH - intcurH;
                        a = a * 60;
                        int b = intM - intcurM;
                        a = a + b;
                        scheduleNotification(getNotification(e), a * 1000*60);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        txtSpeechInput = (TextView) findViewById(R.id.speech);
        btnSpeak = (ImageView) findViewById(R.id.microphone);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

    }

    public void setReminder(Event e){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Test1")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(e.desc)
                .setContentText("Don't forget about " + e.desc + " at " +e.time)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(Event e) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(e.desc);
        builder.setSmallIcon(R.drawable.calendar);
        return builder.build();
    }


    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void promptDateInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, 10);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    Rammy r = new Rammy();
                    String output = r.read(result.get(0));
                    if(b){
                        r.addEvent(output, temp);
                        b = false;

                    }
                    if(output != null) {
                        if(output.equals("What time and date?")){
                            b = true;
                        }
                        if(b) {
                            Toast.makeText(this, "" + output, Toast.LENGTH_SHORT).show();
                            promptSpeechInput();
                        }
                    }
                    /*
                    if(output != null){
                        txtSpeechInput.append("\n" + output);
                        if(output.contains("?")){
                            promptSpeechInput();
                        }
                        String date = r.read(txtSpeechInput.getText().toString());
                        r.issueCommand(date);
                    }
                    */
                }
                break;
            }
            case 10: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.append("\n" + result.get(0));
                    Rammy r = new Rammy();
                    r.issueCommand(result.get(0));

                }

                break;
            }

        }


    }

    public void map(View v){
        //onStop();
        Intent x = new Intent(this, MapActivity.class);
        startActivity(x);
        //te
    }
    public void heart(View v){
        //onPause();
        if(!heartOpened) {
            healthIntent = new Intent(this, HealthActivity.class);
            startActivity(healthIntent);
        }
        else{
            healthIntent = new Intent(this, HealthActivity.class);
        }

    }

    public void foo(View v){
        Intent x = new Intent(this, CalendarActivity.class);
        startActivity(x);
    }
    public void help(View v){
        Intent x = new Intent(this, HelpActivity.class);
        startActivity(x);
    }
}
