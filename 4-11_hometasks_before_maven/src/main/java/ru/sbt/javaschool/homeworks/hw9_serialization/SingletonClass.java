package ru.sbt.javaschool.homeworks.hw9_serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by скурихин on 08.09.2016.
 */
public class SingletonClass implements Serializable {
    public static SingletonClass singleton1 = new SingletonClass(1);
    public static SingletonClass singleton2 = new SingletonClass(2);
    private int value;

    private SingletonClass(int value) {
        this.value = value;
        System.out.println("Created Singleton " + value);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        System.out.println("writeObject");
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        System.out.println("readObject");
    }

    private Object writeReplace() throws Exception {
        System.out.println("writeReplace");
        if (value == singleton1.value) {
            return singleton1;
        }
        if (value == singleton2.value) {
            return singleton2;
        }
        throw new Exception("Incorrect value");
    }

    private Object readResolve() throws Exception {
        System.out.println("readResolve");
        if (value == singleton1.value) {
            return singleton1;
        }
        if (value == singleton2.value) {
            return singleton2;
        }
        throw new Exception("Incorrect value");
    }
}
