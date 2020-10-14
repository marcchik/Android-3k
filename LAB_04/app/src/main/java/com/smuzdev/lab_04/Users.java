package com.smuzdev.lab_04;

import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Users {
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
