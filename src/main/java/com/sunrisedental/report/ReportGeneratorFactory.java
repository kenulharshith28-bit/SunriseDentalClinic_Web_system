package com.sunrisedental.report;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.AppointmentDAOImpl;
import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.dao.BillDAOImpl;

import java.sql.SQLException;

public class ReportGeneratorFactory {

    private final AppointmentDAO appointmentDAO;
    private final BillDAO billDAO;

    public ReportGeneratorFactory() {

        this.appointmentDAO = null;
        this.billDAO = null;
    }

    public ReportGeneratorFactory(
            final AppointmentDAO appointmentDAO,
            final BillDAO billDAO) {

        this.appointmentDAO =
                appointmentDAO;

        this.billDAO =
                billDAO;
    }

    public ReportGenerator create(
            final String reportType) {

        if ("appointment".equalsIgnoreCase(
                reportType)) {

            return new AppointmentReportGenerator(
                    getAppointmentDAO());
        }

        if ("bill".equalsIgnoreCase(
                reportType)) {

            return new BillReportGenerator(
                    getBillDAO());
        }

        return null;
    }

    private AppointmentDAO getAppointmentDAO() {

        if (appointmentDAO != null) {
            return appointmentDAO;
        }

        try {

            return new AppointmentDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to create appointment report generator",
                    exception);
        }
    }

    private BillDAO getBillDAO() {

        if (billDAO != null) {
            return billDAO;
        }

        try {

            return new BillDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to create bill report generator",
                    exception);
        }
    }
}