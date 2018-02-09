package iitg.cestrum.firestore;

import static java.lang.Integer.parseInt;

/**
 * Created by vikas on 25-12-2017.
 */

public class FireStoreEventHolder {

    public String eventName;
    public String eventDate;
    public String eventTime;
    public String eventDuration;
    public String eventVenue;
    public String courseName;
    public String prof;
    public String credits;


    public FireStoreEventHolder(String nam, String date, String time, int duration, String venue, String courName, String prof, String credit) {

        this.eventName = nam;
        this.prof = prof;
        this.credits = credit;
        this.courseName = courName;
        this.eventVenue = venue;
        this.eventDuration =  Integer.toString(duration);
        this.eventDate = date;                                                      // new SimpleDateFormat("yyyy-MM-dd").parse(date);
        this.eventTime = time;                                                      //new Time(new SimpleDateFormat("HH:mm:ss").parse(time).getTime());

    }

    public EventBuilder build(String id){
        return new EventBuilder(id,this.eventName,this.eventDate,this.eventTime,parseInt(this.eventDuration),this.eventVenue,this.courseName,this.prof,this.credits);
    }

    public FireStoreEventHolder() {

    }



}


