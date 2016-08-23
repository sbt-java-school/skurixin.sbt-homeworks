package ru.sbt.javaschool.homeworks.hw6;

import java.lang.reflect.Method;

/**
 * Created by скурихин on 24.08.2016.
 */
public class Aplication {
    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        printAllMethods(testClass.getClass());
        printAllGetters(testClass.getClass());
        checkConstants(testClass.getClass());
    }

    private static void checkConstants(Class<? extends TestClass> aClass) {
        //добавить проверку
    }

    private static void printAllGetters(Class<? extends TestClass> aClass) {
        System.out.println("All getters of a class " + aClass.getName());
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if(method.getName().contains("get")){
                System.out.println(method);
            }
        }
    }

    private static void printAllMethods(Class<? extends TestClass> aClass) {
        System.out.println("All methods of a class " + aClass.getName());
        Class<?> aClass1 = aClass;
        while (aClass1 != null) {
            Method[] aClassMethods = aClass1.getDeclaredMethods();
            for (Method aClassMethod : aClassMethods) {
                System.out.println(aClassMethod);
            }
            aClass1 = aClass1.getSuperclass();
        }
    }

}
