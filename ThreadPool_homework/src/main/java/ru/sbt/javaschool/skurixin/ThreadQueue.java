package ru.sbt.javaschool.skurixin;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by скурихин on 04.10.2016.
 */
public class ThreadQueue<T> {
    private Queue<T> queue = new ArrayDeque<>();

    synchronized void add(T t) {
        queue.add(t);
        this.notify();
    }

    synchronized T remove() throws InterruptedException {
        while (queue.size() < 1) {
            this.wait();
        }
        return queue.remove();
    }

    public void removeAll(){
        queue.clear();
    }
    public int size(){
        return queue.size();
    }
}
