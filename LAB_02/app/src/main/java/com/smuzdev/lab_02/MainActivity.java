package com.smuzdev.lab_02;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smuzdev.lab_02.R;
import com.smuzdev.lab_02.educationManager.Manager;
import com.smuzdev.lab_02.exception.EduException;
import com.smuzdev.lab_02.organization.Organizations;
import com.smuzdev.lab_02.stuff.Stuff;
import com.smuzdev.lab_02.units.Listener;
import com.smuzdev.lab_02.units.Person;
import com.smuzdev.lab_02.units.Student;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_WRITE = 1001;
    private boolean permissionGranted;
    ArrayList<Student> listeners;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeners = new ArrayList<Student>();
        listeners.add(new Student("Vladislav", 19, 3, Organizations.Organization.BELSTU));
        listeners.add(new Student("Mark", 19, 3, Organizations.Organization.BELSTU));
        listeners.add(new Student("Victoria", 18, 2, Organizations.Organization.BELSTU));
        listeners.add(new Student("Anton", 19, 1, Organizations.Organization.BELSTU));
        listeners.add(new Student("Denis", 20, 4, Organizations.Organization.BSUIR));
        listeners.add(new Student("Ruslan", 20, 5, Organizations.Organization.BSEU));

        Manager Oksana = new Manager("Oksana", 30, Organizations.Organization.ITechArt);
        Stuff SoftwareTestingStuff = new Stuff("Software testing stuff", Oksana, 15);

        for (int i = 0; i < 4; i++) {
            try {
                int randomListener = ThreadLocalRandom.current().nextInt(0, 7);
                SoftwareTestingStuff.addPersonToStuff(listeners.get(randomListener));
            }
            catch (EduException ex) {
                Log.i("Person", ex.getMessage());
            }
        }

        try {
            Log.i("Person", "ALL LISTENERS:" );
            SoftwareTestingStuff.printPersonsOnStuff();
        } catch (EduException e) {
            e.printStackTrace();
        }

        SoftwareTestingStuff.sortStudentsByRate();
        SoftwareTestingStuff.sortStudentsByAge();


    }

    public void onSerializeButtonClick(View view) {

        Gson gson = new Gson();
        if(!permissionGranted){
            checkPermissions();
            return;}
        File file = new File(Environment.getExternalStorageDirectory(), "Lab_2.txt");

        FileWriter fw = null;


        try {
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(gson.toJson(listeners));

            bw.close();
            fw.close();

        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isExternalStorageWriteable(){
        String state = Environment.getExternalStorageState();
        return  Environment.MEDIA_MOUNTED.equals(state);
    }

    public boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        return  (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    private boolean checkPermissions(){
        if(!isExternalStorageReadable() || !isExternalStorageWriteable()){
            Toast.makeText(this, "Внешнее хранилище не доступно", Toast.LENGTH_LONG).show();
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