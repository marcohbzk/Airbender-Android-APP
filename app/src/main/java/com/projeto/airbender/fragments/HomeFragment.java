package com.projeto.airbender.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.projeto.airbender.R;
import com.projeto.airbender.activities.FlightActivity;
import com.projeto.airbender.activities.LoginActivity;
import com.projeto.airbender.utils.DBHelper;
import com.projeto.airbender.utils.JsonParser;

public class HomeFragment extends Fragment {

    private Button btnBuyTicket;
    private TextView tvWelcome;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        tvWelcome = view.findViewById(R.id.tvWelcome);
        btnBuyTicket = view.findViewById(R.id.btnFlights);

        SharedPreferences sharedInfoUser = getActivity().getSharedPreferences("user_data", getContext().MODE_PRIVATE);
        String fName = sharedInfoUser.getString("FNAME", "");
        tvWelcome.setText("Welcome, " + fName + "!");

        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!JsonParser.isConnectionInternet(getContext())) {
                    Snackbar.make(view, "No internet connection", Snackbar.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getContext(), FlightActivity.class);
                    startActivity(intent);
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}