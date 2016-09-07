package ru.sbt.javaschool.homeworks.hw6B_proxy;

/**
 * Created by скурихин on 31.08.2016.
 */
public class Calculator implements CalculatorImpl{
    @Override
    public double sqrt(Number number){
        if(number.doubleValue()<0){
            System.out.println("NOO");
            return -1;
        }
        else{
            return Math.sqrt(number.doubleValue());
        }
    }

    @Override
    public int factorial(int n) {
        if(n<0){
            System.out.println("n mustnt be smaller than 0");
            return -1;
        }
        if(n<2){
            return 1;
        }
        return n*factorial(n-1);
    }
}
