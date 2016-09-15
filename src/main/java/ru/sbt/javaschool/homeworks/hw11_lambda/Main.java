package ru.sbt.javaschool.homeworks.hw11_lambda;

import java.util.*;

/**
 * Created by скурихин on 13.09.2016.
 */
public class Main {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Bob"),
                new Person("Bill", 3),
                new Person("Bill", 18),
                new Person("Robe")
        );

//        //filters
//        Streams.of(persons)
//                .filter(p -> p.getName().equals("Bill"))
//                .filter(p -> p.getAge() == 0)
//                .forEach(System.out::println);
//
//        //toMap
//        Map<Person, Integer> personIntegerMap = Streams.of(persons)
//                .toMap(p -> p, p -> p.getAge());
//        for (Map.Entry<Person, Integer> personIntegerEntry : personIntegerMap.entrySet()) {
//            System.out.print(personIntegerEntry.getKey());
//            System.out.println(" " + personIntegerEntry.getValue());
//        }

    }


}
