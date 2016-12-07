package ru.sbt.javaschool.homeworks.hw9_serialization;

import java.io.*;

/**
 * Created by скурихин on 07.09.2016.
 */
public class Main {

    private static final String FILE_NAME = ".\\HomeTasks1-11\\main\\java\\ru\\\\sbt\\javaschool\\homeworks\\hw9_serialization\\singleton";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        write(SingletonClass.singleton1);
        read();
    }

    private static void write(Object singleton) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);) {
            out.writeObject(singleton);
            System.out.println(SingletonClass.singleton1);
        }
    }

    private static void read() throws IOException, ClassNotFoundException {
        try (FileInputStream file = new FileInputStream(FILE_NAME);
             ObjectInputStream in = new ObjectInputStream(file)) {
            Object obj = in.readObject();
            System.out.println(obj);
        }
    }
}
