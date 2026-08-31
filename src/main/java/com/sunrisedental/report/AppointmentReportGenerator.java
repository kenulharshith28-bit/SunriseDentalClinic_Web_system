package com.sunrisedental.report;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.model.Appointment;

import java.sql.SQLException;
import java.util.List;

public class AppointmentReportGenerator
        implements ReportGenerator {

    private final AppointmentDAO appointmentDAO;

    public AppointmentReportGenerator(
            final AppointmentDAO appointmentDAO) {

        this.appointmentDAO =
                appointmentDAO;
    }

    @Override
    public String generate() {

        try {

            final List<Appointment> appointments =
                    appointmentDAO
                            .findAllAppointments();

            if (appointments.isEmpty()) {
                return "No appointments found";
            }

            final StringBuilder report =
                    new StringBuilder();

            report.append(
                    "Appointment Report\n\n");

            for (Appointment appointment : appointments) {

                report.append(
                                "Appointment Number: ")
                        .append(
                                appointment
                                        .getAppointmentNumber())
                        .append("\n");

                report.append(
                                "Date: ")
                        .append(
                                appointment
                                        .getAppointmentDate())
                        .append("\n");

                report.append(
                                "Time: ")
                        .append(
                                appointment
                                        .getAppointmentTime())
                        .append("\n");

                report.append(
                                "Status: ")
                        .append(
                                appointment
                                        .getStatus())
                        .append("\n");

                report.append(
                        "--------------------\n");
            }

            return report.toString();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to generate appointment report",
                    exception);
        }
    }
}