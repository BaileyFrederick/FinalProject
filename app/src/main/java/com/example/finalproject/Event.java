package com.example.finalproject;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Event {

    public String d;
    public String desc;
    public String loc;
    public String t;
    public List<String> days;

    public Event(){

    }

    public Event(String d, String des, String loc, String t, List<String> days){
        this.d = d;
        this.desc = des;
        this.loc = loc;
        this.t = t;
        this.days = days;
    }
}
