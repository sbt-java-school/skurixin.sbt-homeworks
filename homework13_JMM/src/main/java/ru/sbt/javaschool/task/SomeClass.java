package ru.sbt.javaschool.task;

import java.time.LocalTime;
import java.util.concurrent.Callable;

/**
 * Created by скурихин on 29.09.2016.
 */
public class SomeClass implements Callable<String> {
    @Override
    public String call() throws BusinessException {
        //throw new BusinessException("Exception!");
        return Thread.currentThread().getName() + " " + LocalTime.now().getSecond();
    }

}
