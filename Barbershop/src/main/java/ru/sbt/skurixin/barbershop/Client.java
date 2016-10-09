package ru.sbt.skurixin.barbershop;

/**
 * Created by скурихин on 06.10.2016.
 */
public class Client extends Thread {
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
            System.err.println("interrupted");
        }
    }

    public void cutHair() {
        System.out.println(name + " is cutting his hair");
    }

    private void waiting() {
        try {
            System.out.println(name + " is waiting");
            synchronized (this) {
                wait();
                System.out.println(name + " has ended waiting");
                notify();
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupt have come for your soul");
        }
    }

    public boolean takeTheSeat() {
        System.out.println(name + " try to take the seat");
        boolean isAdd = shop.getSeats().add(this);
        shop.getLock().unlock();
        return isAdd;
    }

    private boolean isBarberBusy() throws InterruptedException {
        System.out.println(name + " is coming");
        shop.getLock().lock();
        Thread.sleep(timeToTravelForBarber);
        if (shop.getBarber().isWorking()) {
            System.out.println(name + " see, that barber is BUSY");
            Thread.sleep(timeToTravelForBarber);
            return true;
        } else {
            System.out.println(name + " see, that barber SLEEPING");
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
