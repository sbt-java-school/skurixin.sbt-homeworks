package ru.sbt.skurixin.barbershop;

/**
 * Created by скурихин on 06.10.2016.
 */
public class Client extends Thread {
    private BarberShop shop;
    private String name;

    public Client(BarberShop shop, String name) {
        this.shop = shop;
        this.name = name;
    }

    @Override
    public void run() {
        if (isBarberBusy()) {
            if (takeTheSeat()) {
                waiting();
            } else {
                throw new RuntimeException(name + " is going AWAY!!");
            }
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
        return shop.getSeats().add(this);
    }

    private boolean isBarberBusy() {
        if (shop.getBarber().isBusy()) {
            System.out.println(name + " see, that barber is BUSY");
            return true;
        } else {
            System.out.println(name + " see, that barber SLEEPING");
            synchronized (shop.getBarber()) {
                shop.getBarber().setCurrentClient(this);
                shop.getBarber().notify();
            }
            return false;
        }
    }

    public String returnName() {
        return name;
    }
}
