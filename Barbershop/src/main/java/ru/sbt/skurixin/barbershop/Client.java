package ru.sbt.skurixin.barbershop;

import org.apache.log4j.Logger;

/**
 * Created by скурихин on 06.10.2016.
 */
public class Client extends Thread {
    // Инициализация логера
    private static final Logger LOGGER = Logger.getLogger(Client.class);
    private BarberShop shop;
    private String name;
    private final long timeToTravelForBarber;

    public Client(BarberShop shop, String name, long timeToTravelForBarber) {
        this.shop = shop;
        this.name = name;
        this.timeToTravelForBarber = timeToTravelForBarber;
    }

    @Override
    public void run() {
        try {
            if (isBarberBusy()) {
                if (takeTheSeat()) {
                    waiting();
                } else {
                    throw new RuntimeException(name + " is going AWAY!!");
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error("interrupted");
            Thread.currentThread().interrupt();
        }
    }

    public void cutHair() {
        LOGGER.info(name + " is cutting his hair");
    }

    private void waiting() {
        try {
            LOGGER.info(name + " is waiting");
            synchronized (this) {
                wait();
                LOGGER.info(name + " has ended waiting");
                notify();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Interrupt have come for your soul" + e);
            Thread.currentThread().interrupt();
        }
    }

    public boolean takeTheSeat() {
        LOGGER.info(name + " try to take the seat");
        boolean isAdd = shop.getSeats().add(this);
        shop.getLock().unlock();
        return isAdd;
    }

    private boolean isBarberBusy() throws InterruptedException {
        LOGGER.info(name + " is coming");
        shop.getLock().lock();
        Thread.sleep(timeToTravelForBarber);
        if (shop.getBarber().isWorking()) {
            LOGGER.info(name + " see, that barber is BUSY");
            Thread.sleep(timeToTravelForBarber);
            return true;
        } else {
            LOGGER.info(name + " see, that barber SLEEPING");
            synchronized (shop.getBarber()) {
                shop.getBarber().setCurrentClient(this);
                shop.getLock().unlock();
                shop.getBarber().notify();
            }
            return false;
        }
    }

    public String returnName() {
        return name;
    }
}
