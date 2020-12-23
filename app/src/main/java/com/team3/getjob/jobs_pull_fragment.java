package com.team3.getjob;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link jobs_pull_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class jobs_pull_fragment extends Fragment {
    FirebaseFirestore db;
    ListView mListView;
    TextView title;
    TextView description;
    TextView location;
    TextView payment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "PostId";
   // private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private job_model mParam2;

    public jobs_pull_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment jobs_pull_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static jobs_pull_fragment newInstance(String param1) {
        jobs_pull_fragment fragment = new jobs_pull_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_jobs_pull_fragment, container, false);
        view = view.getRootView();

        TextView title =(TextView)view.findViewById(R.id.title_full);
        TextView description =(TextView)view.findViewById(R.id.description_Full);
        TextView location =(TextView)view.findViewById(R.id.location_Full);
        TextView payment =(TextView)view.findViewById(R.id.payment_Full);


        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Posts").document(mParam1); //mParam the postId from activity
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("CHECK", "DocumentSnapshot data: " + document.getData());
                        job_model jobs_post = document.toObject(job_model.class);

                        title.setText(jobs_post.getTitle());
                        description.setText(jobs_post.getDescription());
                        location.setText(jobs_post.getLocation());
                        payment.setText(jobs_post.getPayment());

                        Log.d("CHECK","Title nEW" + title.getText());
                    } else {
                        Log.d("CHECK" ,"No such document");
                    }
                } else {
                    Log.d("CHECK", "get failed with ", task.getException());
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
       // return inflater.inflate(R.layout.fragment_jobs_pull_fragment, container, false);


    }
}