package com.smuzdev.lab_02.units;

public class Listener extends Person {

    public Listener(String name, Integer age, Enum anEnum) {
        this.name = name;
        this.age = age;
        this.anEnum = anEnum;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
