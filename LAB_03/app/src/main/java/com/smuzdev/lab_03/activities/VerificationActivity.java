package com.smuzdev.lab_03.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smuzdev.lab_03.R;

public class VerificationActivity extends AppCompatActivity {

    Button confirmButton;
    Button backToEducationActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        confirmButton = findViewById(R.id.confirmButton);
        backToEducationActivityButton = findViewById(R.id.backToEducationActivityButton);

        backToEducationActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), EducationActivity.class);
                startActivity(intent);
            }
        });
    }
}