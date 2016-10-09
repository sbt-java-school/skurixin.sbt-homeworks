package ru.sbt.bit.ood.solid.homework.dao;

import ru.sbt.bit.ood.solid.homework.model.SalaryPayment;

import java.util.Date;
import java.util.List;

/**
 * Created by SBTJavastudent on 29.09.2016.
 */
public interface SalaryPaymentDao {
    List<SalaryPayment> salaryPaymentGetByParam(String departmentId, Date dateFrom, Date dateTo);
}
