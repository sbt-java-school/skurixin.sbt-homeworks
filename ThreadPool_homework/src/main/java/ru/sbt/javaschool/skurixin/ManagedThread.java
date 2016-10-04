package ru.sbt.javaschool.skurixin;

/**
 * Created by скурихин on 04.10.2016.
 */
public class ManagedThread extends Thread{
    private ThreadQueue<Runnable> queue;

    public ManagedThread(ThreadQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            try {
                Runnable task = queue.remove();
                System.out.println("EXECUTE " + Thread.currentThread().getName());
                task.run();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
