package ru.sbt.skurixin.barbershop;

/**
 * Created by скурихин on 06.10.2016.
 */
public class Barber extends Thread {
    private long timeOfWork;
    private boolean isWorking;
    private BarberShop shop;

    public Barber(BarberShop barberShop, long timeOfWork) {
        this.timeOfWork = timeOfWork;
        this.shop = barberShop;
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
        System.out.println("Barber sleep");
        try {
            synchronized (shop.barber) {
                shop.barber.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Barber end sleep");
    }

    private boolean checkClients() {
        System.out.println("Barber go for clients");
        Client client = shop.seats.remove();
        if (client == null) {
            return false;
        } else {
            synchronized (client) {
                System.out.println("Barber take with him " + client.getName());
                client.notify();
            }
        }
        return true;
//        Client client = shop.seats.remove();
//        if(client==null){
//            return false;
//        }
//        client.takeTheSeat();
//        return true;
    }

    private void working() {
        isWorking = true;
        System.out.println("Barber start working");
        try {
            Thread.sleep(timeOfWork);
            System.out.println("Barber end working");
            isWorking = false;
        } catch (InterruptedException e) {

        }
    }

    public boolean isWorking() {
        return isWorking;
    }
}
