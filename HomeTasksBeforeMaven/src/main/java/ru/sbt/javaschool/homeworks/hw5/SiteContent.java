package ru.sbt.javaschool.homeworks.hw5;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by скурихин on 19.08.2016.
 */
public class SiteContent {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String url;
        while (true) {
            try {
                url = bufferedReader.readLine();
                String result = readContent(url);
                System.out.println(result);
                break;
            } catch (MalformedURLException e) {
                System.out.println("Incorrect URL, try input again");
                continue;
            } catch (IOException e) {
                System.out.println("Error, try input again");
                continue;
            }
        }
    }

    public static String readContent(String arg) throws IOException {
        URL siteURL = new URL(arg);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(siteURL.openStream()));
        String string;
        while ((string = bufferedReader.readLine()) != null) {
            stringBuilder.append(string+"\n");
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }
}