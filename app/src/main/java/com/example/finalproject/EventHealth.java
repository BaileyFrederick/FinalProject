package com.example.finalproject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventHealth {
    public String steps;
    public String miles;
    public String calories;
    public String formattedDate;

    public EventHealth(String formattedDate, String steps, String miles, String calories) {

        this.formattedDate=formattedDate;
        this.steps=steps;
        this.miles=miles;
        this.calories=calories;
    }
}
