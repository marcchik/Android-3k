package com.smuzdev.lab_03.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.smuzdev.lab_03.auxiliary.CustomTextWatcher;
import com.smuzdev.lab_03.auxiliary.DatePickerFragment;
import com.smuzdev.lab_03.R;

import java.text.DateFormat;
import java.util.Calendar;

public class BirthdayActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button backToMainActivityButton;
    Button toEducationActivityButton;
    Button selectDateButton;
    EditText birthPlaceEditText;
    TextView birthDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        birthDateTextView = findViewById(R.id.birthDateTextView);
        birthPlaceEditText = findViewById(R.id.birthPlaceEditText);
        backToMainActivityButton = findViewById(R.id.BackToMainActivityButton);
        toEducationActivityButton = findViewById(R.id.toEducationActivityButton);
        selectDateButton = findViewById(R.id.selectDateButton);
        toEducationActivityButton.setEnabled(false);
        toEducationActivityButton.getBackground().setAlpha(128);

        EditText[] editTexts = {birthPlaceEditText};
        CustomTextWatcher textWatcher = new CustomTextWatcher(editTexts, toEducationActivityButton);
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(textWatcher);
        }

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        toEducationActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), EducationActivity.class);
                Bundle bundle = getIntent().getBundleExtra("person");
                bundle.putString("birthPlace", birthPlaceEditText.getText().toString());
                bundle.putString("birthDate", birthDateTextView.getText().toString());
                intent.putExtra("person", bundle);
                startActivity(intent);
            }
        });

        backToMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(calendar.getTime());

        TextView selectedDateTextView = findViewById(R.id.birthDateTextView);
        selectedDateTextView.setText(currentDateString);
    }
}