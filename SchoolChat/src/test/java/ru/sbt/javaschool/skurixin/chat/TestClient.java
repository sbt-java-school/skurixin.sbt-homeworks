package ru.sbt.javaschool.skurixin.chat;

import org.junit.Test;

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
}
