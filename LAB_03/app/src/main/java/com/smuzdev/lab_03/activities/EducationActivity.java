package com.smuzdev.lab_03.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smuzdev.lab_03.R;
import com.smuzdev.lab_03.auxiliary.CustomTextWatcher;

public class EducationActivity extends AppCompatActivity {

    Button toVerificationActivityButton;
    Button backToBirthdayActivityButton;
    EditText universityEditText;
    EditText courseEditText;
    EditText specializationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        universityEditText = findViewById(R.id.universityEditText);
        courseEditText = findViewById(R.id.courseEditText);
        specializationEditText = findViewById(R.id.specializationEditText);
        toVerificationActivityButton = findViewById(R.id.toVerificationActivityButton);
        backToBirthdayActivityButton = findViewById(R.id.backToBirthdayActivityButton);
        toVerificationActivityButton.setEnabled(false);
        toVerificationActivityButton.getBackground().setAlpha(128);

        EditText[] editTexts = {universityEditText, courseEditText, specializationEditText};
        CustomTextWatcher textWatcher = new CustomTextWatcher(editTexts, toVerificationActivityButton);
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(textWatcher);
        }

        toVerificationActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), VerificationActivity.class);
                startActivity(intent);
            }
        });

        backToBirthdayActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), BirthdayActivity.class);
                startActivity(intent);
            }
        });
    }
}