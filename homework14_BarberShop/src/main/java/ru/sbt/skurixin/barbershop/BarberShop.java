package ru.sbt.skurixin.barbershop;

import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by скурихин on 06.10.2016.
 */
public class BarberShop {
    // Инициализация логера
    private static final Logger LOGGER = Logger.getLogger(BarberShop.class);
    private final int COUNT_OF_SEATS;
    private final Seats seats;
    private final Barber barber;
    private final long timeToTravelForBarber;
    private final Lock lock=new ReentrantLock();

    public BarberShop(int count_of_seats, long timeOfWork, long timeOfBarberTravel, long timeOfClientTravel) {
        COUNT_OF_SEATS = count_of_seats;
        seats = new Seats(count_of_seats);
        barber = new Barber(this, timeOfWork, timeOfBarberTravel);
        this.timeToTravelForBarber = timeOfClientTravel;
    }

    public void startWorking(int countOfClients, long frequencyOfAppearance) {
        barber.start();

        //замок, клиентов должно быть сгенерировано countOfClients штук
        CountDownLatch latch = new CountDownLatch(countOfClients);
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        BarberShop shop = this;
        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                latch.countDown();
                Client client = new Client(shop, "Client-" + latch.getCount(),timeToTravelForBarber);
                client.setUncaughtExceptionHandler((t, e) -> System.out.println(e.getMessage()));
                client.start();
            }
        }, 0, frequencyOfAppearance, TimeUnit.MILLISECONDS);

        try {
            latch.await();
            schedule.shutdown();
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted sleep of barbershop" + e);
            Thread.currentThread().interrupt();
        }

        try {
            Thread.currentThread().sleep(15000);
            LOGGER.info("BARBERSHOP IS CLOSED");
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted sleep barbershop" + e);
            Thread.currentThread().interrupt();
        }
    }

    public Seats getSeats() {
        return seats;
    }

    public Barber getBarber() {
        return barber;
    }

    public Lock getLock() {
        return lock;
    }
}
