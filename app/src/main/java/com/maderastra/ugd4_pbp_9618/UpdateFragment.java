package com.maderastra.ugd4_pbp_9618;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class UpdateFragment extends Fragment {


    TextInputEditText editName, editNumber, editAge;
    Button saveBtn, deleteBtn, cancelBtn;
    User user;
    TextInputLayout txtName, txtAge, txtNumber;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        user = (User) getArguments().getSerializable("user");
        editNumber = view.findViewById(R.id.input_number);
        editName = view.findViewById(R.id.input_name);
        editAge = view.findViewById(R.id.input_age);
        saveBtn = view.findViewById(R.id.btn_update);
        deleteBtn = view.findViewById(R.id.btn_delete);
        cancelBtn = view.findViewById(R.id.btn_cancel);
        txtNumber = view.findViewById(R.id.input_number_layout);
        txtName = view.findViewById(R.id.input_name_layout);
        txtAge = view.findViewById(R.id.input_age_layout);

        editNumber.setText(Integer.toString(user.getNumberU()));
        editName.setText(user.getNameU());
        editAge.setText(Integer.toString(user.getAgeU()));

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
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateName() & validateAge() & validateNumber()){
                    user.setNumberU(Integer.parseInt(editNumber.getText().toString()));
                    user.setNameU(editName.getText().toString());
                    user.setAgeU(Integer.parseInt(editAge.getText().toString()));
                    update(user);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure to delete ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete(user);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateFragment.this).commit();
            }
        });
    }

    private void update(final User user){
        class UpdateUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .userDAO()
                        .update(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "User updated", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateFragment.this).commit();
            }
        }

        UpdateUser update = new UpdateUser();
        update.execute();
    }

    private void delete(final User user) {
        class DeleteUser extends AsyncTask<Void, Void, Void>{

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "User deleted", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateFragment.this).commit();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .userDAO()
                        .delete(user);
                return null;
            }
        }

        DeleteUser delete = new DeleteUser();
        delete.execute();
    }

}