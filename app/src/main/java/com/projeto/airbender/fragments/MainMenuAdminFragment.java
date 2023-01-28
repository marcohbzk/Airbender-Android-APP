package com.projeto.airbender.fragments;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.projeto.airbender.R;
import com.projeto.airbender.activities.LoginActivity;

public class MainMenuAdminFragment extends Fragment {
    private Button btnLogout;
    private ExtendedFloatingActionButton fabCheckIn;

    public MainMenuAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu_admin, container, false);
        btnLogout = view.findViewById(R.id.btnLogout);
        fabCheckIn = view.findViewById(R.id.fabCheckIn);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedInfoUser = getActivity().getSharedPreferences("user_data", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedInfoUser.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        fabCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new ReadQRCodeFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}