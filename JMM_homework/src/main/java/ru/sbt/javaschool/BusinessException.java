package ru.sbt.javaschool;

/**
 * Created by скурихин on 30.09.2016.
 */
public class BusinessException extends Exception{
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
