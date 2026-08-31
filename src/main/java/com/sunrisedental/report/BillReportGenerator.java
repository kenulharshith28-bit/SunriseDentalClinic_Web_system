package com.sunrisedental.report;

import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.model.Bill;

import java.sql.SQLException;
import java.util.List;

public class BillReportGenerator
        implements ReportGenerator {

    private final BillDAO billDAO;

    public BillReportGenerator(
            final BillDAO billDAO) {

        this.billDAO =
                billDAO;
    }

    @Override
    public String generate() {

        try {

            final List<Bill> bills =
                    billDAO
                            .findAllBills();

            if (bills.isEmpty()) {
                return "No bills found";
            }

            final StringBuilder report =
                    new StringBuilder();

            report.append(
                    "Bill Report\n\n");

            for (Bill bill : bills) {

                report.append(
                                "Bill ID: ")
                        .append(
                                bill.getBillId())
                        .append("\n");

                report.append(
                                "Appointment ID: ")
                        .append(
                                bill.getAppointmentId())
                        .append("\n");

                report.append(
                                "Total Amount: ")
                        .append(
                                bill.getTotalAmount())
                        .append("\n");

                report.append(
                        "--------------------\n");
            }

            return report.toString();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to generate bill report",
                    exception);
        }
    }
}