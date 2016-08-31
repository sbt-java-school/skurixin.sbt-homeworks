package ru.sbt.javaschool.homeworks.hw6B_proxy;

/**
 * Created by скурихин on 31.08.2016.
 */
public class Calculator implements CalculatorImpl{
    public double sqrt(Number number){
        if(number.doubleValue()<0){
            System.out.println("NOO");
            return -1;
        }
        else{
            return Math.sqrt(number.doubleValue());
        }
    }
}
