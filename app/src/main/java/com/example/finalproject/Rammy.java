package com.example.finalproject;

import android.util.Log;
import android.widget.Switch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Rammy {

    /*
    Add event to calander-What day and time?-Month day @ time
    Directions to Location
    What is my next appointment
    How many steps have I walked
    How many calories have I burned


     */

    public Rammy(){

    }

    public String read(String input){
        input.toLowerCase().trim();
        String[] sentence = input.split(" ");
        ArrayList<String> words = new ArrayList<String>(sentence.length);
        for(int i = 0; i < sentence.length;i++){
            words.add(sentence[i]);
        }
        if(words.get(0).equals("rammy")){
            words.remove(0);
        }
        for(int i = 0; i < words.size()-1; i++){
            switch (words.get(i)){
                case "add":

                    return "What time and date?";
                case "Directions":

                    return null;
                case "next":

                    return "Test";
                case "steps":

                    return "Steps";
                case "calories":

                    return "Calories";
            }
        }
        return null;
    }

    public boolean issueCommand(String command){
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        if(command.trim().startsWith("date")){
            Date date = new Date();
            int time = 0;
            command.substring(5).trim();
            String[] words = command.split(" ");
            for(int i = 0; i < words.length;i++){
                try
                {
                   time = Integer.parseInt(words[0]);
                }
                catch (NumberFormatException nfe)
                {
                    try{
                        date = df.parse(command);
                    }catch (Exception e){
                        Log.v("MYTAG","" + e.getLocalizedMessage());
                    }

                }
            }
            //add event to database
        }
        switch (command){
            case "directions":

                return true;
        }
        return false;
    }

}
