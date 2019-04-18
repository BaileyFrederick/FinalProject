package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper
{

    // All Static variables
// Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DB";

    //  table name
    private static final String TABLE_HEALTH = "Health";

    //  Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_STEPS = "steps";
    private static final String KEY_MILES = "miles";
    private static final String KEY_CALORIES = "calories";
    //private static final String KEY_HEIGHT = "height";
    //private static final String KEY_WEIGHT = "weight";

    private SQLiteDatabase db;

    private int currID=0;


   public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v("MY_TAG", "IN DBHANDLER CONSTRUCTOR");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.v("MY_TAG", "IN ON CREATE");

        String CREATE_HEALTH_TABLE = "CREATE TABLE " + TABLE_HEALTH + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " + KEY_DATE + " TEXT, " + KEY_STEPS
                + " TEXT, " + KEY_MILES + " TEXT, " + KEY_CALORIES + " TEXT);";
        db.execSQL(CREATE_HEALTH_TABLE);

        Date d = new Date();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        /*ContentValues values = new ContentValues();
        values.put(KEY_DATE, formattedDate);
        values.put(KEY_STEPS, "0");
        values.put(KEY_MILES, "0");
        values.put(KEY_CALORIES, "0");
        values.put(KEY_HEIGHT, "0");
        values.put(KEY_WEIGHT, "0");

        db.insert(TABLE_HEALTH, null, values);
        Log.v("MY_TAG", "INSERTED 0 VALUES");
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH);

        // Create tables again
        onCreate(db);
    }

    // Adding new health info like mileage and steps to save for later
    //date is used bc info resets at beginning of each day
    public void addHealthInfo(HealthActivity h) {
        Date d = new Date();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);


        ContentValues values = new ContentValues();
        values.put(KEY_ID, currID);
        values.put(KEY_DATE, formattedDate);
        values.put(KEY_STEPS, h.step.getText().toString());
        values.put(KEY_MILES, h.miles.getText().toString());
        values.put(KEY_CALORIES, h.calories.getText().toString());
        //values.put(KEY_HEIGHT, h.height.getText().toString());
        //values.put(KEY_WEIGHT, h.weight.getText().toString());

        SQLiteDatabase db = this.getWritableDatabase();


        // Inserting Row
        if(TABLE_HEALTH !=null) {
            Log.v("MY_TAG", "TABLE_HEALTH IS NOT NULL");
            db.insert(TABLE_HEALTH, null, values);
        }

        String selectQuery = "SELECT * FROM " + TABLE_HEALTH;
        Cursor curs = db.rawQuery(selectQuery, null);
        int cursorCount = curs.getCount();
        Log.v("MY_TAG", "COUNT OF CURS="+ String.valueOf(cursorCount));

        currID++;

        db.close(); // Closing database connection

        Log.v("MY_TAG", "HEALTH INFO ADDED");
    }

    // Getting single health info based on unique id
    //probably not really gonna be used
    public String getHealth(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HEALTH, new String[] { KEY_ID,
                        KEY_DATE, KEY_STEPS, KEY_MILES, KEY_CALORIES, }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String result = cursor.getString(0) + " " +
                cursor.getString(1) + " " + cursor.getString(2) + " "
                + cursor.getString(3) + " " + cursor.getString(4) +
                cursor.getString(5) + cursor.getString(6);
        // return contact
        return result;
    }

    //get everything that has been entered in database
    public List<String[]> getAllHealthInputs() {
        List<String[]> healthList = new ArrayList<String[]>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HEALTH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.v("MY_TAG", "CURSOR SIZE =" + cursor.getCount());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String[] s = new String[7];
                s[0]=cursor.getString(0);
                //s.append(" ");
                s[1]=cursor.getString(1);
                //s.append(" ");
                s[2]=cursor.getString(2);
                //s.append(" ");
                s[3]=cursor.getString(3);
                //s.append(" ");
                s[4]=cursor.getString(4);
                //s.append(" ");
                //s[5]=(cursor.getString(5));
                //s.append(" ");
                //s[6]=cursor.getString(6);
                //s.append(" ");

                // Adding contact to list
                healthList.add(s);
            } while (cursor.moveToNext());
        }

        // return contact list
        return healthList;
    }

    /*public List<String> maxHealthByDay(){

        Log.v("MY_TAG", "IN MAX HEALTH INFO");

        List<String> healthList = new ArrayList<>();

        Date d = new Date();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        Log.v("MY_TAG", "DATE =" + formattedDate);

        // Select All Query
        String selectQuery = "SELECT  * FROM  WHERE ? = ? ORDER BY T.KEY_STEPS DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM  HEALTH_TABLE WHERE ? = ? ORDER BY T.KEY_STEPS DESC LIMIT 1", new String[]{KEY_DATE, formattedDate});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                healthList.add(cursor.getString(0));
                healthList.add(cursor.getString(1));
                healthList.add(cursor.getString(2));
                Log.v("MY_TAG", "MAX STEPS=" + cursor.getString(2));
                healthList.add(cursor.getString(3));
                healthList.add(cursor.getString(4));
                healthList.add(cursor.getString(5));
                healthList.add(cursor.getString(6));

            } while (cursor.moveToNext());
        }

        // return contact list
        return healthList;
    }
    */

}
