package ru.sbt.javaschool.skurixin;

/**
 * Created by скурихин on 04.10.2016.
 */
public class ThreadPool {
    private final int DEPTH_OF_POOL;
    private ThreadQueue<Runnable> queueTasks;
    private ManagedThread[] threads;

    public ThreadPool(int depth) {
        if (depth < 1) {
            throw new IllegalArgumentException("Depth must be bigger than 0");
        }
        this.DEPTH_OF_POOL = depth;
        threads = new ManagedThread[DEPTH_OF_POOL];
        queueTasks = new ThreadQueue<>();
        for (int i = 0; i < DEPTH_OF_POOL; i++) {
            threads[i] = new ManagedThread(queueTasks);
            threads[i].start();
        }
    }

    public void submit(Runnable task) {
        if(task==null){
            throw new IllegalArgumentException("Task is not defined");
        }
        queueTasks.add(task);
    }
}
