package com.team3.getjob;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

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
    Button apply;

   //Setup
    private static final String ARG_PARAM1 = "PostId";
    private static final String ARG_PARAM2 = "UserId";
    private String mParam1;//PostId after OnCREATE
    private String mParam3;//userId
    private job_model mParam2;


    public jobs_pull_fragment() {
        // Required empty public constructor
    }


    public static jobs_pull_fragment newInstance(String param1, String param2) {
        jobs_pull_fragment fragment = new jobs_pull_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam3 = getArguments().getString(ARG_PARAM2);
        }

        //Add post id to user array jobs


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //SETUP
        View view = inflater.inflate(R.layout.fragment_jobs_pull_fragment, container, false);
        view = view.getRootView();

        TextView title =(TextView)view.findViewById(R.id.title_full);
        TextView description =(TextView)view.findViewById(R.id.description_Full);
        TextView location =(TextView)view.findViewById(R.id.location_Full);
        TextView payment =(TextView)view.findViewById(R.id.payment_Full);

        //DATABASE REF
        db = FirebaseFirestore.getInstance();

        //Fill data (DATABASE CALL)
        FillPostData(title, description, location, payment);


        @SuppressLint("WrongViewCast") Button nav = (Button)view.findViewById(R.id.nav);
        View finalView = view;
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) finalView.findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.LEFT);
            }
        });


        //Apply add post id to user id
        Button apply = (Button)view.findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CODE

                //Add post id to user jobs
                db.collection("Users").whereEqualTo("Uid", mParam3)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String doc_id;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("TAG", document.getId());

                                    //Add post id to user jobs
                                    doc_id = document.getId();
                                    DocumentReference userRef = db.collection("Users").document(doc_id);

                                    userRef.update("Jobs", FieldValue.arrayUnion(mParam1));

                                    //Add userUid to post
                                    DocumentReference docRef = db.collection("Posts").document(mParam1);
                                    docRef.update("Users", FieldValue.arrayUnion(mParam3));
                                }

                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());

                            }
                        });



                //Back to filter
                Intent intent = new Intent(getActivity(), Jobs_Pull.class);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return view;

    }

    private void FillPostData(TextView title, TextView description, TextView location, TextView payment) {
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
    }

    //DataPull andUpdate

}