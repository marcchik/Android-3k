package com.smuzdev.lab_03.auxiliary;

import android.content.IntentFilter;

public class Person {
    String firstName;
    String middleName;
    String lastName;
    String birthPlace;
    String birthDate;
    String university;
    Integer course;
    String specialization;

    public Person(String firstName, String middleName, String lastName, String birthPlace,
                  String birthDate, String university, Integer course, String specialization) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.university = university;
        this.course = course;
        this.specialization = specialization;
    }
}
