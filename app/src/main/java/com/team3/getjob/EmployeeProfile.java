package com.team3.getjob;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmployeeProfile extends BaseActivity {

    private FirebaseAuth mAuth;
    private String TAG="EmployeeProfile";
    TextView user_name;
    TextView email;
    TextView phone_num;
    TextView location;
    ListView mListView;
    FirebaseFirestore db;
    ArrayList<String> post_apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_employee_profile);
        View rootView = getLayoutInflater().inflate(R.layout.activity_employee_profile, frameLayout);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        user_name = (TextView) findViewById(R.id.user_name);
        email = (TextView) findViewById(R.id.email);
        phone_num = (TextView) findViewById(R.id.phone);
        location = (TextView) findViewById(R.id.location);


        mListView = findViewById(R.id.list);
        db = FirebaseFirestore.getInstance();


        //logout button
        View clickView = rootView.findViewById(R.id.nav);
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.LEFT);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateFields(currentUser);

        List<String> id_list = new ArrayList<String>();
        ArrayList<job_model> temp_list = new ArrayList<job_model>();
        Context context = this;

        //Data Pull
        DataBasePull(id_list, temp_list, context);

    }

    private void updateFields(FirebaseUser currentUser){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        post_apply=new ArrayList<String>();
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
                            list.add(String.valueOf(document.getData().get("Address ")));
                            post_apply=(ArrayList<String>) document.getData().get("Jobs");
                        }
                        user_name.setText(String.valueOf(list.get(0)));
                        email.setText(String.valueOf(list.get(1)));
                        phone_num.setText(String.valueOf(list.get(2)));
                        location.setText(String.valueOf(list.get(3)));
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
                                Log.d("CHECK", "updateFields: "+post_apply);

                                if(post_apply.contains(document.getId())){

                                    //Fill the class with data!!!!!
                                    job_model jobs = document.toObject(job_model.class);
                                    id_list.add(document.getId());

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