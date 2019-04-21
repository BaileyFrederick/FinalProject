package com.example.finalproject;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Event {

    public String d;
    public String desc;
    public String loc;
    public List<String> days;
    public int duration;
    public String time;

    public Event(){

    }

    public Event(String d, String des, String loc, int duration, String t, List<String> days){
        this.d = d;
        this.desc = des;
        this.loc = loc;
        this.days = days;
        this.duration = duration;
        this.time = t;
    }
}
