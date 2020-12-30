package com.team3.getjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class AddJobWindow extends AppCompatActivity implements View.OnClickListener {


    EditText job_title;
    TextView salary;
    TextView date;
    TextView location;
    TextView age;
    TextView phone;
    TextView languages;
    TextView job_detail;
    Button confirm_button;
    ImageButton back;

    @SuppressLint({"WrongViewCast", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_window);
        job_title = (EditText) findViewById(R.id.jobtitle_field);
        salary = (TextView) findViewById(R.id.salary_field);
        date = (TextView) findViewById(R.id.date_field);
        location = (TextView) findViewById(R.id.location_field);
        age = (TextView) findViewById(R.id.age_field);
        phone = (TextView) findViewById(R.id.phone_field);
        languages = (TextView) findViewById(R.id.languages_field);
        job_detail = (TextView) findViewById(R.id.jobdetail_field);
        confirm_button=findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(this);
        back=(ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddJobWindow.this, EmployerProfile.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.confirm_button:
                FirebaseFirestore db=FirebaseFirestore.getInstance();

                //add data and auto generate id+++++++++++++++++++++++++
                ArrayList<String> temp_lang=new ArrayList<String>();

                temp_lang.add(languages.getText().toString());

                Date temp_date = new Date();

                /*job_model jobs = new job_model(job_title.getText().toString(),job_detail.getText(),
                        location.getText().toString(), salary.getText(), "0", temp_date ,temp_lang, null);*/


                job_model jobs = new job_model(job_title.getText().toString(),job_detail.getText().toString(), location.getText().toString(), salary.getText().toString(),"0" ,true,temp_date,temp_lang, null);



                db.collection("Posts")
                        .add(jobs)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("check", "DocumentSnapshot written with ID: " + documentReference.getId());
                                Intent myIntent = new Intent(v.getContext(), EmployerProfile.class);
                                startActivity(myIntent);
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("check", "Error adding document", e);
                            }
                        });
                break;
        }
    }




}
