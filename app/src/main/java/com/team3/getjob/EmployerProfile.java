package com.team3.getjob;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployerProfile extends BaseActivity {

    private FirebaseAuth mAuth;
    private String TAG = "EmployerProfile";
    private Button add_job_button;
    private Button logout;
    TextView user_name;
    TextView email;
    TextView phone_num;
    TextView company_name;
    TextView location;
    ImageButton back;
    ImageButton excel_button;
    FirebaseFirestore db;
    ListView mListView;
    ArrayList<String> my_posts;

    public EmployerProfile() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.employer_profile);

        View rootView = getLayoutInflater().inflate(R.layout.employer_profile, frameLayout);

        /*View clickView = rootView.findViewById(R.id.nav);
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.LEFT);

            }
        });*/

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        user_name = (TextView) findViewById(R.id.user_name);
        email = (TextView) findViewById(R.id.email);
        phone_num = (TextView) findViewById(R.id.phone);
        company_name = (TextView) findViewById(R.id.company_name);
        location = (TextView) findViewById(R.id.location);
        //back
        back=(ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployerProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });


        mListView = findViewById(R.id.list);
        db = FirebaseFirestore.getInstance();



        //logout button
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingOut();
            }
        });

        //Data Pull
        List<String> id_list = new ArrayList<String>();
        ArrayList<job_model> temp_list = new ArrayList<job_model>();
        Context context = this;
        DataBasePull(id_list, temp_list, context);

        add_job_button = (Button) findViewById(R.id.addjob);
        add_job_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddJobWindow();
                ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
            }
        });


        //Delete from database:
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(EmployerProfile.this, JobAction.class);
                intent.putExtra("keyName", id_list.get(position));
                startActivity(intent);


            }
        });

        //download to excel
//        excel_button=(ImageButton) findViewById(R.id.excel_button);
//        excel_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateFields(currentUser);
    }

    private void updateFields(FirebaseUser currentUser) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        my_posts=new ArrayList<String>();
        db.collection("Users").whereEqualTo("Uid", currentUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId());
                            list.add(String.valueOf(document.getData().get("Name")));
                            list.add(String.valueOf(document.getData().get("Email")));
                            list.add(String.valueOf(document.getData().get("PhoneNumber")));
                            list.add(String.valueOf(document.getData().get("Company")));
                            list.add(String.valueOf(document.getData().get("Address")));
                            my_posts=(ArrayList<String>) document.getData().get("MyJobs");
                        }
                        Log.d("CHECK", "my_post: "+my_posts);
                        user_name.setText(String.valueOf(list.get(0)));
                        email.setText(String.valueOf(list.get(1)));
                        phone_num.setText(String.valueOf(list.get(2)));
                        company_name.setText(String.valueOf(list.get(3)));
                        location.setText(String.valueOf(list.get(4)));

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void openAddJobWindow() {
        Intent intent = new Intent(this, AddJobWindow.class);
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
                                Log.d("CHECK", "DataBasePull: "+my_posts);

                                if(my_posts.contains(document.getId())){
                                    //Log.d("test -----> ",document.getId());
                                    //Fill the class with data!!!!!
                                    job_model jobs = document.toObject(job_model.class);
                                    id_list.add(document.getId());
                                    //temp_list.add(new job_model(jobs.getTitle(),jobs.getDescription(), jobs.getLocation(), jobs.getPayment(),jobs.getRank(),jobs.getDate(),jobs.getLanguages()));
                                    temp_list.add(new job_model(jobs.getTitle(),jobs.getDescription(), jobs.getLocation(), jobs.getPayment(),jobs.getRank() ,jobs.isAgeAdult(),jobs.getDate(),jobs.getLanguages(), jobs.getUsers()));
                                    jobs_adapter adapter = new jobs_adapter(context, temp_list);

                                    //Set data to list view!!!!
                                    mListView.setAdapter(adapter);
                                }
                            }
                        } else {
                            Log.d("check", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void SingOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
