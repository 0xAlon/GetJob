package com.team3.getjob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Jobs_Pull extends AppCompatActivity {

    FirebaseFirestore db;
    ListView mListView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs__pull);

        mListView = findViewById(R.id.list_view);

        ArrayList<job_model> temp_list = new ArrayList<job_model>();

        for (int i = 0; i < 10; i++)
        {
            temp_list.add(new job_model("Gardener","Lite work in garden","BerSheva" ,2020));
        }

        jobs_adapter adapter = new jobs_adapter(this, temp_list);
        mListView.setAdapter(adapter);
    }


    /*ArrayList<job_model> LoadJobs()
    {
        ArrayList<job_model> temp_list = new ArrayList<job_model>(2);;
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Posts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            temp_list.add(new job_model(document.getString("Title"),document.getString("Description"),document.getString("Location"), year));
                            Log.d("check", (document.getString("Title") + document.getString("Description") + document.getString("Location")));
                        }
                    } else {
                        Log.d("check", "Error getting documents: ", task.getException());
                    }
                });


        return temp_list;

    }*/

}