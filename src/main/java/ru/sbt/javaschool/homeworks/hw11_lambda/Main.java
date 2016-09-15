package ru.sbt.javaschool.homeworks.hw11_lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by скурихин on 13.09.2016.
 */
public class Main {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Bob"),
                new Person("Bill"),
                new Person("Robe")
        );

//        persons.stream().filter(p -> p.getName().equals("Bob"))
//                .forEach(p -> System.out.println(p.getName()));

//        Streams.of(persons).filter((Person p) ->p.getName().equals("Bob"));
//        Streams.of(persons).filter(p->p)
//        Streams.of(persons).forEach(System.out::print);
        Streams.of(persons);
    }


}
