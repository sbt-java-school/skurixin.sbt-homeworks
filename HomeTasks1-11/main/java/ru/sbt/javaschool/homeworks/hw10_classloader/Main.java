package ru.sbt.javaschool.homeworks.hw10_classloader;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by скурихин on 14.09.2016.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
//        PluginManager manager = new PluginManager("file:///D:/root_for_java_classes/");
//        PluginManager manager = new PluginManager("file:///./root_for_java_classes/");
//        PluginManager manager = new PluginManager("file:///root_for_java_classes/");

        String path = new File(".").getAbsolutePath().replaceAll("\\\\", "/");
        PluginManager manager = new PluginManager("file:///" + path.substring(0, path.length() - 1) + "root_for_java_classes/");
        Object obj = manager.load("pluginImpl", "ru.sbt.javaschool.homeworks.hw10_classloader.PluginImpl");
        System.out.println(obj);
        //ошибка
        //Plugin plugin = (Plugin) obj;

        //не получается сделать приведение типов
//        Plugin obj =(Plugin) manager.load("pluginImpl", "ru.sbt.javaschool.homeworks.hw10_classloader.PluginImpl");
    }
}
