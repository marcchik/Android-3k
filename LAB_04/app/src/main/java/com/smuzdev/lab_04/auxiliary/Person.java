package com.smuzdev.lab_04.auxiliary;

import java.io.Serializable;

public class Person implements Serializable {
    public String name, surname, email, twitter, phone, pathToAvatar;;

    public Person(String name, String surname, String email, String twitter, String phone, String pathToAvatar) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.twitter = twitter;
        this.phone = phone;
        this.pathToAvatar = pathToAvatar;
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
                "Phone: " + phone + '\'' + "}";
    }
}
