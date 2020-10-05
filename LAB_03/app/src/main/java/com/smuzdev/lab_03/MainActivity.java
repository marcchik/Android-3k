package com.smuzdev.lab_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

//        if (toBirthdayActivityButton.isEnabled() == false) {
//            toBirthdayActivityButton.getBackground().setAlpha(128);
//        } else {
//            toBirthdayActivityButton.getBackground().setAlpha(255);
//        }

        EditText[] editTexts = {firstNameEditText, middleNameEditText, lastNameEditText};
        CustomTextWatcher textWatcher = new CustomTextWatcher(editTexts, toBirthdayActivityButton);
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(textWatcher);
        }

        toBirthdayActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), BirthdayActivity.class);
                intent.putExtra("firstName", firstNameEditText.getText().toString());
                intent.putExtra("middleName", middleNameEditText.getText().toString());
                intent.putExtra("lastName", lastNameEditText.getText().toString());
                startActivity(intent);
            }
        });
    }

}