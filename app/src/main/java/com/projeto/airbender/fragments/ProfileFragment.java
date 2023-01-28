package com.projeto.airbender.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.projeto.airbender.R;
import com.projeto.airbender.activities.LoginActivity;
import com.projeto.airbender.listeners.LoginListener;
import com.projeto.airbender.models.SingletonAirbender;
import com.projeto.airbender.utils.DBHelper;

import java.util.Map;

public class ProfileFragment extends Fragment implements LoginListener {

    private Button btnLogout;
    private TextView tvUsername, tvFname, tvSurname, tvNIF, tvPhone, tvBalance, tvEditProfile;
    private SwipeRefreshLayout pullToRefresh;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        fillUser(view);
        SingletonAirbender.getInstance(getContext()).setLoginListener(this);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SingletonAirbender.getInstance(getContext()).getUserData(getContext());
                pullToRefresh.setRefreshing(false);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedInfoUser = getActivity().getSharedPreferences("user_data", getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedInfoUser.edit();
                editor.clear();
                editor.apply();

                DBHelper dbHelper = new DBHelper(getContext());
                dbHelper.deleteAllDB("tickets");
                dbHelper.deleteAllDB("balanceReq");
                dbHelper.deleteAllDB("airports");
                dbHelper.deleteAllDB("flights");
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                );

                fragmentTransaction.replace(R.id.frameLayout, new EditProfileFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;

    }
    private void fillUser(View view){
        btnLogout = view.findViewById(R.id.btnLogout);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvFname = view.findViewById(R.id.tvFname);
        tvSurname = view.findViewById(R.id.tvSurname);
        tvNIF = view.findViewById(R.id.tvNIF);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvBalance = view.findViewById(R.id.tvBalance);
        tvEditProfile = view.findViewById(R.id.tvEditProfile);
        pullToRefresh = view.findViewById(R.id.pullToRefresh);

        SharedPreferences sharedInfoUser = getActivity().getSharedPreferences("user_data", getContext().MODE_PRIVATE);
        tvUsername.setText(sharedInfoUser.getString("USERNAME", ""));
        tvFname.setText(sharedInfoUser.getString("FNAME", ""));
        tvSurname.setText(sharedInfoUser.getString("SURNAME", ""));
        tvNIF.setText(sharedInfoUser.getString("NIF", ""));
        tvPhone.setText(sharedInfoUser.getString("PHONE", ""));
        tvBalance.setText(String.format("%.2f€", sharedInfoUser.getFloat("BALANCE", 0)));
    }

    @Override
    public void onLogin(Map<String, String> map) {
    }

    @Override
    public void updateUserData(Map<String, String> map) {
        // save on shared prefenrences
        SharedPreferences sharedInfoUser = getActivity().getSharedPreferences("user_data", getActivity().MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedInfoUser.edit();
        editor.putString("FNAME", map.get("fName"));
        editor.putString("USERNAME", map.get("username"));
        editor.putString("SURNAME", map.get("surname"));
        editor.putString("PHONE", map.get("phone"));
        editor.putString("NIF", map.get("nif"));
        editor.putFloat("BALANCE", Float.parseFloat(map.get("balance")));
        editor.apply();
    }
}