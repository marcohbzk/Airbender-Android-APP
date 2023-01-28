package com.projeto.airbender.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.projeto.airbender.R;
import com.projeto.airbender.activities.MainActivity;
import com.projeto.airbender.models.SingletonAirbender;

public class EditProfileFragment extends Fragment {
    private EditText etFname, etSurname, etNIF, etPhone;
    private Button btnSaveUser;
    private String fname, surname, nif, phone;
    private FloatingActionButton fabBack;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        loadAttributes(view);

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()) {
                    SingletonAirbender.getInstance(getContext()).updateUser(getContext(), fname, surname, nif, phone);
                    getActivity().onBackPressed();
                }
            }
        });
        return view;
    }

    private boolean validateFields() {
        fname = etFname.getText().toString().trim();
        surname = etSurname.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        nif = etNIF.getText().toString().trim();
        if (fname.isEmpty()) {
            etFname.setError("Please fill in this field");
            return false;
        }
        if (surname.isEmpty()) {
            etSurname.setError("Please fill in this field");
            return false;
        }
        if (nif.isEmpty()) {
            etNIF.setError("Please fill in this field");
            return false;
        }
        if (phone.isEmpty()) {
            etPhone.setError("Please fill in this field");
            return false;
        }
        if (phone.length() != 9) {
            etPhone.setError("Needs to be of length 9!");
            return false;
        }
        if (nif.length() != 9) {
            etNIF.setError("Needs to be of length 9!");
            return false;
        }
        if (!isInt(nif)){
            etNIF.setError("Needs to be all numbers!");
            return false;
        }
        if (!isInt(phone)){
            etPhone.setError("Needs to be all numbers!");
            return false;
        }
        return true;
    }

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    private void loadAttributes(View view) {
        etFname = view.findViewById(R.id.etFname);
        etSurname = view.findViewById(R.id.etSurname);
        etNIF = view.findViewById(R.id.etNIF);
        etPhone = view.findViewById(R.id.etPhone);
        btnSaveUser = view.findViewById(R.id.btnSaveUser);
        fabBack = view.findViewById(R.id.fabBack);

        SharedPreferences sharedInfoUser = getActivity().getSharedPreferences("user_data", getContext().MODE_PRIVATE);
        etFname.setText(sharedInfoUser.getString("FNAME", ""));
        etSurname.setText(sharedInfoUser.getString("SURNAME", ""));
        etNIF.setText(sharedInfoUser.getString("NIF", ""));
        etPhone.setText(sharedInfoUser.getString("PHONE", ""));
    }

}