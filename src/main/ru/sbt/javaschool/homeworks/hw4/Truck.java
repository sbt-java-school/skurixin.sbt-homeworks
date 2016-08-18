package ru.sbt.javaschool.homeworks.hw4;

/**
 * Created by скурихин on 13.08.2016.
 */
public class Truck {
    private long id;

    public Truck(long id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    private int capacity;
    private String type; //kamaz,uaz,...

    public Truck(long id, int capacity, String type) {
        this.id = id;
        this.capacity = capacity;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", capacity=" + capacity +
                '}';
    }

    public int getCapacity() {
        return capacity;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
