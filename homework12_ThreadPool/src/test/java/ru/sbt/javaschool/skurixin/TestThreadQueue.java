package ru.sbt.javaschool.skurixin;

import org.junit.Test;

import java.util.ArrayDeque;

/**
 * Created by скурихин on 04.10.2016.
 */
public class TestThreadQueue {

    @Test
    public void Test() throws InterruptedException {
        ThreadQueue<Integer> queue = new ThreadQueue<>();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Producer(queue)).start();
        Thread.sleep(3000);
    }

    class Consumer implements Runnable {
        private ThreadQueue<Integer> queue;

        public Consumer(ThreadQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                System.out.println(queue.remove() + " " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Producer implements Runnable {
        private ThreadQueue<Integer> queue;

        public Producer(ThreadQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            queue.add(1);
        }
    }
}
