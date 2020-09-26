package com.smuzdev.lab_02.educationManager;

import android.util.Log;

import com.smuzdev.lab_02.units.Person;

public class Manager extends Person {
    Enum organization;

    public Manager(String name, Integer age , Enum organization) {
        this.name = name;
        this.age = age;
        this.organization = organization;
        Log.i("Person", "New manager " + name + " has been created.");
    }
    @Override

    public int compareTo(Object o) {
        return 0;
    }
}
