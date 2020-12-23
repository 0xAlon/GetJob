package com.team3.getjob.Login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.team3.getjob.R;
import com.team3.getjob.Utilities.Validation;

public class ForgotPassword extends Fragment implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private View binding;
    private String TAG = "ForgotPassword";
    private FragmentManager fragmentManager;

    EditText email;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        View view = binding.getRootView();

        ImageButton back = (ImageButton) view.findViewById(R.id.back);
        back.setOnClickListener(this);

        Button next = (Button) view.findViewById(R.id.next);
        next.setOnClickListener(this);

        TextView remember = (TextView) view.findViewById(R.id.remember);
        remember.setOnClickListener(this);

        email = (EditText) view.findViewById(R.id.email_field);

        return view;
    }

    public ForgotPassword(FragmentManager fragmentManager) {

        FirebaseApp.initializeApp(getContext());
        mAuth = FirebaseAuth.getInstance();
        this.fragmentManager = fragmentManager;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
            case R.id.remember:
                getFragmentManager().popBackStackImmediate();
                break;
            case R.id.next:
                if (checkValidation()){
                    mAuth.sendPasswordResetEmail(email.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(getContext(), "Email sent.", Toast.LENGTH_SHORT).show();
                                    fragmentManager.popBackStack();
                                } else {
                                    Toast.makeText(getContext(), "Email not sent.", Toast.LENGTH_SHORT).show();

                                }
                            });
                }
                break;
        }
    }

    private boolean checkValidation(){
        if (!Validation.isEmailValid(email.getText().toString())) {
            Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
