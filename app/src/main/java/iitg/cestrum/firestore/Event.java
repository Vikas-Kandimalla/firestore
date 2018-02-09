package iitg.cestrum.firestore;

import android.util.Log;

import java.util.Objects;

/**
 * Created by vikas on 26-12-2017.
 */

public class Event {
    public static final int EVENT_BUILDER =1;
    public static final int RECUR_EVENT_BUILDER =2;
    public static final int EXP_RECUR_EVENT_BUILDER =3;
    public static final int EVENT_CACHE_BUILDER = 4;
    public static final int MONTH_DISPLAY = 5;
    public static final int DATE_DISPLAY = 6;
    public static final int WEEK_DISPLAY = 7;

    private int eventType;
    private String date_name;
    private String week_name;
    private String week_id;
    private String week_date;

    public Event(int type){
        this.eventType = type;
    }
    public void setType(int type){
        this.eventType = type;
    }
    public int getType(){
        return this.eventType;
    }

    public Event(String date){
        this.eventType = DATE_DISPLAY;
        this.date_name = date;
    }

    public Event(boolean a, String id , String week){
        this.eventType = WEEK_DISPLAY;
        this.week_name = week;
        this.week_id = id;
    }


    public String getDate_name() {
        return this.date_name;
    }

    public String getWeek_name() {return  this.week_name; }

    public String getWeek_id() {return this.week_id; }

    public void setWeek_date(String date){
        this.week_date = date;
    }

    public String getWeek_date() {return this.week_date; }
}
