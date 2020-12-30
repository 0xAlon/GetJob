package com.team3.getjob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
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

public class Jobs_Pull extends BaseActivity {

    FirebaseFirestore db;
    ListView mListView;
    private FirebaseAuth mAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_jobs__pull);
        View rootView = getLayoutInflater().inflate(R.layout.activity_jobs__pull, frameLayout);

        View clickView = rootView.findViewById(R.id.nav);
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.LEFT);

            }
        });


        //Setup
        mListView = findViewById(R.id.list_view);
        db = FirebaseFirestore.getInstance();
        TextView filter_button = (TextView) findViewById(R.id.jobs_search);

        //User path
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userId = currentUser.getUid();
        //Add list for user applied jobs

        //Add button apply inside fragment jobs


        //Additional Setup
        List<String> id_list = new ArrayList<String>();
        ArrayList<job_model> temp_list = new ArrayList<job_model>();
        ArrayList<String> ranks = new ArrayList<String>();
        ArrayList<String> languages = new ArrayList<String>();
        Context context = this;
        Log.d("checkAge", "age vak is " + Filter_model.ageAdult);


        //Filter and DataPull call
        if(Filter_model.s_languages.size() != 0 || Filter_model.s_ranks.size() != 0 || Filter_model.Min_payment != null || Filter_model.Max_payment != null || Filter_model.ageAdult)
        {

            //Setup
            ArrayList<String> temp_lang = Filter_model.s_languages;
            ArrayList<String> temp_ranks = Filter_model.s_ranks;
            String MaxVAL = Filter_model.Max_payment;
            String MinVal = Filter_model.Min_payment;
            boolean ageVal=Filter_model.ageAdult;
            //Data Pull WITH FILTERS
            DataBasePull(id_list, temp_list, context, temp_lang, temp_ranks, MaxVAL, MinVal,ageVal);
        }
        else
        {
            //WITHOUT
            DataBasePullFull(id_list, temp_list, context);
        }

        //Click to open fragment with all data
        Open_Post(id_list);

        //Click to go over Filter activity
        Filter_Button(filter_button);
    }

    private void DataBasePull(List<String> id_list, ArrayList<job_model> temp_list, Context context, ArrayList<String> temp_lang, ArrayList<String> temp_ranks, String MaxVal, String MinVal,boolean age) {
        db.collection("Posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("check", document.getId() + " => " + document.getData());

                                    //Job object fill
                                    job_model jobs = document.toObject(job_model.class);

                                    //Check filter
                                if(Check_filter_lang(jobs, temp_lang) && Check_filter_rank(jobs, temp_ranks) && Check_filter_payment(jobs, MaxVal, MinVal) && Check_Filter_age(jobs,age))
                                    {
                                        //Id list for future post click and fragment transition
                                        id_list.add(document.getId());

                                        //fill list of jib objects
                                        temp_list.add(new job_model(jobs.getTitle(),jobs.getDescription(), jobs.getLocation(), jobs.getPayment(),jobs.getRank() ,jobs.isAgeAdult(),jobs.getDate(),jobs.getLanguages()));

                                        //Show list of rows(jobs)
                                        jobs_adapter adapter = new jobs_adapter(context, temp_list);
                                        mListView.setAdapter(adapter);

                                    }
                            }
                        } else {
                            Log.d("check", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    //Without Filter
    private void DataBasePullFull(List<String> id_list, ArrayList<job_model> temp_list, Context context) {
        db.collection("Posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("check", document.getId() + " => " + document.getData());

                                //Job object fill
                                job_model jobs = document.toObject(job_model.class);

                                //Id list for future post click and fragment transition
                                id_list.add(document.getId());

                                //fill list of jib objects
                                temp_list.add(new job_model(jobs.getTitle(),jobs.getDescription(), jobs.getLocation(), jobs.getPayment(),jobs.getRank(), jobs.isAgeAdult(), jobs.getDate(),jobs.getLanguages()));

                                //Show list of rows(jobs)
                                jobs_adapter adapter = new jobs_adapter(context, temp_list);
                                mListView.setAdapter(adapter);

                            }
                        } else {
                            Log.d("check", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



    private void Filter_Button(TextView filter_button) {
        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Jobs_Pull.this, Filter.class);
                startActivity(intent2);
            }
        });
    }

    private void Open_Post(List<String> id_list) {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jobs_pull_fragment jobsPullFragment = jobs_pull_fragment.newInstance(id_list.get(i), userId);
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.jobs_pull_frame, jobsPullFragment).commit();
            }
        });
    }

    private boolean Check_filter_lang(job_model jobs, ArrayList<String> langs)
    {
        if(langs.size() == 0) return true;
        for(int i = 0; i < langs.size(); i++)
        {

            if(jobs.getLanguages().contains(langs.get(i)))
            {
                return true;
            }
        }

        return false;
    }

    private boolean Check_filter_rank(job_model jobs, ArrayList<String> rank)
    {
        if(rank.size() == 0) return true;
        for(int i = 0; i < rank.size(); i++)
        {
            if(jobs.getRank().equals(rank.get(i)))
            {
                return true;
            }
        }
        return false;
    }

    private boolean Check_filter_payment(job_model jobs, String Max, String Min)
    {
        if(Max == null || Min == null) return true;
        int check=Integer.parseInt(jobs.getPayment());
        int low=Integer.parseInt(Min);
        int high=Integer.parseInt(Max);
        return (check >= low) && (check <= high);
    }

    private boolean Check_Filter_age(job_model jobs,boolean age)
    {
        if(!age)
            return true;
        else return jobs.isAgeAdult() == age;
    }
}