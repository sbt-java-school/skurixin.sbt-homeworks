package ru.sbt.javaschool.skurixin;

import org.junit.Test;

/**
 * Created by скурихин on 04.10.2016.
 */
public class TestThreadPool {
    @Test
    public void test() throws InterruptedException {
        ThreadPool pool = new ThreadPool(2);
        pool.submit(new Task());
        pool.submit(new Task());
        pool.submit(new Task());
        pool.submit(new Task());

        Thread.currentThread().sleep(5000);
    }

    class Task implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(1);
                System.out.println("TASK! " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
