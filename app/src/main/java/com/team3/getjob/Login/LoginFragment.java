package com.team3.getjob.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team3.getjob.Jobs_Pull;
import com.team3.getjob.R;
import com.team3.getjob.Utilities.Validation;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    private static final String TAG = "Login";
    private View binding;

    TextView password;
    TextView email;

    public LoginFragment(FragmentManager fragmentManager) {
        FirebaseApp.initializeApp(getContext());
        mAuth = FirebaseAuth.getInstance();
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = inflater.inflate(R.layout.fragment_login, container, false);
        View view = binding.getRootView();

        ImageButton back = (ImageButton) view.findViewById(R.id.back);
        back.setOnClickListener(this);

        Button login = (Button) view.findViewById(R.id.login);
        login.setOnClickListener(this);

        TextView forgot_password = (TextView) view.findViewById(R.id.forget_password);
        forgot_password.setOnClickListener(this);

        password = (TextView) view.findViewById(R.id.password_field);
        email = (TextView) view.findViewById(R.id.email_field);

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

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                getFragmentManager().popBackStackImmediate();
                break;

            case R.id.login:
                login();
                break;

            case R.id.forget_password:
                fragmentManager.beginTransaction()
                        .replace(R.id.login_main_container, new ForgotPassword(fragmentManager))
                        .addToBackStack("ForgotPassword")
                        .commit();
                break;
        }
    }

    void login() {
        String user_email, user_password;
        user_email = email.getText().toString();
        user_password = password.getText().toString();


        if (TextUtils.isEmpty(user_email)) {
            Toast.makeText(getActivity(), "Invalid Email Or Password toast", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(user_password)) {
            Toast.makeText(getActivity(), "Invalid Email Or Password toast", Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getContext(), Jobs_Pull.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
}
