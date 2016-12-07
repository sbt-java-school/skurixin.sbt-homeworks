package ru.sbt.javaschool;

import org.junit.Test;
import ru.sbt.javaschool.execution_manager.Context;
import ru.sbt.javaschool.execution_manager.Executor;

/**
 * Created by скурихин on 05.10.2016.
 */
public class ExecutionManagerTest {

    public static final int COUNT_RUNNABLES = 15;

    @Test
    public void testManager() throws InterruptedException {
        Executor executor = new Executor(4);
        Runnable[] runnables = new RunnableImpl[COUNT_RUNNABLES];
        for (int i = 0; i < COUNT_RUNNABLES; i++) {
            runnables[i] = new RunnableImpl();
        }
        Context context = executor.execute(new RunnableInTheEnd(), runnables);

        for (int i = 0; i < 5; i++) {
            System.out.println("Completed "+context.getCompletedTaskCount()+" tasks");
            Thread.sleep(800);
        }
        while(!context.isFinished()){
            Thread.sleep(1000);
        }


    }

    class RunnableImpl implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Run " + Thread.currentThread().getName());
        }
    }

    class RunnableInTheEnd implements Runnable {

        @Override
        public void run() {
            System.out.println("ТредПул завершен!");
        }
    }
}
