package com.team3.getjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.team3.getjob.Login.WelcomeActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static FragmentManager fragmentManager;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (currentUser == null) { // User not exist
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.login_main_container, new WelcomeActivity(fragmentManager))
                    .commit();
        } else { // User exist check fot type and start the necessary activity
            Log.d(TAG, currentUser.getUid());
            db.collection("Users").whereEqualTo("Uid", currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getData().get("UserType").equals("1")) {
                                    //Intent intent = new Intent(this, EmployerProfile.class);
                                    //startActivity(intent);
                                } else {
                                    Intent intent = new Intent(this, Jobs_Pull.class);
                                    startActivity(intent);
                                }

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });
        }
=======
//        Intent intent = new Intent(this, EmployerProfile.class);
//        startActivity(intent);
//        Intent intent = new Intent(this, EmployeeProfile.class);
//        startActivity(intent);
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
>>>>>>> origin/ArtiomHackathon
    }

}