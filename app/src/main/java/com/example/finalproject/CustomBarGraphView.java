package com.example.finalproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.time.LocalDateTime;
import java.util.Date;

public class CustomBarGraphView extends View {

    double[] milesEachHour = new double[24];


    public CustomBarGraphView(Context context) {
        super(context);
    }

    public CustomBarGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomBarGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomBarGraphView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas){

        super.onDraw(canvas);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        Paint p = new Paint();
        p.setColor(Color.BLACK);

        //rgb values for bar color
        Paint barColor = new Paint();
        barColor.setColor(Color.rgb(123,175,212));
        barColor.setStrokeWidth(8);

        float scaleX = (float) (width / 12);
        float scaleY = (float) (height / 20);

        for (int i = 0; i < 9; i++) {
            canvas.drawLine((float) (i * scaleX), (float) (0), (float) (i * scaleX), (float) (600.0), p);
        }

        for (int i = 0; i < 9; i++) {
            canvas.drawLine((float) (0.0), (float) (i * scaleY), (float) (600.0), (float) (i * scaleY), p);
    }

        float scaleBars = (float) (width/36);

        for(int i=0; i<24; i++){
            //canvas.drawLine((float) (i * scaleBars), (float) (0), (float) (i * scaleBars), (float) (10*milesEachHour[i]), barColor);
            if(milesEachHour[i]>0.0) {
                canvas.drawLine((float) (i * scaleBars), (float) (30 * milesEachHour[i]), (float) (i * scaleBars), (float) 600.0, barColor);
            }
        }

        invalidate();

    }

    public void addPoint(double point){
       Date d = new Date();
       int currHour = d.getHours();
       milesEachHour[currHour] = point;

    }

    public double maxPoint(){

        /*milesEachHour[4]=16.0;
        milesEachHour[8] = 8.0;
        milesEachHour[12] = 14.0;
        */

        double result = milesEachHour[0];
        for(int i=1; i<milesEachHour.length; i++){
            if(result<milesEachHour[i]){
                result=milesEachHour[i];
            }
        }
        return result;
    }

}
