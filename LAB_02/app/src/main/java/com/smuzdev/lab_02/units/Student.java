package com.smuzdev.lab_02.units;

import android.util.Log;

import java.util.Comparator;

public class Student extends Person {
    Integer rate;
    Boolean isEmployee;

    public Student(String name, Integer age, Integer rate, Enum anEnum) {
        this.name = name;
        this.age = age;
        this.rate = rate;
        this.anEnum = anEnum;
    }

    @Override
    public String toString() {
        Log.i("Student", "Имя: " + name + " " + "Age: " + age + "Rate: " + rate);
        return super.toString();
    }

    @Override
    public int compareTo(Object object) {
        int rate = ((Student)object).rate;
        return rate-this.rate;
    }

    public static Comparator<Student> StudentAgeComporator = new Comparator<Student>() {
        @Override
        public int compare(Student student1, Student student2) {
            Integer student1Age = student1.age;
            Integer student2Age = student2.age;
            return student1Age.compareTo(student2Age);
        }
    };

}
