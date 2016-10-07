package ru.sbt.skurixin.barbershop;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by скурихин on 06.10.2016.
 */
public class Seats {
    private Queue<Client> queue;
    private final int COUNT_OF_SEATS;

    public Seats(int count_of_seats) {
        if (count_of_seats < 1) {
            throw new IllegalArgumentException("Число мест должно быть больше 0");
        }
        COUNT_OF_SEATS = count_of_seats;
        queue = new ArrayDeque(COUNT_OF_SEATS);
    }

    public boolean add(Client t) {
        if (queue.size() < COUNT_OF_SEATS) {
            queue.add(t);
            return true;
        } else return false;
    }

    public Client remove() {
        Client t = null;
        if (queue.size() > 0) {
            t = queue.remove();
        }
        return t;
    }
}
