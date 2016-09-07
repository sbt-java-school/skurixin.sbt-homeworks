package ru.sbt.javaschool.homeworks.hw6B_proxy;

/**
 * Created by скурихин on 31.08.2016.
 */
public interface CalculatorImpl {
    @Cache
    double sqrt(Number number);

    int factorial(int n);
}
