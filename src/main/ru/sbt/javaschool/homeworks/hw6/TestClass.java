package ru.sbt.javaschool.homeworks.hw6;

/**
 * Created by скурихин on 24.08.2016.
 */
public class TestClass extends ParentClass{
    public static final String MONTH = "MONTH";
    private static final String NAME = "NAME";
    public static final String ADDRESS = "MOSCOW";
    private static final String ZODAIC = "LION";
    public static final int COUNT = 3;
    public void TestPublicMethod(){
        System.out.println("TestPublicMethod");
    }

    private void TestPrivateMethod(){
        System.out.println("TestPrivateMethod");
    }
}
