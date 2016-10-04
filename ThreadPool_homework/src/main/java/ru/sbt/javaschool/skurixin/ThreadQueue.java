package ru.sbt.javaschool.skurixin;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by скурихин on 04.10.2016.
 */
public class ThreadQueue<T> {
    private Queue<T> queue = new ArrayDeque<>();

    synchronized void add(T t) {
        System.out.println("Added! " + Thread.currentThread().getName());
        queue.add(t);
        System.out.println("Wake up! " + Thread.currentThread().getName());
        this.notify();
    }

    synchronized T remove() throws InterruptedException {
        System.out.println("try to remove " + Thread.currentThread().getName());
        while (queue.size() < 1) {
            System.out.println("start wait " + Thread.currentThread().getName());
            this.wait();
            System.out.println("end wait " + Thread.currentThread().getName());
        }
        System.out.println("remove " + Thread.currentThread().getName());
        return queue.remove();

    }
}
