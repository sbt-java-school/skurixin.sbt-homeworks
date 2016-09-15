package ru.sbt.javaschool.homeworks.hw11_lambda;

import java.util.Collection;

/**
 * Created by скурихин on 13.09.2016.
 */
public class Streams {
    private static Collection collection;
    private static Streams MyStream = new Streams();
    private Streams(){

    }

    public static void of(Collection newCollection) {
        collection = newCollection;
    }
//    public Collection filter() {
//
//    }
//
//    public Collection map() {
//
//    }
}
