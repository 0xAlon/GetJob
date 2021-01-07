package com.team3.getjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class JobAction extends AppCompatActivity {

    Button update_button;
    Button delete;
    EditText job_title;
    TextView salary;
    TextView date;
    TextView location;
    TextView age;
    TextView phone;
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
        salary = (TextView) findViewById(R.id.salary_field);
        date = (TextView) findViewById(R.id.date_field);
        location = (TextView) findViewById(R.id.location_field);
        age = (TextView) findViewById(R.id.age_field);
        phone = (TextView) findViewById(R.id.phone_field);
        languages = (TextView) findViewById(R.id.languages_field);
        job_detail = (TextView) findViewById(R.id.jobdetail_field);
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
                        location.setText(String.valueOf(document.getData().get("location")));
                        phone.setText(String.valueOf(document.getData().get("phone")));
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

                DocumentReference ref = db.collection("Posts").document(postId);
                ref
                        .update("title", job_title.getText().toString(), "payment", salary.getText().toString(), "location", location.getText().toString(),"phone",phone.getText().toString(), "description",job_detail.getText().toString(),"languages", FieldValue.arrayUnion(languages.getText().toString()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully updated!");
                                Intent intent = new Intent(JobAction.this,MainActivity.class);
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
}