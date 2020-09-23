package com.smuzdev.lab_02;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.smuzdev.lab_02.educationManager.Manager;
import com.smuzdev.lab_02.exception.EduException;
import com.smuzdev.lab_02.organization.Organizations;
import com.smuzdev.lab_02.stuff.Stuff;
import com.smuzdev.lab_02.units.Listener;
import com.smuzdev.lab_02.units.Person;
import com.smuzdev.lab_02.units.Student;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Student> listeners = new ArrayList<Student>();
        listeners.add(new Student("Vladislav", 19, 3, Organizations.Organization.BELSTU));
        listeners.add(new Student("Mark", 19, 3, Organizations.Organization.BELSTU));
        listeners.add(new Student("Victoria", 18, 2, Organizations.Organization.BELSTU));
        listeners.add(new Student("Anton", 19, 1, Organizations.Organization.BELSTU));
        listeners.add(new Student("Denis", 20, 4, Organizations.Organization.BSUIR));
        listeners.add(new Student("Ruslan", 20, 5, Organizations.Organization.BSEU));

        Manager Oksana = new Manager("Bob", 30, Organizations.Organization.ITechArt);
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
    }

}