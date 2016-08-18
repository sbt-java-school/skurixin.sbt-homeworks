package ru.sbt.javaschool.homeworks.hw4;

import java.util.Arrays;
import java.util.List;

/**
 * Created by скурихин on 13.08.2016.
 */
public class TruckDaoMemoryImpl implements TruckDao{

    @Override
    public List<Truck> list() {
        return Arrays.asList(
                new Truck(14, 100),
                new Truck(2, 20),
                new Truck(3, 45),
                new Truck(4, 23),
                new Truck(5, 12),
                new Truck(6, 54)
                );
    }
}
