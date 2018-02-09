package iitg.cestrum.firestore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface
 * to handle interaction events.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {
    private static final String TAG = "FireStore";
    private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
    private String userName;
    private FirebaseFirestore firestore;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String userName) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString("User Name", userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("User Name");
        }
        firestore = FirebaseFirestore.getInstance();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View main =  inflater.inflate(R.layout.fragment_notifications, container, false);
        final Button btn = (Button) main.findViewById(R.id.sendBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn.setEnabled(false);
                final EditText msg = (EditText) main.findViewById(R.id.message);
                final EditText sub = (EditText) main.findViewById(R.id.subject);
                msg.setEnabled(false);
                sub.setEnabled(false);
                String message = msg.getText().toString();
                String subject = sub.getText().toString();
                String time = dt.format(Calendar.getInstance().getTime());

                // TODO: Check if the network is reachable, else skip.




                NotificationHolder nb = new NotificationHolder(subject,message,userName,time);
                firestore.collection("ece_sem_5").document("notifications").collection("data").add(nb).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Written successfully ID = " + documentReference.getId());
                        Snackbar.make(main, "Notification successfully delivered.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        sub.setText("");
                        msg.setText("");
                        btn.setEnabled(true);
                        msg.setEnabled(true);
                        sub.setEnabled(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Snackbar.make(main, "Error! Notification not delivered. Try Again", Snackbar.LENGTH_LONG)
                                .setAction("Act", null).show();
                        btn.setEnabled(true);
                        msg.setEnabled(true);
                        sub.setEnabled(true);
                    }
                });

            }
        });
        return main;
    }



   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {

        super.onDetach();

    }





}
