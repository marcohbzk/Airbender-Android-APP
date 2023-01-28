package com.projeto.airbender.fragments;

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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.projeto.airbender.R;
import com.projeto.airbender.activities.LoginActivity;

import java.io.Console;
import java.util.Objects;
import java.util.regex.Pattern;

public class SettingsFragment extends Fragment {

    private RadioButton rbLocal, rbRemote;
    private EditText etLocal, etRemote;
    private Button btnSave;
    private ImageButton btnBack;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        rbLocal = view.findViewById(R.id.rbLocal);
        rbRemote = view.findViewById(R.id.rbRemote);

        etLocal = view.findViewById(R.id.etLocal);
        etRemote = view.findViewById(R.id.etRemote);

        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);

        SharedPreferences preferences = getActivity().getSharedPreferences("settings", 0);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something when the button is clicked
                if(rbLocal.isChecked())
                    System.out.println("Local");
                    // preferences.edit().putString("SERVER", etLocal.getText().toString()).apply();
                else if(rbRemote.isChecked()) {
                    System.out.println("Remote");
                    String ip = etRemote.getText().toString().trim();
                    if(Pattern.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$",ip))
                        preferences.edit().putString("SERVER", ip).apply();
                    else {
                        etRemote.setError("Invalid IP"); return;
                    }
                }
                Toast.makeText(getActivity(), "Settings saved", Toast.LENGTH_SHORT).show();

                ((LoginActivity)getActivity()).replaceFragment(new LoginFragment(), false);
            }
        });

        // save on shared prefenrences
        if(Objects.equals(preferences.getString("SERVER", ""), "10.0.2.2")){
            // local server selected
            rbLocal.setChecked(true);
            rbRemote.setChecked(false);
        } else {
            // remote server selected
            rbLocal.setChecked(false);
            rbRemote.setChecked(true);
            etRemote.setText(preferences.getString("SERVER", ""));
        }
        return view;
    }
}