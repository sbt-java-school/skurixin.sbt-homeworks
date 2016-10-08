package ru.sbt.javaschool.skurixin.chat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by скурихин on 08.10.2016.
 */
public class TestClient {
    @Test
    public void test1() throws InterruptedException {
        String string=new String("adsf>>dsaffsd>>  >>");
        String[] split = string.split(">>",2);
        for (String s : split) {
            System.out.println(s);
        }
    }
    @Test
    public void test2() throws InterruptedException {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(new String[]{"sdfad", "asdf"}));
        System.out.println(arrayList.toString());
    }
}
