package com.smuzdev.lab_02.stuff;

import android.util.Log;

import com.smuzdev.lab_02.educationManager.IAction;
import com.smuzdev.lab_02.educationManager.Manager;
import com.smuzdev.lab_02.exception.EduException;
import com.smuzdev.lab_02.units.Person;
import com.smuzdev.lab_02.units.Student;

import java.util.ArrayList;
import java.util.Collections;

public class Stuff implements IAction {
    String name;
    Manager manager;
    Integer maxQuantityStudents;
    public ArrayList<Person> studentList = new ArrayList<Person>();

    public Stuff(String name, Manager manager, Integer maxQuantityStudents) {
        this.name = name;
        this.manager = manager;
        this.maxQuantityStudents = maxQuantityStudents;
    }

    public void addPersonToStuff(Person person) throws EduException {
        if(!studentList.contains(person)) {
            if(studentList.size()<maxQuantityStudents){
                studentList.add(person);
                Log.i("Person", "Listener " + person.name + " joined courses.");
            }
            else {
                throw new EduException("No free places for listeners");
            }
        }
    }

    public void printPersonsOnStuff() throws EduException {
        if (studentList!=null) {
            for (Person person:studentList) {
                Log.i("Person", person.toString());
            }
        }
        else {
            throw new EduException("There is no listeners at the moment.");
        }
    }

    public void sortStudentsByRate() {
        if(!studentList.isEmpty()) {
            Collections.sort(studentList, Collections.reverseOrder());
            Log.i("Person" ,"SORTING LISTENERS BY RATE:");

            for (Person person:studentList) {
                person.toString();
            }
        }
    }

    public void sortStudentsByAge() {
        Collections.sort(studentList, Student.StudentAgeComparator);
        Log.i("Person" ,"SORTING LISTENERS BY AGE:");

        for (Person person:studentList) {
            person.toString();
        }
    }
}
