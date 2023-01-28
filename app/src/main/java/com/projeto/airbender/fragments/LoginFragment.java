package com.projeto.airbender.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projeto.airbender.R;
import com.projeto.airbender.activities.LoginActivity;
import com.projeto.airbender.models.SingletonAirbender;

public class LoginFragment extends Fragment {

    private EditText etUsername, etPassword;
    private FloatingActionButton fabSettings;
    private Button btnLogin;
    private LoginActivity activity;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        fabSettings = view.findViewById(R.id.fabSettings);
        activity = (LoginActivity) getActivity();

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something when the button is clicked
                LoginActivity activity = (LoginActivity) getActivity();
                activity.replaceFragment(new SettingsFragment(), true);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (username.isEmpty()) {
                    etUsername.setError("Invalid username");
                    return;
                }
                if (password.isEmpty() || password.length() < 6) {
                    etPassword.setError("Invalid password");
                    return;
                }

                // chamar o método login da activity
                activity.attemptLogin(username, password);

            }
        });

        return view;
    }
}