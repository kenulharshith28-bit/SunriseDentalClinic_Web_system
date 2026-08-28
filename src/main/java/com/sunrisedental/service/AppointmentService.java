package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.model.Appointment;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;



/**
 * Handles business operations related to appointments.
 */
public class AppointmentService {

    private final AppointmentDAO appointmentDAO;

    public AppointmentService(
            final AppointmentDAO appointmentDAO) {

        this.appointmentDAO = appointmentDAO;
    }

    /**
     Searches for an appointment using its appointment number.
     */
    public Optional<Appointment> searchAppointment(
            final String appointmentNumber)
            throws SQLException {

        if (appointmentNumber == null
                || appointmentNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Appointment number must not be blank");
        }

        try {
            return appointmentDAO
                    .findByAppointmentNumber(
                            appointmentNumber);

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to search appointment",
                    exception);
        }
    }

    /**
     Saves an appointment.
     */
    public boolean saveAppointment(
            final Appointment appointment)
            throws SQLException {

        if (appointment == null) {
            throw new IllegalArgumentException(
                    "Appointment must not be null");
        }

        try {
            return appointmentDAO
                    .saveAppointment(appointment);

        } catch (SQLIntegrityConstraintViolationException exception) {

            if (exception.getErrorCode() == 1062) {
                throw exception;
            }

            throw new SQLException(
                    "Failed to save appointment",
                    exception);

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to save appointment",
                    exception);
        }
    }
}
