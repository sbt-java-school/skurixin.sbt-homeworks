package ru.sbt.javaschool.homeworks.hw11_lambda.stream_without_pipeline;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by скурихин on 13.09.2016.
 */
public class MainStream {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Bob"),
                new Person("Bill", 3),
                new Person("Bill", 18),
                new Person("Robe")
        );

//        //filters
        Streams.of(persons)
                .filter(p -> p.getName().equals("Bill"))
                .filter(p -> p.getAge() == 0)
                .forEach(System.out::println);

//        //toMap
        Map<Integer, Person> integerPersonMap = Streams.of(persons)
                .toMap(p -> p.getAge(), p -> p);
        for (Map.Entry<Integer, Person> personIntegerEntry : integerPersonMap.entrySet()) {
            System.out.print(personIntegerEntry.getKey());
            System.out.println(" " + personIntegerEntry.getValue());
        }
        System.out.println("***********");

        Map<Person, Integer> personIntegerMap = Streams.of(persons)
                .toMap(p -> p, p -> p.getAge());
        for (Map.Entry<Person, Integer> personIntegerEntry : personIntegerMap.entrySet()) {
            System.out.print(personIntegerEntry.getKey());
            System.out.println(" " + personIntegerEntry.getValue());
        }

        Streams.of(persons)
                .transform(person -> new Person(person.getName(), person.getAge() + 18))
                .forEach(System.out::println);
        Stream.of(persons);
    }


}
