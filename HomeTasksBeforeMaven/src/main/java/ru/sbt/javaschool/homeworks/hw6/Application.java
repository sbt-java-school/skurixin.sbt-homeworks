package ru.sbt.javaschool.homeworks.hw6;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by скурихин on 24.08.2016.
 */
public class Application {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        TestClass testClass = new TestClass();
//        printAllMethods(testClass.getClass());
//        printAllGetters(testClass.getClass());
        checkConstants(testClass);
    }

    private static void checkConstants(TestClass object) throws IllegalAccessException, NoSuchFieldException {
        Class<? extends TestClass> aClass = object.getClass();
        System.out.println("Check all Constants of class " + aClass.getName());
        int aClassModifiers = aClass.getModifiers();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            int declaredFieldModifiers = declaredField.getModifiers();
            declaredField.setAccessible(true);
            if (Modifier.isFinal(declaredFieldModifiers) && Modifier.isStatic(declaredFieldModifiers)
                    && declaredField.getType().getName().equals(String.class.getName())) {
                if (!declaredField.getName().equals(declaredField.get(object))) {
                    System.out.println("Name of field " + declaredField.getName() + "hasnt coincided with value");
                }
            }
        }
    }

    private static void printAllGetters(Class<? extends TestClass> aClass) {
        System.out.println("All getters of a class " + aClass.getName());
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.getName().contains("get")) {
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
