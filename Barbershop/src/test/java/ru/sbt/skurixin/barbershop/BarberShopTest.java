package ru.sbt.skurixin.barbershop;

import org.junit.Test;

/**
 * Created by скурихин on 06.10.2016.
 */
public class BarberShopTest {

    //
    @Test
    public void test1(){
        new BarberShop(3,1000, 2000).startWorking(5,1000);
    }

    //
    @Test
    public void test2(){
        new BarberShop(2,500, 0).startWorking(8,1000);
    }
}
