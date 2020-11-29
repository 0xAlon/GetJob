package com.team3.getjob;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterUser extends Fragment implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginFragment";
    private View binding;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = inflater.inflate(R.layout.fragment_register_user, container, false);
        View view = binding.getRootView();

        ImageButton back = (ImageButton) view.findViewById(R.id.back);
        back.setOnClickListener(this);

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

        }

    }

    //Password must contain minimum 8 characters at least 1 Alphabet, 1 Number
    private static boolean isValidPassword(String s) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]{8,24}");
        return !TextUtils.isEmpty(s) && pattern.matcher(s).matches();
    }
}
