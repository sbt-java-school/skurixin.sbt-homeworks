package ru.sbt.javaschool.homeworks.hw11_lambda.stream_without_pipeline;

import java.time.LocalDate;
import java.time.Period;

/**
 * Created by скурихин on 13.09.2016.
 */
public class Person {
    public enum Sex {
        Male, Female
    }

    private String name;
    private LocalDate birthday;
    private int age;
    private Sex gender;

    public Person(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, LocalDate birthday, Sex gender) {

        this.name = name;

        this.birthday = birthday;
        this.gender = gender;
    }


    public Sex getGender() {
        return gender;
    }

    public String getName() {

        return name;
    }

    public Person(int age) {
        this.age = age;
    }

    public int getAge() {
        if (birthday != null) {
            return Period.between(LocalDate.now(), birthday).getYears();
        } else {
            return age;

        }
    }

    @Override
    public String toString() {
        return "[" + name + " " + getAge() + " " + gender + " " + "]";
    }
}
