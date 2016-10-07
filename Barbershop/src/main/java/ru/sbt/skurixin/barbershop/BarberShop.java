package ru.sbt.skurixin.barbershop;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by скурихин on 06.10.2016.
 */
public class BarberShop {
    private final int COUNT_OF_SEATS;
    private final Seats seats;
    private final Barber barber;

    public BarberShop(int count_of_seats, long timeOfWork, long timeOfTravelToSeats) {
        COUNT_OF_SEATS = count_of_seats;
        seats = new Seats(count_of_seats);
        barber = new Barber(this, timeOfWork, timeOfTravelToSeats);
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
                Client client = new Client(shop, "Client-" + latch.getCount());
                client.setUncaughtExceptionHandler((t, e) -> System.out.println(e.getMessage()));
                client.start();
            }
        }, 0, frequencyOfAppearance, TimeUnit.MILLISECONDS);

        try {
            latch.await();
            schedule.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.currentThread().sleep(15000);
            System.out.println("BARBERSHOP IS CLOSED");
        } catch (InterruptedException e) {
            System.out.println("sleep barbershop");
        }
    }

    public Seats getSeats() {
        return seats;
    }

    public Barber getBarber() {
        return barber;
    }
}
