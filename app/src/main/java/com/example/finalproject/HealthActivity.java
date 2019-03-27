package com.example.finalproject;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    CustomBarGraphView barGraph;
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
        barGraph = (CustomBarGraphView) findViewById(R.id.bargraph);

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
        double totalCalsRounded = Math.round(totalCals*100.0)/100.0;
        String totalCalsString = Double.toString(totalCalsRounded);
        calories.setText(totalCalsString);

        barGraph.addPoint(totalMilesRounded);

        double maxPoint = barGraph.maxPoint();
        double scale = maxPoint/8;

        TextView ypoint1 = (TextView) findViewById(R.id.ypoint1);
        TextView ypoint2 = (TextView) findViewById(R.id.ypoint2);
        TextView ypoint3 = (TextView) findViewById(R.id.ypoint3);
        TextView ypoint4 = (TextView) findViewById(R.id.ypoint4);
        TextView ypoint5 = (TextView) findViewById(R.id.ypoint5);
        TextView ypoint6 = (TextView) findViewById(R.id.ypoint6);
        TextView ypoint7 = (TextView) findViewById(R.id.ypoint7);
        TextView ypoint8 = (TextView) findViewById(R.id.ypoint8);
        TextView ypoint9 = (TextView) findViewById(R.id.ypoint9);

        ypoint1.setText(String.valueOf(Math.round(maxPoint*100.0)/100.0));
        ypoint2.setText(String.valueOf(Math.round((maxPoint-scale)*100.0)/100.0));
        ypoint3.setText(String.valueOf(Math.round((maxPoint-scale*2)*100.0)/100.0));
        ypoint4.setText(String.valueOf(Math.round((maxPoint-scale*3)*100.0)/100.0));
        ypoint5.setText(String.valueOf(Math.round((maxPoint-scale*4)*100.0)/100.0));
        ypoint6.setText(String.valueOf(Math.round((maxPoint-scale*5)*100.0)/100.0));
        ypoint7.setText(String.valueOf(Math.round((maxPoint-scale*6)*100.0)/100.0));
        ypoint8.setText(String.valueOf(Math.round((maxPoint-scale*7)*100.0)/100.0));
        ypoint9.setText(String.valueOf(Math.round((maxPoint-scale*8)*100.0)/100.0));

    }

    public void goBack(View v){
        Intent x = new Intent(this, MainActivity.class);
        startActivity(x);
    }
}
