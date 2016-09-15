package ru.sbt.javaschool.homeworks.hw11_lambda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by скурихин on 13.09.2016.
 */
public class Streams<T extends Person> {
    private List<T> list;
    private Streams<T> myStream;
    private static Streams staticStream;

    private Streams() {
        myStream = new Streams<T>();
    }

    public static Streams of(List newCollection) {
        staticStream = new Streams();
        staticStream.list = new ArrayList();
        staticStream.list.addAll(newCollection);
        return staticStream.myStream;
    }

    public Streams<T> filter(Predicate<T> predicate) {
        for (T t : list) {
            if (!predicate.test(t)) {
                list.remove(t);
            }
        }
        return this;
    }

    public Streams<T> transform(Function<T, T> function) {
        for (T t : list) {
            t = function.apply(t);
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

    public void forEach(Consumer consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }
//    public Collection map() {
//
//    }
}
