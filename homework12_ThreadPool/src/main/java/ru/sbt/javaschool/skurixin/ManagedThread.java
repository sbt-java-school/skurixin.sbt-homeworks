package ru.sbt.javaschool.skurixin;

/**
 * Created by скурихин on 04.10.2016.
 */
public class ManagedThread extends Thread {
    private ThreadQueue<Runnable> queue;
    int countOfExecutedTasks;
    int countOfFailedTasks;

    public ManagedThread(ThreadQueue<Runnable> queue) {
        this.queue = queue;
        countOfExecutedTasks = 0;
        countOfFailedTasks = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Runnable task = queue.remove();
                task.run();
                countOfExecutedTasks++;
            } catch (InterruptedException e) {
                break;
            } catch (Exception e) {
                countOfFailedTasks++;
                continue;
            }

        }
    }
}
