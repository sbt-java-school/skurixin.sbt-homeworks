package ru.sbt.skurixin.barbershop;

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
            do {
                working();
            } while (checkClients());
        }
    }

    private void sleeping() {
        synchronized (this) {
            try {
                isBusy = false;
                System.out.println("Barber sleep");
                this.wait();
            } catch (InterruptedException e) {
                System.out.println("Interrupted sleep");
            } finally {
            }
        }
        System.out.println("Barber end sleep");
    }

    private boolean checkClients() {
        System.out.println("Barber came for clients");
        currentClient = shop.getSeats().remove();
        if (currentClient == null) {
            return false;
        } else {
            synchronized (currentClient) {
                System.out.println("Barber take with him " + currentClient.returnName());
                currentClient.notify();
                try {
                    currentClient.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        return true;
    }

    private void working() {
        isBusy = true;
        System.out.println("Barber start working");
        try {
            currentClient.cutHair();
            Thread.sleep(timeOfWork);
            System.out.println("Barber end working");
            System.out.println("Barber will go to seats for a long time");
            Thread.sleep(timeOfTravelToSeats);
        } catch (InterruptedException e) {
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }
}
