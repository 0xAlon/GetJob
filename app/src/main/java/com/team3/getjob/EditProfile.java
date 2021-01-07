package com.team3.getjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EditProfile extends AppCompatActivity {
    String TAG = "EditProfile";
    Button update_button;
    Button delete;
    TextView password;
    TextView email;
    TextView name;
    TextView id;
    TextView age;
    TextView address;
    TextView phone;
    ImageButton back;
    TextView old_password;
    private FirebaseAuth mAuth;

    private String postId = null;

    public EditProfile() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        setContentView(R.layout.activity_edit_profile);
        name = (EditText) findViewById(R.id.name_field);
        id = (TextView) findViewById(R.id.id_field);
        email = (TextView) findViewById(R.id.email_field);
        password = (TextView) findViewById(R.id.password_field);
        age = (TextView) findViewById(R.id.age_field);
        phone = (TextView) findViewById(R.id.phone_field);
        address = (TextView) findViewById(R.id.address_field);
        old_password = (TextView) findViewById(R.id.old_password_field);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });


        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users").whereEqualTo("Uid", currentUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            name.setText(String.valueOf(document.getData().get("Name")));
                            id.setText(String.valueOf(document.getData().get("Id")));
                            email.setText(String.valueOf(document.getData().get("Email")));
                            age.setText(String.valueOf(document.getData().get("Age")));
                            phone.setText(String.valueOf(document.getData().get("PhoneNumber")));
                            address.setText(String.valueOf(document.getData().get("Address")));
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });


        update_button = (Button) findViewById(R.id.save_edit_profile);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().toString().isEmpty()) {
                    PasswordUpdate();
                }

                db.collection("Users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        if (currentUser.getUid().equals(String.valueOf(document.get("Uid")))) {
                                            DocumentReference ref = db.collection("Users").document(document.getId());
                                            ref
                                                    .update("Name", name.getText().toString(),"Address", address.getText().toString(), "PhoneNumber",phone.getText().toString(),"Id",id.getText().toString(),"Age",age.getText().toString(),"Email",email.getText().toString())
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d("TAG", "DocumentSnapshot successfully updated!");
                                                            Intent intent = new Intent(EditProfile.this, MainActivity.class);
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
                                } else {
                                    Log.d("check", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });

        delete = (Button) findViewById(R.id.delete_user);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("Users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        if (currentUser.getUid().equals(String.valueOf(document.get("Uid")))) {

                                            db.collection("Users").document(document.getId())//UserID
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                                            FirebaseAuth.getInstance().signOut();
                                                            Intent intent = new Intent(EditProfile.this, MainActivity.class);
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
                                    }
                                } else {
                                    Log.d("check", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });

    }

    private void PasswordUpdate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(password.getText().toString(), old_password.getText().toString());

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("TAG", "Password updated");
                                    } else {
                                        Log.d("TAG", "Error password not updated");
                                    }
                                }
                            });
                        } else {
                            Log.d("TAG", "Error auth failed");
                        }
                    }
                });
    }
}