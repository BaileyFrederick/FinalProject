package com.example.finalproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
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

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private TextView txtSpeechInput;
    private ImageView btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private DrawerLayout drawerLayout;

    private DatabaseReference mDatabase;
  
    private boolean heartOpened = false;

    private Intent healthIntent;

    private HealthActivity h;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        //mDatabase.child("message").setValue("Te");

        // Write a message to the database
        new FirebaseHandler();



        txtSpeechInput = (TextView) findViewById(R.id.speech);
        btnSpeak = (ImageView) findViewById(R.id.microphone);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

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
                    String output = r.read(result.get(0).toString());
                    if(output != null) {
                        Toast.makeText(this,"" + output, Toast.LENGTH_SHORT);
                        promptSpeechInput();
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
}
