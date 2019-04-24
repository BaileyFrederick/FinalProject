package com.example.finalproject;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Event {

    public String d;
    public String desc;
    public String loc;
    public int duration;
    public String time;

    public Event(){

    }

    public Event(String d, String des, String loc, int duration, String t){
        this.d = d;
        this.desc = des;
        this.loc = loc;
        this.duration = duration;
        this.time = t;
    }
}
