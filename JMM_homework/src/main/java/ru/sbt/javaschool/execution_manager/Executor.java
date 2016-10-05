package ru.sbt.javaschool.execution_manager;


import ru.sbt.javaschool.skurixin.ThreadPool;

import java.util.concurrent.CountDownLatch;

/**
 * Created by скурихин on 05.10.2016.
 */
public class Executor implements ExecutionManager {
    private final int COUNT_OF_THREADS;
    ThreadPool threadPool;

    public Executor(int countOfThreads) {
        if (countOfThreads < 1) {
            throw new IllegalArgumentException("Число потоков должно быть больше 0");
        }
        this.COUNT_OF_THREADS = countOfThreads;
        threadPool = new ThreadPool(COUNT_OF_THREADS);
    }

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        Context context = new ContextImpl(this, tasks.length);
        for (Runnable task : tasks) {
            threadPool.submit(task);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!context.isFinished()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted callback");
                    }
                }
                callback.run();
            }
        }).start();

        return context;
    }

}
