package com.example.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.time.LocalDateTime;

public class MapActivity extends AppCompatActivity {

    FrameLayout l;
    LayoutInflater inflater;
    View customView;
    boolean open = false;
    int width;
    int height;
    TextView nextEvent;
    EditText reminder;
    EditText locationReminder;
    FirebaseHandler fb;
    Geocoder gc;
    Intent addressCurrLocation;
    private AddressResultReceiver resultReceiver;
    private FusedLocationProviderClient fusedLocationClient;
    Location lastKnownLocation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.activity_reminder, null);
        l = findViewById(R.id.mapLayout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        nextEvent = (TextView) findViewById(R.id.nexteventisat);

        fb = new FirebaseHandler();
        final DatabaseReference myRef = fb.mDatabase.getReference("Reminders");

    }

    public void backToMain(View v){
        Intent x = new Intent(this, MainActivity.class);
        startActivity(x);
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate){



        super.onSaveInstanceState(outstate);

    }

    public void getCurrentLocation(View v){
        /*fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        lastKnownLocation = location;

                        // In some rare cases the location returned can be null
                        if (lastKnownLocation == null) {
                            return;
                        }

                        if (!Geocoder.isPresent()) {
                            Toast.makeText(MapActivity.this,
                                    "no geocoder available",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                        // Start service and update UI to reflect new location
                        //startIntentService();
                        //updateUI();
                        Log.v("MY_TAG", "latitude = "+lastKnownLocation.getLatitude());
                        Log.v("MY_TAG", "longitude = "+lastKnownLocation.getLongitude());
                    }
                });
        addressCurrLocation = new Intent(this, FetchAddressIntentService.class);
        addressCurrLocation.putExtra(Constants.RECEIVER, resultReceiver);
        addressCurrLocation.putExtra(Constants.LOCATION_DATA_EXTRA, addressCurrLocation);
        startService(addressCurrLocation); */

        Intent x = new Intent(this, MapsActivity_GoogleMaps.class);
        startActivity(x);

    }

    public void addPopup(View v){
        if(!open) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(800, 1300);
            //top shows time
            //height duration
            params.setMargins(((width / 2) - 350), 194, 0, 0);
            customView.setLayoutParams(params);
            nextEvent.setText("");
            l.addView(customView);
            open = true;
        }
        else{
            open=false;
            l.removeView(customView);
            nextEvent.setText("Next Event Is At:");
        }
    }

    public void addReminder(View v){
        locationReminder = (EditText) findViewById(R.id.locationReminder);
        reminder = (EditText) findViewById(R.id.reminder);
        EventReminder er = new EventReminder(locationReminder.getText().toString(), reminder.getText().toString());
        fb.addReminderEvent(er);
        if(open==true){
            open=false;
            l.removeView(customView);
            nextEvent.setText("Next Event Is At:");
        }
    }
}
