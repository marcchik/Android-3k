package com.smuzdev.lab_03.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smuzdev.lab_03.auxiliary.CustomTextWatcher;
import com.smuzdev.lab_03.R;

public class MainActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText middleNameEditText;
    EditText lastNameEditText;
    Button toBirthdayActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        middleNameEditText = findViewById(R.id.middleNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        toBirthdayActivityButton = findViewById(R.id.toBirthdayActivityButton);
        toBirthdayActivityButton.setEnabled(false);
        toBirthdayActivityButton.getBackground().setAlpha(128);

        EditText[] editTexts = {firstNameEditText, middleNameEditText, lastNameEditText};
        CustomTextWatcher textWatcher = new CustomTextWatcher(editTexts, toBirthdayActivityButton);
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(textWatcher);
        }

        toBirthdayActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), BirthdayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("firstName", firstNameEditText.getText().toString());
                bundle.putString("middleName", middleNameEditText.getText().toString());
                bundle.putString("lastName", lastNameEditText.getText().toString());
                intent.putExtra("person", bundle);
                startActivity(intent);
            }
        });
    }

}