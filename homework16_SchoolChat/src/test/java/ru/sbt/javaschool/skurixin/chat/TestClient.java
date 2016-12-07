package ru.sbt.javaschool.skurixin.chat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by скурихин on 08.10.2016.
 */
public class TestClient {
    @Test
    public void testOfInputMessages() throws InterruptedException {
        String string=new String("adsf>>dsaffsd>>  >>");
        String[] split = string.split(">>",2);
        for (String s : split) {
            System.out.println(s);
        }
    }

    @Test
    public void testOfSameLogins() throws InterruptedException {
        Map<String,String> map = new HashMap<>();
        map.put("serj123","sdaf");
        map.put("serj123[1]","sdaf");
        map.put("serj123[2]","sdaf");
        map.put("serj123[23]","sdaf");
        String stringOriginal="serj123";
        //Pattern compile = Pattern.compile("^1\\d+$");
        Pattern compile = Pattern.compile("^"+stringOriginal+"\\[\\d+\\]$");
        boolean anyMatch = map.entrySet().stream()
                .anyMatch(p -> p.getKey().equals(stringOriginal));
        System.out.println(anyMatch);
        if(anyMatch){
            long count1 = map.entrySet().stream()
                    .filter(p -> compile.matcher(p.getKey()).matches()).count();
            System.out.println(count1);
        }

    }
}
