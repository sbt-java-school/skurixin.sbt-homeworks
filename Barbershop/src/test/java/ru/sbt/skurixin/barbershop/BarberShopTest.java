package ru.sbt.skurixin.barbershop;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by скурихин on 06.10.2016.
 */
public class BarberShopTest {
    @Test
    public void test(){
        new BarberShop(3,2000).startWorking(5,1000);

    }

    @Test
    public void testSchedule(){
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        CountDownLatch latch = new CountDownLatch(3);
        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                latch.countDown();
                System.out.println(System.currentTimeMillis());
            }
        },0,1000, TimeUnit.MILLISECONDS);

        try {
            latch.await();
            schedule.shutdown();
            System.out.println("Wait");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
