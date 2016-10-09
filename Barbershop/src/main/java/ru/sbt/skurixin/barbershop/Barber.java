package ru.sbt.skurixin.barbershop;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by скурихин on 06.10.2016.
 */
public class Barber extends Thread {
    private long timeOfWork;
    private long timeOfTravelToSeats;
    private volatile boolean isBusy;
    private BarberShop shop;
    private Client currentClient;

    public Barber(BarberShop barberShop, long timeOfWork, long timeOfTravelToSeats) {
        this.timeOfWork = timeOfWork;
        this.shop = barberShop;
        this.timeOfTravelToSeats = timeOfTravelToSeats;
    }

    @Override
    public void run() {
        while (true) {
            sleeping();
            try {
                do {
                    working();
                } while (checkClients());
            } catch (InterruptedException e) {
                System.err.println("interrupted");
            }
        }
    }

    private void sleeping() {
        synchronized (this) {
            try {
                System.out.println("Barber sleep");
                this.wait();
            } catch (InterruptedException e) {
                System.out.println("Interrupted sleep");
            } finally {
            }
        }
        System.out.println("Barber end sleep");
    }

    private boolean checkClients() throws InterruptedException {
        System.out.println("Barber will go to seats for a long time");
        Thread.sleep(timeOfTravelToSeats);
        System.out.println("Barber came for clients");
        currentClient = shop.getSeats().remove();
        boolean flag;
        if (currentClient == null) {
            flag = false;
        } else {
            flag = true;
            synchronized (currentClient) {
                System.out.println("Barber take with him " + currentClient.returnName());
                currentClient.notify();
                try {
                    currentClient.wait();
                } catch (InterruptedException e) {
                    System.err.println("interrupted in checkClients");
                }
            }
        }
        Thread.sleep(timeOfTravelToSeats);
        shop.getLock().unlock();
        System.out.println("Barber came to work place");
        return flag;
    }

    private void working() {
        isBusy = true;
        System.out.println("Barber start working");
        try {
            currentClient.cutHair();
            Thread.sleep(timeOfWork);
            System.out.println("Barber end working");
            isBusy = false;
            shop.getLock().lock();
        } catch (InterruptedException e) {
        }
    }

    public boolean isWorking() {
        return isBusy;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }
}
