package ru.sbt.bit.ood.solid.homework.model;

import java.math.BigDecimal;

/**
 * Created by SBTJavastudent on 29.09.2016.
 */
public class SalaryPayment {
    public SalaryPayment(Long id, String name, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    private final Long id;
    private final String name;
    private final BigDecimal salary;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}
