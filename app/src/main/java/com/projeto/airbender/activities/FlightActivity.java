package com.projeto.airbender.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.projeto.airbender.R;
import com.projeto.airbender.fragments.SelectAirportFragment;

public class FlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        replaceFragment(new SelectAirportFragment(), false);
    }
    public void replaceFragment(Fragment newFragment, boolean keepBack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, newFragment);

        if (keepBack)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
}