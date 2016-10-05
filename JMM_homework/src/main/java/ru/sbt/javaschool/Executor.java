package ru.sbt.javaschool;


/**
 * Created by скурихин on 05.10.2016.
 */
public class Executor implements ExecutionManager {
    private Context context;
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        context=new ContextImpl();

        return null;
    }

}
