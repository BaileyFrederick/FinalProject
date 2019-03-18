package com.example.finalproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class HealthActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sm;
    Sensor s;
    Sensor l;
    TextView step;
    int steps = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        step = (TextView) findViewById(R.id.step);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        sm.registerListener(this,s, 500000);
        l = sm.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sm.registerListener(this,l,500000);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            float x = event.values[0];
            Log.v("MY_TAG", "step result = " + x);
            updateSteps();
        }else{
            Log.v("MY_TAG", "HERE " + event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void updateSteps(){
        steps++;
        try{
            step.setText(""+steps);
        }catch (Exception e){
            Log.v("MYTAG", ""+e.getLocalizedMessage());
        }

    }
}
