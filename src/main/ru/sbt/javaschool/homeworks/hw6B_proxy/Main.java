package ru.sbt.javaschool.homeworks.hw6B_proxy;

import com.sun.deploy.net.proxy.ProxyUtils;

import java.lang.reflect.Proxy;

/**
 * Created by скурихин on 31.08.2016.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Calculator calculator = new Calculator();
        CalculatorImpl instance = (CalculatorImpl)Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{CalculatorImpl.class}, new CacheProxy(calculator));
//        Thread.sleep(2000);
        instance.sqrt(10);
        instance.sqrt(12);
        instance.sqrt(10);
        instance.sqrt(12);
        instance.factorial(4);
    }
}
