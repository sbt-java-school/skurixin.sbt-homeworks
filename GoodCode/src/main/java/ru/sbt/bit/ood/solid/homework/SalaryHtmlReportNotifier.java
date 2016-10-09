package ru.sbt.bit.ood.solid.homework;

import ru.sbt.bit.ood.solid.homework.builder.ReportBuilder;
import ru.sbt.bit.ood.solid.homework.dao.SalaryPaymentDao;
import ru.sbt.bit.ood.solid.homework.model.SalaryPayment;
import ru.sbt.bit.ood.solid.homework.sender.Sender;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class SalaryHtmlReportNotifier {

    private Connection connection;
    private SalaryPaymentDao salaryPaymentDao;
    private ReportBuilder reportBuilder;
    private Sender sender;

    public SalaryHtmlReportNotifier(Connection connection, SalaryPaymentDao salaryPaymentDao, ReportBuilder reportBuilder, Sender sender) {
        this.connection = connection;
        this.salaryPaymentDao = salaryPaymentDao;
        this.reportBuilder = reportBuilder;
        this.sender = sender;
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, Date dateFrom, Date dateTo, String recipients) {
        List<SalaryPayment> salaryPayments = salaryPaymentDao.salaryPaymentGetByParam(departmentId, dateFrom, dateTo);
        StringBuilder report = reportBuilder.build(salaryPayments);
        sender.send(recipients, report);

    }

}
