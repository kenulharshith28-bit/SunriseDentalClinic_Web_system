package com.sunrisedental.report;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.AppointmentDAOImpl;
import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.dao.BillDAOImpl;

import java.sql.SQLException;

public class ReportGeneratorFactory {

    public ReportGenerator create(
            final String reportType) {

        try {

            if ("appointment".equalsIgnoreCase(
                    reportType)) {

                final AppointmentDAO appointmentDAO =
                        new AppointmentDAOImpl();

                return new AppointmentReportGenerator(
                        appointmentDAO);
            }

            if ("bill".equalsIgnoreCase(
                    reportType)) {

                final BillDAO billDAO =
                        new BillDAOImpl();

                return new BillReportGenerator(
                        billDAO);
            }

            return null;

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to create report generator",
                    exception);
        }
    }
}