package com.smuzdev.lab_03.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smuzdev.lab_03.R;
import com.smuzdev.lab_03.auxiliary.Person;
import com.smuzdev.lab_03.auxiliary.RequestPermissions;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VerificationActivity extends AppCompatActivity {

    //region Initialization
    Button confirmButton, backToEducationActivityButton;
    TextView confirmFirstNameTextView, confirmMiddleNameTextView, confirmLastNameTextView,
            confirmBirthPlaceTextView, confirmBirthDateTextView, confirmUniversityTextView,
            confirmCourseTextView, confirmSpecializationTextView;
    String firstName, middleName, lastName, birthPlace, birthDate, university, specialization;
    Integer course;
    Context context = this;
    Activity activity = this;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        //region findViewById()
        confirmFirstNameTextView = findViewById(R.id.confirmFirstNameTextView);
        confirmMiddleNameTextView = findViewById(R.id.confirmMiddleNameTextView);
        confirmLastNameTextView = findViewById(R.id.confirmLastNameTextView);
        confirmBirthPlaceTextView = findViewById(R.id.confirmBirthPlaceTextView);
        confirmBirthDateTextView = findViewById(R.id.confirmBirthDateTextView);
        confirmUniversityTextView = findViewById(R.id.confirmUniversityTextView);
        confirmCourseTextView = findViewById(R.id.confirmCourseTextView);
        confirmSpecializationTextView = findViewById(R.id.confirmSpecializationTextView);
        confirmButton = findViewById(R.id.confirmButton);
        backToEducationActivityButton = findViewById(R.id.backToEducationActivityButton);
        //endregion()

        //region Bundle
        Bundle bundle = getIntent().getBundleExtra("person");
        firstName = bundle.getString("firstName");
        middleName = bundle.getString("middleName");
        lastName = bundle.getString("lastName");
        birthPlace = bundle.getString("birthPlace");
        birthDate = bundle.getString("birthDate");
        university = bundle.getString("university");
        course = bundle.getInt("course");
        specialization = bundle.getString("specialization");
        //endregion

        //region setText()
        confirmFirstNameTextView.setText("First name: " + firstName);
        confirmMiddleNameTextView.setText("Middle name: " + middleName);
        confirmLastNameTextView.setText("Last name: " + lastName);
        confirmBirthPlaceTextView.setText("Birth date: " + birthDate);
        confirmBirthDateTextView.setText("Birth place: " + birthPlace);
        confirmUniversityTextView.setText("University : " + university);
        confirmCourseTextView.setText("Course: " + course);
        confirmSpecializationTextView.setText("Specialization: " + specialization);
        //endregion

        Log.d("Person", "Person: " + firstName + ", " + middleName + ", "
                + ", " + lastName + ", " + birthDate + ", " + birthDate + ", " + university + ", " + course + ", " + specialization);

        backToEducationActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), EducationActivity.class);
                startActivity(intent);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RequestPermissions requestPermission = new RequestPermissions();

                if(!requestPermission.permissionGranted) {
                    requestPermission.checkPermissions(context, activity);
                }

                GsonBuilder builder = new GsonBuilder();
                Gson gson = new Gson();
                Person person = new Person(firstName, middleName, lastName, birthPlace,
                        birthDate, university, course, specialization);

                File file = new File(Environment.getExternalStorageDirectory(), "Lab_3.txt");
                FileWriter fw = null;

                try {
                    fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(gson.toJson(person));

                    bw.close();
                    fw.close();

                    Toast.makeText(getApplicationContext(), "Registration comleted", Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}