package com.team3.getjob.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.team3.getjob.R;

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
        String InvalidLogin = getString(R.string.InvalidLogin);
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        /*Intent intent = new Intent(getContext(), JobListActivity.class);
                        startActivity(intent);*/
                    } else {
                        Toast.makeText(getContext(), InvalidLogin, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(Throwable::printStackTrace);
    }
}
