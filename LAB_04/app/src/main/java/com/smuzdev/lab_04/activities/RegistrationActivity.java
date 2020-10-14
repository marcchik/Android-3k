package com.smuzdev.lab_04.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.smuzdev.lab_04.R;

public class RegistrationActivity extends AppCompatActivity {

    Button addButton;
    TextInputLayout inputName, inputSurname, inputEmail, inputTwitter, inputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setTitle("Registration");

        addButton = findViewById(R.id.addButton);
        inputName = findViewById(R.id.inputName);
        inputSurname = findViewById(R.id.inputSurname);
        inputEmail = findViewById(R.id.inputEmail);
        inputTwitter = findViewById(R.id.inputTwitter);
        inputPhone = findViewById(R.id.inputPhone);


    }
}