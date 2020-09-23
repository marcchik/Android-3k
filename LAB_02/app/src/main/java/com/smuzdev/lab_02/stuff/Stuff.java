package com.smuzdev.lab_02.stuff;

import android.util.Log;

import com.smuzdev.lab_02.MainActivity;
import com.smuzdev.lab_02.educationManager.IAction;
import com.smuzdev.lab_02.educationManager.Manager;
import com.smuzdev.lab_02.exception.EduException;
import com.smuzdev.lab_02.units.Person;

import java.util.ArrayList;

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
                Log.i("Person", "One listener joined courses.");
            }
            else {
                throw new EduException("No free places for listeners");
            }
        }
    }

    public void printPersonsOnStuff() throws EduException {
        if (studentList!=null) {
            for (Person person:studentList) {
                person.toString();
            }
        }
        else {
            throw new EduException("There is no listeners at the moment.");
        }
    }

    public void sortStudentsByRate() {

    }

    public void sortStudentsByAge() {

    }
}
