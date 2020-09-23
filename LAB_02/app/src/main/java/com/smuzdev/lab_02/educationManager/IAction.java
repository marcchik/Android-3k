package com.smuzdev.lab_02.educationManager;

import com.smuzdev.lab_02.exception.EduException;
import com.smuzdev.lab_02.units.Person;

public interface IAction {
    void addPersonToStuff(Person person) throws EduException;
    void printPersonsOnStuff() throws  EduException;
    void sortStudentsByRate();
    void sortStudentsByAge();
}
