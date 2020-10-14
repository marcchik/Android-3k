package com.smuzdev.lab_04;

import java.io.Serializable;

public class Person implements Serializable {
    String name, surname, email, twitter, phone;

    public Person(String name, String surname, String email, String twitter, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.twitter = twitter;
        this.phone = phone;
    }

    public Person() {

    }

    @Override
    public String toString(){
        return "Person{ " +
                "Name: " + name + '\'' +
                "Surname: " + surname + '\'' +
                "Email: " + email + '\'' +
                "Twitter: " + twitter + '\'' +
                "Phone: " + phone + '\'';
    }
}
