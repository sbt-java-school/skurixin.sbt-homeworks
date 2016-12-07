package ru.sbt.skurixin.barbershop;

import org.apache.log4j.Logger;

/**
 * Created by скурихин on 06.10.2016.
 */
public class Barber extends Thread {
    // Инициализация логера
    private static final Logger LOGGER = Logger.getLogger(Barber.class);
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
                LOGGER.error("Interrupted barber" + e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void sleeping() {
        synchronized (this) {
            try {
                LOGGER.info("Barber sleep");
                this.wait();
            } catch (InterruptedException e) {
                LOGGER.error("Interrupted sleep of barber");
                Thread.currentThread().interrupt();
            }
        }
        LOGGER.info("Barber end sleep");
    }

    private boolean checkClients() throws InterruptedException {
        LOGGER.info("Barber will go to seats for a long time");
        Thread.sleep(timeOfTravelToSeats);
        LOGGER.info("Barber came for clients");
        currentClient = shop.getSeats().remove();
        boolean flag;
        if (currentClient == null) {
            flag = false;
        } else {
            flag = true;
            synchronized (currentClient) {
                LOGGER.info("Barber take with him " + currentClient.returnName());
                currentClient.notify();
                try {
                    currentClient.wait();
                } catch (InterruptedException e) {
                    LOGGER.error("interrupted in checkClients");
                    Thread.currentThread().interrupt();
                }
            }
        }
        Thread.sleep(timeOfTravelToSeats);
        shop.getLock().unlock();
        LOGGER.info("Barber came to work place");
        return flag;
    }

    private void working() {
        isBusy = true;
        LOGGER.info("Barber start working");
        try {
            currentClient.cutHair();
            Thread.sleep(timeOfWork);
            LOGGER.info("Barber end working");
            isBusy = false;
            shop.getLock().lock();
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted in work" + e);
            Thread.currentThread().interrupt();
        }
    }

    public boolean isWorking() {
        return isBusy;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }
}
