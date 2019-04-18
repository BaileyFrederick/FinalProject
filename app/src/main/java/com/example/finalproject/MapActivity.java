package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    public void backToMain(View v){
        Intent x = new Intent(this, MainActivity.class);
        startActivity(x);
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate){



        super.onSaveInstanceState(outstate);

    }
}
