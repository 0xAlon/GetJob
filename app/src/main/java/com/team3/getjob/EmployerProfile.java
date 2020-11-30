package com.team3.getjob;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployerProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String TAG="EmployerProfile";
    private Button add_job_button;
    TextView user_name;
    TextView email;
    TextView phone_num;
    TextView company_name;
    TextView loction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_profile);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        user_name = (TextView) findViewById(R.id.user_name);
        email = (TextView) findViewById(R.id.email);
        phone_num = (TextView) findViewById(R.id.phone);
        company_name = (TextView) findViewById(R.id.company_name);
        loction = (TextView) findViewById(R.id.location);

        add_job_button = (Button) findViewById(R.id.addjob);
        add_job_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddJobWindow();
            }
        });
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
                        company_name.setText(String.valueOf(list.get(3)));
                        loction.setText(String.valueOf(list.get(4)));

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
}
