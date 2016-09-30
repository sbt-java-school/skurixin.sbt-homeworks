package ru.sbt.javaschool;

/**
 * Created by скурихин on 30.09.2016.
 */
public class RunnableClass<T> implements Runnable {
    private Task<T> task;
    @Override
    public void run(){
        try {
            T t = task.get();
            System.out.println(t);
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
        }
    }

    public RunnableClass(Task<T> task) {
        this.task=task;
    }
}
