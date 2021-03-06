package com.team3.getjob.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team3.getjob.R;

public class WelcomeActivity extends Fragment implements View.OnClickListener {

    public FirebaseAuth mAuth;
    private FragmentManager fragmentManager;
    private static final String TAG = "LoginFragment";
    private View binding;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = inflater.inflate(R.layout.fragment_welcome, container, false);
        View view = binding.getRootView();

        Button login = (Button) view.findViewById(R.id.login);
        login.setOnClickListener(this);

        Button registerRecruiter = (Button) view.findViewById(R.id.recruiter);
        registerRecruiter.setOnClickListener(this);

        Button registerUser = (Button) view.findViewById(R.id.user);
        registerUser.setOnClickListener(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return view;
    }

    // constructor
    public WelcomeActivity(FragmentManager fragmentManager) {

        FirebaseApp.initializeApp(getContext());
        mAuth = FirebaseAuth.getInstance();
        this.fragmentManager = fragmentManager;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onClick(final View v) { //check for what button is pressed
        switch (v.getId()) {
            case R.id.login:
                fragmentManager.beginTransaction()
                        .replace(R.id.login_main_container, new LoginFragment(fragmentManager))
                        .addToBackStack("Login")
                        .commit();
                break;
            case R.id.recruiter:
                fragmentManager.beginTransaction()
                        .replace(R.id.login_main_container, new RegisterRecruiter())
                        .addToBackStack("RegisterRecruiter")
                        .commit();
                break;
            case R.id.user:
                fragmentManager.beginTransaction()
                        .replace(R.id.login_main_container, new RegisterUser())
                        .addToBackStack("RegisterUser")
                        .commit();
                break;

        }
    }
}