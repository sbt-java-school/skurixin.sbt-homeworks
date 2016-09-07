package ru.sbt.javaschool.homeworks.hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by скурихин on 13.08.2016.
 */
public class TruckDaoMemoryImpl implements TruckDao{

    private final List<Truck> trucks = new ArrayList<Truck>(
            Arrays.asList(
            new Truck(14, 100, "kamaz"),
            new Truck(2, 20, "uaz"),
            new Truck(3, 45, "kamaz"),
            new Truck(4, 23, "gaz"),
            new Truck(5, 12, "uaz"),
            new Truck(6, 54, "kamaz")
    ));

    @Override
    public List<Truck> list() {
        return trucks;
    }
}
