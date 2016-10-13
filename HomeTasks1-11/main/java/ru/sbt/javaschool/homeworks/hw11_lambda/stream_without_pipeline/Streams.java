package ru.sbt.javaschool.homeworks.hw11_lambda.stream_without_pipeline;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by скурихин on 13.09.2016.
 */
public class Streams<T> {
    private List<T> list;

    private Streams() {
    }

    public static <T> Streams<T> of(List<T> newCollection) {
        Streams<T> myStream = new Streams<>();
        myStream.list = new ArrayList();
        myStream.list.addAll(newCollection);
        return myStream;
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (!predicate.test(iterator.next())) {
                iterator.remove();
            }
        }
        return this;
    }

    public Streams<T> transform(Function<? super T, T> function) {
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            iterator.set(function.apply(t));
        }
        return this;
    }

    public <R, S> Map<R, S> toMap(Function<T, R> function1, Function<T, S> function2) {
        HashMap<R, S> map = new HashMap<>();

        for (T t : list) {
            map.put(function1.apply(t), function2.apply(t));
        }
        return map;
    }

    public void forEach(Consumer<? super T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }
//    public Collection map() {
//
//    }
}
