package com.smuzdev.lab_04.auxiliary;

import android.util.Log;

import java.util.ArrayList;


public class Users {
    String TAG = "LAB_04";
    public ArrayList<Person> personArrayList = new ArrayList<Person>();

    public void addPerson(Person person) {
        personArrayList.add(person);
    }

    public void removePerson(Person person) {
        personArrayList.remove(person);
    }

    public void printPersonList() {
        for (Person person : personArrayList) {
            Log.d(TAG, person.toString());
        }
    }
}
