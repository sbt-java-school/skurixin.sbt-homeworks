package ru.sbt.bit.ood.solid.homework.builder;

import ru.sbt.bit.ood.solid.homework.model.SalaryPayment;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by SBTJavastudent on 29.09.2016.
 */
public class SalaryReportBuilder implements ReportBuilder<SalaryPayment> {
    @Override
    public StringBuilder build(List<SalaryPayment> objects) {
        StringBuilder htmlReport = new StringBuilder();
        htmlReport.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
        BigDecimal totals = new BigDecimal(0);

        for (SalaryPayment salaryPayment:objects){
            // process each row of query results
            htmlReport.append("<tr>"); // add row start tag
            htmlReport.append("<td>").append(salaryPayment.getName()).append("</td>"); // appending employee name
            htmlReport.append("<td>").append(salaryPayment.getSalary()).append("</td>"); // appending employee salary for period
            htmlReport.append("</tr>"); // add row end tag
            totals.add(salaryPayment.getSalary()); // add salary to totals
        }
        htmlReport.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        htmlReport.append("</table></body></html>");
        return htmlReport;
    }
}
