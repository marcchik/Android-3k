package com.smuzdev.lab_03.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VerificationActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_WRITE = 1001;
    private boolean permissionGranted;

    Button confirmButton;
    Button backToEducationActivityButton;
    TextView confirmFirstNameTextView;
    TextView confirmMiddleNameTextView;
    TextView confirmLastNameTextView;
    TextView confirmBirthPlaceTextView;
    TextView confirmBirthDateTextView;
    TextView confirmUniversityTextView;
    TextView confirmCourseTextView;
    TextView confirmSpecializationTextView;

    String firstName;
    String middleName;
    String lastName;
    String birthPlace;
    String birthDate;
    String university;
    Integer course;
    String specialization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

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

        Bundle bundle = getIntent().getBundleExtra("person");
        firstName = bundle.getString("firstName");
        middleName = bundle.getString("middleName");
        lastName = bundle.getString("lastName");
        birthPlace = bundle.getString("birthPlace");
        birthDate = bundle.getString("birthDate");
        university = bundle.getString("university");
        course = bundle.getInt("course");
        specialization = bundle.getString("specialization");

        confirmFirstNameTextView.setText("First name: " + firstName);
        confirmMiddleNameTextView.setText("Middle name: " + middleName);
        confirmLastNameTextView.setText("Last name: " + lastName);
        confirmBirthPlaceTextView.setText("Birth date: " + birthDate);
        confirmBirthDateTextView.setText("Birth place: " + birthPlace);
        confirmUniversityTextView.setText("University : " + university);
        confirmCourseTextView.setText("Course: " + course);
        confirmSpecializationTextView.setText("Specialization: " + specialization);

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

                if (!permissionGranted) {
                    checkPermissions();
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

    public boolean isExternalStorageWriteable() {
        String state = Environment.getExternalStorageState();
        return  Environment.MEDIA_MOUNTED.equals(state);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return  (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    private boolean checkPermissions() {
        if(!isExternalStorageReadable() || !isExternalStorageWriteable()){
            Toast.makeText(this, "External storage not available.", Toast.LENGTH_LONG).show();
            return false;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "Permissions received", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permissions must be granted", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}