package com.smuzdev.lab_02.units;

public abstract class Person implements Comparable {
    public String name;
    public Integer age;
    Enum anEnum;

    public Person(String name, Integer age, Enum anEnum) {
        this.name = name;
        this.age = age;
        this.anEnum = anEnum;
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {

    }
}

