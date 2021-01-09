package com.team3.getjob;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class EmployerProfile extends BaseActivity {

    private FirebaseAuth mAuth;
    private String TAG = "EmployerProfile";
    private ImageButton add_job_button;
    TextView user_name;
    TextView email;
    TextView phone_num;
    TextView company_name;
    TextView location;
    ImageButton excel_button;
    FirebaseFirestore db;
    ListView mListView;
    ArrayList<String> my_posts;
    File directory, sd, file;
    WritableWorkbook workbook;
    List<MyInfo> listdata;

    public EmployerProfile() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View rootView = getLayoutInflater().inflate(R.layout.employer_profile, frameLayout);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        user_name = (TextView) findViewById(R.id.user_name);
        email = (TextView) findViewById(R.id.email);
        phone_num = (TextView) findViewById(R.id.phone);
        company_name = (TextView) findViewById(R.id.company_name);
        location = (TextView) findViewById(R.id.location);

        View clickView = rootView.findViewById(R.id.nav);
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.LEFT);

            }
        });

        mListView = findViewById(R.id.list);
        db = FirebaseFirestore.getInstance();

        //Data Pull
        List<String> id_list = new ArrayList<String>();
        ArrayList<job_model> temp_list = new ArrayList<job_model>();
        Context context = this;
        DataBasePull(id_list, temp_list, context);

        add_job_button = (ImageButton) findViewById(R.id.addjob);
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
        excel_button=(ImageButton) findViewById(R.id.excel_button);
        excel_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               createExcelSheet();

            }
        });
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

    public void getExcelData(){
        listdata = new ArrayList<>();

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
                                    listdata.add(new MyInfo(String.valueOf(document.get("title"))));
                                }
                            }
                        } else {
                            Log.d("check", "Error getting documents: ", task.getException());
                        }
                    }
                });





    }


    public void createExcelSheet() {
        if(isStoragePermissionGranted()) {
            getExcelData();
            String csvFile = "GetJob.xls";
            sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            directory = new File(sd.getAbsolutePath());
            file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            try {
                workbook = Workbook.createWorkbook(file, wbSettings);
                createFirstSheet();

                //closing cursor
                workbook.write();
                workbook.close();

                Toast.makeText(this, "File Saved !", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{

            Toast.makeText(this, "Permission Denied !", Toast.LENGTH_SHORT).show();
        }
    }

    public void createFirstSheet() {
        try {

            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "NameInitial"));
            sheet.addCell(new Label(1, 0, "firstName"));


            for (int i = 0; i < listdata.size(); i++) {
                sheet.addCell(new Label(0, i + 1, listdata.get(i).getName()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}
