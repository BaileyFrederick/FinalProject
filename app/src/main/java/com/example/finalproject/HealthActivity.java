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

import org.w3c.dom.Text;

public class HealthActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sm;
    Sensor s;
    Sensor l;
    TextView step;
    TextView miles;
    TextView calories;
    TextView height;
    TextView weight;
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

        miles = (TextView) findViewById(R.id.miles);
        calories = (TextView) findViewById(R.id.calories);
        height = (TextView) findViewById(R.id.height);
        weight = (TextView) findViewById(R.id.weight);

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

        double totalMiles = steps*0.00047348484848485;
        double totalMilesRounded = Math.round(totalMiles*100.0)/100.0;
        String totalMilesString = Double.toString(totalMilesRounded);
        miles.setText(totalMilesString);

        double totalCals = steps*0.05;
        String totalCalsString = Double.toString(totalCals);
        calories.setText(totalCalsString);
    }
}
