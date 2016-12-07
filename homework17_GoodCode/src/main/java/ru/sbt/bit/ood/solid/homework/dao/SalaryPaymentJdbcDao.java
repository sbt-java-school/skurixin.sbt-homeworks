package ru.sbt.bit.ood.solid.homework.dao;

import ru.sbt.bit.ood.solid.homework.model.SalaryPayment;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by SBTJavastudent on 29.09.2016.
 */
public class SalaryPaymentJdbcDao implements SalaryPaymentDao {

    private Connection connection;

    public SalaryPaymentJdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<SalaryPayment> salaryPaymentGetByParam(String departmentId, java.util.Date dateFrom, java.util.Date dateTo) {
        // PreparedStatement ps = null;
        try {
            PreparedStatement ps = getPreparedStatement();

            List<SalaryPayment> salaryPayments = new ArrayList<SalaryPayment>();
            // inject parameters to sql
            ps.setString(0, departmentId);
            ps.setDate(1, new java.sql.Date(dateFrom.getTime()));
            ps.setDate(2, new java.sql.Date(dateTo.getTime()));

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                salaryPayments.add(new SalaryPayment(results.getLong("emp_id"), results.getString("amp_name"), results.getBigDecimal("salary")));
            }
            return salaryPayments;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private PreparedStatement getPreparedStatement() throws SQLException {
        return connection.prepareStatement("select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join" +
                "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                " sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
    }

}
