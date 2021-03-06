package com.team3.getjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team3.getjob.Utilities.Validation;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class JobAction extends AppCompatActivity {

    Button update_button;
    Button delete;
    EditText job_title;
    TextView salary;
    TextView date;
    TextView address;
    TextView age;
    TextView languages;
    TextView job_detail;
    ImageButton back;
    private String postId = null;
    public JobAction() {}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_action);
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

        back=(ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobAction.this, MainActivity.class);
                startActivity(intent);
            }
        });

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null) {
            postId =(String) b.get("keyName");
        }

        DocumentReference docRef = db.collection("Posts").document(postId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        job_title.setText(String.valueOf(document.getData().get("title")));
                        salary.setText(String.valueOf(document.getData().get("payment")));
                        address.setText(String.valueOf(document.getData().get("location")));
                        languages.setText(String.valueOf(document.getData().get("languages")));
                        job_detail.setText(String.valueOf(document.getData().get("description")));
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

        update_button = (Button) findViewById(R.id.save_edit);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkValidation()) {
                    DocumentReference ref = db.collection("Posts").document(postId);
                    ref
                            .update("title", job_title.getText().toString(), "payment", salary.getText().toString(), "location", address.getText().toString(), "description", job_detail.getText().toString(), "languages", FieldValue.arrayUnion(languages.getText().toString()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully updated!");
                                    Intent intent = new Intent(JobAction.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error updating document", e);
                                }
                            });
                }

            }
        });

        delete = (Button) findViewById(R.id.delete_post);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Posts").document(postId)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                Intent intent = new Intent(JobAction.this,MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error deleting document", e);
                            }
                        });
            }
        });


    }

    private boolean checkValidation(){
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