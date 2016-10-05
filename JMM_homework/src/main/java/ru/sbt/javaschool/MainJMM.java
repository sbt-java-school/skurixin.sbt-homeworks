package ru.sbt.javaschool;

/**
 * Created by скурихин on 29.09.2016.
 */
public class MainJMM {
    public static void main(String[] args) throws InterruptedException {
        Task<String> task = new Task<>(new SomeClass());
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new RunnableClass(task));
            thread.start();
        }
//        String str = task.get();
//        System.out.println(str);
    }
}
