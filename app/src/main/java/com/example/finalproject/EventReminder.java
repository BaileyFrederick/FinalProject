package com.example.finalproject;

public class EventReminder {

    public String location;
    public String reminder;

    public EventReminder(String location, String reminder){
        this.location=location;
        this.reminder=reminder;
    }

    public EventReminder(){

    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location=location;
    }

    public String getReminder(){
        return reminder;
    }

    public void setReminder(String reminder){
        this.reminder=reminder;
    }
}
