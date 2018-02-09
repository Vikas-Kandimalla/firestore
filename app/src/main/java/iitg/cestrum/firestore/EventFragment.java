package iitg.cestrum.firestore;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    public final static String TAG = "EVENT_FRAGMENT";

    private EditText dateView;
    private EditText timeStartView;
    private EditText timeEndView;
    private String dateString;
    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(final View view,Bundle savedInstance){

        dateView = (EditText) view.findViewById(R.id.event_date);
        timeStartView = (EditText) view.findViewById(R.id.event_time_begin);
        timeEndView = (EditText) view.findViewById(R.id.event_time_end);

        final Calendar date = Calendar.getInstance();
        final DatePickerDialog dateDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG,"Day = " + dayOfMonth + " Month = " + month + " year = " + year);
                Calendar c = Calendar.getInstance();
                c.set(year,month,dayOfMonth);
                String time = (new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)).format(c.getTime());
                dateString = (new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)).format(c.getTime());
                dateView.setText(time);
            }
        },date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DATE));


        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });

        dateView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    dateDialog.show();
                else
                    dateDialog.cancel();
            }
        });

        final TimePickerDialog timeBeginPickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format(Locale.ENGLISH,"%02d:%02d",hourOfDay,minute);
                timeStartView.setText(time);
            }
        },date.get(Calendar.HOUR),date.get(Calendar.MINUTE),true);

        final TimePickerDialog timeEndPickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format(Locale.ENGLISH,"%02d:%02d",hourOfDay,minute);
                timeEndView.setText(time);
            }
        },date.get(Calendar.HOUR),date.get(Calendar.MINUTE),true);

        timeStartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeBeginPickerDialog.show();
            }
        });

        timeStartView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    timeBeginPickerDialog.show();
                else
                    timeBeginPickerDialog.cancel();
            }
        });

        timeEndView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeEndPickerDialog.show();
            }
        });
        timeEndView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    timeEndPickerDialog.show();
                else
                    timeEndPickerDialog.cancel();
            }
        });



        view.findViewById(R.id.event_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view.findViewById(R.id.event_submit_button).setEnabled(false);
                String name = ((EditText) view.findViewById(R.id.event_name)).getText().toString();
                String courseName = ((EditText) view.findViewById(R.id.course_name)).getText().toString();
                String venue = ((EditText) view.findViewById(R.id.event_venue)).getText().toString();
                String credits = ((EditText) view.findViewById(R.id.credits)).getText().toString();
                String prof = ((EditText) view.findViewById(R.id.prof)).getText().toString();
                String time =  ((EditText) view.findViewById(R.id.event_time_begin)).getText().toString();

                String timeend = ((EditText) view.findViewById(R.id.event_time_end)).getText().toString();
                int duration =  getDuration(time,timeend);

                time += ":00";

                FireStoreEventHolder eb = new FireStoreEventHolder(name,dateString,time,duration,venue,courseName,prof,credits);

                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                firestore.collection("ece_sem_5").document("events").collection("data").add(eb).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Written successfully ID = " + documentReference.getId());
                        Snackbar.make(view, "Notification successfully delivered.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        view.findViewById(R.id.event_submit_button).setEnabled(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Snackbar.make(view, "Error! Notification not delivered. Try Again", Snackbar.LENGTH_LONG)
                                .setAction("Act", null).show();
                    }
                });

            }
        });

        Log.d(TAG,"View created");


    }


    public int getDuration(String begin , String end) {
        int ret = 0;
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
        long a = 0;
        long b = 0;
        try {
            a = dt.parse(begin).getTime();
            b = dt.parse(end).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ret = (int) (b - a)/60000;
        return ret;

    }

}
