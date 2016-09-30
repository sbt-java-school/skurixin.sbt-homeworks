package ru.sbt.javaschool;

/**
 * Created by скурихин on 30.09.2016.
 */
public interface Context {

    //возвращает количество тасков, которые на текущий момент успешно выполнились.
    int getCompletedTaskCount();

    //возвращает количество тасков, при выполнении которых произошел Exception.
    int getFailedTaskCount();

    //возвращает количество тасков, которые не были выполены из-за отмены (вызовом предыдущего метода).
    int getInterruptedTaskCount();

    //отменяет выполнения тасков, которые еще не начали выполняться.
    void interrupt();

    //вернет true, если все таски были выполнены или отменены, false в противном случае.
    boolean isFinished();
}
