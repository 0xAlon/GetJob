package com.team3.getjob;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployerProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String TAG="EmployerProfile";
    private Button add_job_button;
    TextView user_name;
    TextView email;
    TextView phone_num;
    TextView id;
    TextView location;
    FirebaseFirestore db;
    ListView mListView;
    ImageView mDeleteImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_profile);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        user_name = (TextView) findViewById(R.id.user_name);
        email = (TextView) findViewById(R.id.email);
        phone_num = (TextView) findViewById(R.id.phone);
        id = (TextView) findViewById(R.id.company_name);
        location = (TextView) findViewById(R.id.location);

        add_job_button = (Button) findViewById(R.id.addjob);
        add_job_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddJobWindow();
            }
        });

        mListView = findViewById(R.id.list);
        db = FirebaseFirestore.getInstance();

        List<String> id_list = new ArrayList<String>();
        ArrayList<job_model> temp_list = new ArrayList<job_model>();
        Context context = this;


        //Data Pull
        DataBasePull(id_list, temp_list, context);

        //Delete from database:

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateFields(currentUser);
    }

    private void updateFields(FirebaseUser currentUser){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users").whereEqualTo("Uid", "W4CyywCheWVdQfpTLocUC8Kfysj1")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId());
                            list.add(String.valueOf(document.getData().get("Name")));
                            list.add(String.valueOf(document.getData().get("Email")));
                            list.add(String.valueOf(document.getData().get("PhoneNumber")));
                            list.add(String.valueOf(document.getData().get("Company ")));
                            list.add(String.valueOf(document.getData().get("Address ")));
                        }
                        user_name.setText(String.valueOf(list.get(0)));
                        email.setText(String.valueOf(list.get(1)));
                        phone_num.setText(String.valueOf(list.get(2)));
                        id.setText(String.valueOf(list.get(3)));
                        location.setText(String.valueOf(list.get(4)));

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void openAddJobWindow()
    {
        Intent intent = new Intent(this,AddJobWindow.class);
        startActivity(intent);
    }


    private void DataBasePull(List<String> id_list, ArrayList<job_model> temp_list, Context context) {
        db.collection("Posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("check", document.getId() + " => " + document.getData());

                                //Fill the class with data!!!!!
                                job_model jobs = document.toObject(job_model.class);
                                id_list.add(document.getId());
                                temp_list.add(new job_model(jobs.getTitle(),jobs.getDescription(), jobs.getLocation(), jobs.getPayment(),jobs.getRank(),jobs.getDate(),jobs.getLanguages()));
                                jobs_adapter adapter = new jobs_adapter(context, temp_list);

                                //Set data to list view!!!!
                                mListView.setAdapter(adapter);
                            }
                        } else {
                            Log.d("check", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void deleteFromDB(FirebaseUser currentUser){
        db.collection("Posts").document(String.valueOf(currentUser))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }


}
