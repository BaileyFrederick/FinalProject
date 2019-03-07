package com.example.finalproject;

import android.content.Intent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void map(View v){
        Intent x = new Intent(this, MapActivity.class);
        startActivity(x);
        //test
    }
    public void heart(View v){
        Intent x = new Intent(this, HealthActivity.class);
        startActivity(x);
    }

    public void foo(View v){
        Intent x = new Intent(this, MainActivity2.class);
        startActivity(x);
    }
}
