package iitg.cestrum.firestore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by vikas on 02-02-2018.
 */

public class NotificationBuilder {

    public String ID;
    public String subject,postedBy,timeStamp,message;

    NotificationBuilder(String id, String sub, String post, String timeStamp, String message){
        this.ID = id;
        this.subject = sub;
        this.postedBy = post;
        this.timeStamp = timeStamp;
        this.message = message;
    }

    NotificationBuilder() {
    }

    NotificationBuilder(JSONObject o) throws JSONException {
        this.ID = o.getString("ID");
        this.subject = o.getString("subject");
        this.timeStamp = o.getString("timeStamp");
        this.postedBy = o.getString("postedBy");
        this.message = o.getString("message");
    }

    NotificationBuilder(Cursor c){
        this.ID = c.getString(0);
        this.subject = c.getString(1);
        this.timeStamp = c.getString(2);
        this.postedBy = c.getString(3);
        this.message = c.getString(4);
    }
    public void setData(Cursor c){
        this.ID = c.getString(0);
        this.subject = c.getString(1);
        this.timeStamp = c.getString(2);
        this.postedBy = c.getString(3);
        this.message = c.getString(4);
    }
}
