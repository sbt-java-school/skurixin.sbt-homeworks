package ru.sbt.javaschool.task;

import java.util.concurrent.Callable;

/**
 * Created by скурихин on 29.09.2016.
 */
public class Task<T> {
    private Callable<? extends T> callable;
    private static final Object lock = new Object();
    private T result;
    private BusinessException businessException;


    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() throws BusinessException {
        try {
            if (result != null) {
                return result;
            }
            if (businessException != null) {
                throw businessException;
            }
            synchronized (lock) {
                if (result != null) {
                    return result;
                }
                if (businessException != null) {
                    throw businessException;
                }
                result = callable.call();
                Thread.sleep(2000);
                return result;
            }
        } catch (BusinessException e) {
            businessException = e;
            throw businessException;
        } catch (InterruptedException e) {
            System.out.println("Interrupted!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
