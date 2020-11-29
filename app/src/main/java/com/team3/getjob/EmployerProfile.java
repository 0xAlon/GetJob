package com.team3.getjob;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class EmployerProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String TAG="EmployerProfile";
    TextView user_name;
    TextView email;
    TextView phone_num;
    TextView company_name;
    TextView loction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_profile);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        user_name = (TextView)findViewById(R.id.user_name);
        //user_name.setText(SetUserInfo("Name").get(0));
        SetUserInfo("Name").get(0);
    }

    ArrayList<String> SetUserInfo(String value)
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<String> temp_list = new ArrayList<String>(1);
        db.collection("Users").whereEqualTo("Uid", "W4CyywCheWVdQfpTLocUC8Kfysj1")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId());
                            temp_list.add(document.getString(value));
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
        return temp_list;
    }


}