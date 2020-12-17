package com.team3.getjob;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class RegisterRecruiter extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginFragment";
    private View binding;

    TextView password;
    TextView email;
    TextView name;
    TextView id;
    TextView company;
    TextView address;
    TextView phone;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = inflater.inflate(R.layout.fragment_register_recruiter, container, false);
        View view = binding.getRootView();

        ImageButton back = (ImageButton) view.findViewById(R.id.back);
        back.setOnClickListener(this);

        Button register = (Button) view.findViewById(R.id.register);
        register.setOnClickListener(this);

        password = (TextView) view.findViewById(R.id.password_field);
        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isValidPassword(password.getText().toString())) {
                    password.setError(getString(R.string.InvalidPassword));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        email = (TextView) view.findViewById(R.id.email_field);
        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isEmailValid(email.getText().toString())) {
                    email.setError(getString(R.string.InvalidEmail));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        name = (TextView) view.findViewById(R.id.name_field);
        name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isAlpha(name.getText().toString())) {
                    name.setError(getString(R.string.InvalidName));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        id = (TextView) view.findViewById(R.id.id_field);
        id.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isValidId(id.getText().toString())) {
                    id.setError(getString(R.string.InvalidId));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        company = (TextView) view.findViewById(R.id.company_field);
        company.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isValidCompany(company.getText().toString())) {
                    company.setError(getString(R.string.InvalidName));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        address = (TextView) view.findViewById(R.id.address_field);
        address.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isAlpha(address.getText().toString())) {
                    address.setError(getString(R.string.InvalidName));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        phone = (TextView) view.findViewById(R.id.phone_field);
        phone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!Validation.isValidPhoneNumber(phone.getText().toString())) {
                    phone.setError(getString(R.string.InvalidPhone));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        return view;
    }


    public RegisterRecruiter() {

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
                if (checkValidation()) {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(getActivity(), task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("Uid", user.getUid());
                                    userData.put("Address", String.valueOf(address.getText().toString()));
                                    userData.put("Id", Integer.parseInt(id.getText().toString()));
                                    userData.put("Email", String.valueOf(email.getText().toString()));
                                    userData.put("Company", String.valueOf(company.getText().toString()));
                                    userData.put("Name", String.valueOf(name.getText().toString()));
                                    userData.put("UserType", 3);
                                    userData.put("Rating", 0);
                                    userData.put("PhoneNumber", Integer.parseInt(phone.getText().toString()));

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

    private boolean checkValidation() {
        if (!Validation.isValidPassword(password.getText().toString())) {
            return false;
        }
        if (!Validation.isEmailValid(email.getText().toString())) {
            return false;
        }
        if (!Validation.isAlpha(name.getText().toString())) {
            return false;
        }
        if (!Validation.isAlpha(address.getText().toString())) {
            return false;
        }
        if (!Validation.isValidCompany(company.getText().toString())) {
            return false;
        }
        if (!Validation.isValidId(id.getText().toString())) {
            return false;
        }
        if (!Validation.isValidPhoneNumber(phone.getText().toString())) {
            return false;
        }
        return true;
    }

}