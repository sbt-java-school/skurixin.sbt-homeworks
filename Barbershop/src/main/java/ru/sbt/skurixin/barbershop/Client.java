package ru.sbt.skurixin.barbershop;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by скурихин on 06.10.2016.
 */
public class Client extends Thread {
    private BarberShop shop;

    public Client(BarberShop shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        if (isBarberBusy()) {
            if (takeTheSeat()) {
                waiting();
                cutHair();
            } else {
                throw new RuntimeException(Thread.currentThread().getName() + " is going AWAY");
            }
        }
    }

    private void cutHair() {
        System.out.println(Thread.currentThread().getName() + " is cuting his hair");
    }

    private void waiting() {
        try {
            System.out.println(Thread.currentThread().getName() + " is waiting");
            synchronized (this) {
                wait();
            }
            System.out.println(Thread.currentThread().getName() + " has ended waiting");
        } catch (InterruptedException e) {
            System.out.println("Interrupt have come for your soul");
        }
    }

    public boolean takeTheSeat() {
        System.out.println(Thread.currentThread().getName() + " try to take the seat");
        return shop.seats.add(this);
    }

    private boolean isBarberBusy() {
        if (shop.barber.isWorking()) {
            System.out.println(Thread.currentThread().getName() + " see, that barber is BUSY");
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " see, that barber SLEEPING");
            synchronized (shop.barber) {
                shop.barber.notify();
            }
            yield();
            cutHair();
            return false;
        }
    }
}
