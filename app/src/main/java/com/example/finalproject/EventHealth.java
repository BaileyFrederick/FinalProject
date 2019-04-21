package com.example.finalproject;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventHealth {
    public String calories;
    public String formattedDate;
    public String miles;
    public String steps;
    public String height;
    public String weight;


    public EventHealth(String calories, String formattedDate, String miles, String steps, String height, String weight) {

        this.calories=calories;
        this.formattedDate=formattedDate;
        this.miles=miles;
        this.steps=steps;
        this.height=height;
        this.weight=weight;
    }

    public EventHealth(){

    }

    public String getCalories(){
        //Log.v("MY_TAG", "IN GET CALORIES");
        return calories;
    }

    public void setCalories(String calories){
        //Log.v("MY_TAG", "IN SET CALORIES");
        this.calories=calories;
    }

    public String getFormattedDate(){
        //Log.v("MY_TAG", "IN GET FORMATTEDDATE");
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate){
        //Log.v("MY_TAG", "IN SET FORMATTEDDATE");
        this.formattedDate=formattedDate;
    }

    public String getMiles(){
        //Log.v("MY_TAG", "IN GET MILES");
        return miles;
    }

    public void setMiles(String miles){
        //Log.v("MY_TAG", "IN SET MILES");
        this.miles=miles;
    }


    public String getSteps(){
        //Log.v("MY_TAG", "IN GET STEPS");
        return steps;
    }
    public void setSteps(String steps){
       // Log.v("MY_TAG", "IN SET STEPS");
        this.steps=steps;
    }

    public String getHeight(){
        return height;
    }
    public void setHeight(String height){
        this.height=height;
    }

    public String getWeight(){
        return weight;
    }
    public void setWeight(String weight){
        this.weight=weight;
    }

    @Override
    public String toString() {
        return "Event Health: date= " + formattedDate + " steps = " + steps
                + " miles = " + miles + " calories = " + calories + "height = " + height + "weight = " + weight;
    }



}
