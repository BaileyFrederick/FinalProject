package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    FirebaseHandler fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


        fb = new FirebaseHandler();
        final DatabaseReference myRef = fb.mDatabase.getReference("Health");

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
                    EventHealth e = noteDataSnapshot.getValue(EventHealth.class);

                    if(e.formattedDate!=null && e.formattedDate.equals(dashedFormatDay)) {
                        TextView stepsTV = (TextView) findViewById(R.id.step);
                        stepsTV.setText(e.getSteps());
                        TextView milesTV = (TextView) findViewById(R.id.miles);
                        milesTV.setText(e.getMiles());
                        TextView caloriesTV = (TextView) findViewById(R.id.calories);
                        caloriesTV.setText(e.getCalories());
                        TextView heightTV = (TextView) findViewById(R.id.height);
                        heightTV.setText(e.getHeight());
                        TextView weightTV = (TextView) findViewById(R.id.weight);
                        weightTV.setText(e.getWeight());
                        steps=Integer.valueOf(e.getSteps());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MY_TAG", "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            float x = event.values[0];
            //Log.v("MY_TAG", "step result = " + x);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date d = new Date();
        String date = dateFormat.format(d);

        //step.setText("8256");
        //miles.setText("4.43");
        //height.setText("64");
        //weight.setText("125");

        EventHealth e = new EventHealth(calories.getText().toString(),date,miles.getText().toString(), step.getText().toString(), height.getText().toString(), weight.getText().toString());
        //Log.v("MY_TAG", "DATE="+date);
        fb.addHealthEvent(e);
        Intent x = new Intent(this, MainActivity.class);
        startActivity(x);
    }


    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        Log.v("MY_TAG", "IN ON SAVE INSTANCE STATE");
        outState.putString("step", step.getText().toString());
        outState.putString("miles", miles.getText().toString());
        outState.putString("calories", calories.getText().toString());
        outState.putString("height", height.getText().toString());
        outState.putString("weight", weight.getText().toString());

        //Log.v("MY_TAG", outState.getString("step"));
        // call superclass to save any view hierarchy
    }


}
