package com.team3.getjob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected FrameLayout frameLayout;


    private DrawerLayout drawer;
    private String TAG = "BaseActivity";
    private FirebaseAuth mAuth;
    private String userType;
    MenuItem name;
    MenuItem type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //context=this;
        frameLayout = (FrameLayout) findViewById(R.id.container);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        name = menu.findItem(R.id.name);
        type = menu.findItem(R.id.type);

        initUserName();
        initUserType();
    }

    private void initUserName() {

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (currentUser != null){
            db.collection("Users").whereEqualTo("Uid", currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (currentUser.getUid().equals(String.valueOf(document.getData().get("Uid")))) {
                                    name.setTitle(String.valueOf(document.getData().get("Name")));
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });

        }
        else{
            name.setTitle("Error getting Name");
        }
    }

    private void initUserType() {

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (currentUser != null){
            db.collection("Users").whereEqualTo("Uid", currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    switch (String.valueOf(document.getData().get("UserType"))){
                                        case "1":
                                            type.setTitle("נער");
                                            userType = "1";
                                            break;
                                        case "2":
                                            type.setTitle("מחפש עבודה");
                                            userType = "2";
                                            break;
                                        case "3":
                                            type.setTitle("מגייס");
                                            userType = "3";
                                            break;
                                        default:
                                            break;
                                    }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });

        }
        else{
            name.setTitle("Error getting Name");
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            if (userType.equals("1") || userType.equals("2")){
                Intent intent = new Intent(this, EmployeeProfile.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, EmployerProfile.class);
                startActivity(intent);
            }

        } else if (id == R.id.job_list) {
            Intent intent = new Intent(this, Jobs_Pull.class);
            startActivity(intent);

        } else if (id == R.id.about) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);

        } else if (id == R.id.edit) {

        } else if (id == R.id.git){
            String url = "https://github.com/0xAlon/GetJob";
            Intent browserIntent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
            //drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
