package com.team3.getjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.team3.getjob.Utilities.Validation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddJobWindow extends AppCompatActivity implements View.OnClickListener {


    EditText job_title;
    TextView salary;
    TextView date;
    TextView address;
    TextView age;
    TextView languages;
    TextView job_detail;
    Button confirm_button;
    ImageButton back;
    private FirebaseAuth mAuth;

    @SuppressLint({"WrongViewCast", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_window);
        //test
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        job_title = (EditText) findViewById(R.id.jobtitle_field);
        job_title.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isValidCompany(job_title.getText().toString())) {
                    job_title.setError("יש להזין כותרת");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        salary = (TextView) findViewById(R.id.salary_field);
        salary.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isValidSalary(salary.getText().toString())) {
                    salary.setError("מחיר לא חוקי");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        date = (TextView) findViewById(R.id.date_field);

        address = (TextView) findViewById(R.id.location_field);


        age = (TextView) findViewById(R.id.age_field);
        age.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isValidAge(age.getText().toString())) {
                    age.setError(getString(R.string.InvalidAge));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        languages = (TextView) findViewById(R.id.languages_field);
        languages.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isValidCompany(languages.getText().toString())) {
                    languages.setError("יש להזין שפה");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        job_detail = (TextView) findViewById(R.id.jobdetail_field);
        job_detail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isValidCompany(job_detail.getText().toString())) {
                    job_detail.setError("יש להזין תיאור");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

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

                if(checkValidation()) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    //add data and auto generate id+++++++++++++++++++++++++
                    ArrayList<String> temp_lang = new ArrayList<String>();

                    temp_lang.add(languages.getText().toString());
                    Date temp_date = new Date();
                    job_model jobs = new job_model(job_title.getText().toString(), job_detail.getText().toString(),address.getText().toString(), salary.getText().toString(), "0", true, temp_date, temp_lang, null);
                    db.collection("Posts")
                            .add(jobs)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("AddJobWindow", "DocumentSnapshot written with ID: " + documentReference.getId());
                                    addToMyJobs(documentReference.getId());
                                    Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                                    startActivity(myIntent);
                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("check", "Error adding document", e);
                                }
                            });
                }
                break;
        }
    }

    public void addToMyJobs(String postID) {
        Log.d("addToMyJobs", "get in");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //test
        FirebaseUser currentUser = mAuth.getCurrentUser();

        db.collection("Users")
                .whereEqualTo("Uid", currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                DocumentReference ref = db.collection("Users").document(document.getId());
                                ref.update("MyJobs", FieldValue.arrayUnion(postID));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private boolean checkValidation(){
        if (!Validation.isValidAge(age.getText().toString())) {
            return false;
        }
        if (!Validation.isValidSalary(salary.getText().toString())) {
            return false;
        }
        if (!Validation.isValidCompany(job_title.getText().toString())) {
            return false;
        }
        if (!Validation.isValidCompany(job_detail.getText().toString())) {
            return false;
        }
        if (!Validation.isValidCompany(languages.getText().toString())) {
            return false;
        }
        return true;
    }

}

