package ru.sbt.javaschool.execution_manager;

/**
 * Created by скурихин on 05.10.2016.
 */
public class ContextImpl implements Context {
    private final Executor executor;
    private final int tasksCount;

    public ContextImpl(Executor executor, int tasksCount) {
        this.executor = executor;
        this.tasksCount = tasksCount;
    }

    @Override
    public int getCompletedTaskCount() {
        return executor.threadPool.getCountOfExecutedTasks();
    }

    @Override
    public int getFailedTaskCount() {
        return executor.threadPool.getCountOfFailedTasks();
    }

    @Override
    public int getInterruptedTaskCount() {
        return executor.threadPool.getInterruptedTasksCount();
    }

    @Override
    public void interrupt() {
        executor.threadPool.interruptTasks();
    }

    @Override
    public boolean isFinished() {
        if (tasksCount == getCompletedTaskCount() + getInterruptedTaskCount())
        return true;
        else return false;
    }
}
