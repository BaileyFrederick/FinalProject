package com.example.finalproject;

public class EventDirections {

    private String fromLoc;
    private String toLoc;
    private String directions;

    public EventDirections(String fromLoc, String toLoc, String directions){
        this.fromLoc=fromLoc;
        this.toLoc=toLoc;
        this.directions=directions;
    }

    public EventDirections(){

    }

    public String getFromLoc(){
        return fromLoc;
    }

    public void setFromLoc(String fromLoc){
        this.fromLoc=fromLoc;
    }

    public String getToLoc(){
        return toLoc;
    }

    public void setToLoc(String toLoc){
        this.toLoc=toLoc;
    }

    public String getDirections(){
        return directions;
    }

    public void setDirections(String directions){
        this.directions=directions;
    }
}
