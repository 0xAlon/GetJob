package com.team3.getjob;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends Fragment implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginFragment";
    private View binding;

    TextView password;
    TextView email;
    TextView name;
    TextView id;
    TextView age;
    TextView address;
    TextView phone;


    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = inflater.inflate(R.layout.fragment_register_user, container, false);
        View view = binding.getRootView();

        ImageButton back = (ImageButton) view.findViewById(R.id.back);
        back.setOnClickListener(this);

        Button register = (Button) view.findViewById(R.id.register);
        register.setOnClickListener(this);

        password = (TextView) view.findViewById(R.id.password_field);
        email = (TextView) view.findViewById(R.id.email_field);
        name = (TextView) view.findViewById(R.id.name_field);
        id = (TextView) view.findViewById(R.id.id_field);
        age = (TextView) view.findViewById(R.id.age_field);
        address = (TextView) view.findViewById(R.id.address_field);
        phone = (TextView) view.findViewById(R.id.phone_field);

        return view;
    }


    public RegisterUser() {

        FirebaseApp.initializeApp(getContext());
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                getFragmentManager().popBackStackImmediate();
                break;

            case R.id.register:
                if (checkValidation()){
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(getActivity(), task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("Uid", user.getUid());
                                    userData.put("Address", String.valueOf(address.getText().toString()));
                                    userData.put("Age", String.valueOf(age.getText().toString()));
                                    userData.put("Email", String.valueOf(email.getText().toString()));
                                    userData.put("Id", String.valueOf(id.getText().toString()));
                                    userData.put("Name", String.valueOf(name.getText().toString()));
                                    userData.put("PhoneNumber", String.valueOf(phone.getText().toString()));

                                    db.collection("Users") // Add a new document with a generated ID
                                            .add(userData)
                                            .addOnSuccessListener(documentReference -> {
                                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                    /*
                                                    Intent intent = new Intent(getContext(), JobListActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                    startActivity(intent);*/
                                            })
                                            .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                                }
                            }).addOnFailureListener(Throwable::printStackTrace);
                }
                break;
        }
    }

    private boolean checkValidation(){
        if (!Validation.isValidPassword(password.getText().toString())){
            Toast.makeText(getActivity(),"Invalid Password",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validation.isEmailValid(email.getText().toString())){
            Toast.makeText(getActivity(),"Invalid Email",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validation.isAlpha(name.getText().toString())){
            Toast.makeText(getActivity(),"Invalid Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validation.isAlpha(address.getText().toString())){
            Toast.makeText(getActivity(),"Invalid Address",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validation.isValidAge(age.getText().toString())){
            Toast.makeText(getActivity(),"Invalid Age",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validation.isValidId(id.getText().toString())){
            Toast.makeText(getActivity(),"Invalid ID number",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validation.isValidPhoneNumber(phone.getText().toString())){
            Toast.makeText(getActivity(),"Invalid Phone number",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
