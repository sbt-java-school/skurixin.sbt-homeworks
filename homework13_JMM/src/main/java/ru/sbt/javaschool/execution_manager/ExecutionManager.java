package ru.sbt.javaschool.execution_manager;

/**
 * Created by скурихин on 30.09.2016.
 */
public interface ExecutionManager {
    //неблокирующий метод, который сразу возвращает объект Context. Context это интерфейс следующего вида:
    Context execute(Runnable callback, Runnable... tasks);
}
