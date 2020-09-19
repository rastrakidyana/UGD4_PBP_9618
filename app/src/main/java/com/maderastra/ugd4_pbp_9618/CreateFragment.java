package com.maderastra.ugd4_pbp_9618;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.maderastra.ugd4_pbp_9618.Database.DatabaseClient;
import com.maderastra.ugd4_pbp_9618.Model.User;

public class CreateFragment extends Fragment {


    TextInputEditText editName, editNumber, editAge;
    Button addBtn, cancelBtn;
    TextInputLayout txtName, txtAge, txtNumber;

    public CreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        editNumber = view.findViewById(R.id.input_number);
        editName = view.findViewById(R.id.input_name);
        editAge = view.findViewById(R.id.input_age);
        addBtn = view.findViewById(R.id.btn_add);
        cancelBtn = view.findViewById(R.id.btn_cancel);
        txtNumber = view.findViewById(R.id.input_number_layout);
        txtName = view.findViewById(R.id.input_name_layout);
        txtAge = view.findViewById(R.id.input_age_layout);

        return view;
    }

    private boolean validateNumber() {
        try{
            Integer.parseInt(editNumber.getText().toString());
            if (!editNumber.getText().toString().equals("")) {
                txtNumber.setError(null);
                return true;
            } else {
                txtNumber.setError("Number can't be empty");
                return false;
            }
        } catch (NumberFormatException ex) {
            if (!editNumber.getText().toString().equals("")) {
                txtNumber.setError("Number must be Int");
            } else {
                txtNumber.setError("Number can't be empty");
            }
            return false;
        }
    }

    private boolean validateName() {

            if (!editName.getText().toString().equals("")) {
                txtName.setError(null);
                return true;

        } else {
            txtName.setError("Name can't be empty");
            return false;
        }
    }

    private boolean validateAge() {
        try{
            Integer.parseInt(editAge.getText().toString());
            if (!editAge.getText().toString().equals("")) {
                txtAge.setError(null);
                return true;
            } else {
                txtAge.setError("Age can't be empty");
                return false;
            }
        } catch (NumberFormatException ex){
            if (!editAge.getText().toString().equals("")) {
                txtAge.setError("Age must be Int");
            } else {
                txtAge.setError("Age can't be empty");
            }
            return false;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateName() & validateAge() & validateNumber()){
                    addUser();
                }
            }
        });

        cancelBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(CreateFragment.this).commit();
            }
        });
    }

    private void addUser(){
        final int number = Integer.parseInt(editNumber.getText().toString());
        final String name = editName.getText().toString();
        final int age = Integer.parseInt(editAge.getText().toString());

        class AddUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "User saved", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(CreateFragment.this).commit();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.setNumberU(number);
                user.setNameU(name);
                user.setAgeU(age);

                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .userDAO()
                        .insert(user);
                return null;
            }
        }

        AddUser add = new AddUser();
        add.execute();
    }


}