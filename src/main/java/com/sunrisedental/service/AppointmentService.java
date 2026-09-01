package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.model.Appointment;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
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
     * Searches for an appointment using its appointment number.
     * Kept for compatibility with older code/tests.
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
                            appointmentNumber.trim());

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to search appointment",
                    exception);
        }
    }

    /**
     * Searches for an appointment using the date
     * and daily appointment number.
     */
    public Optional<Appointment> searchAppointment(
            final LocalDate appointmentDate,
            final String appointmentNumber) {

        if (appointmentDate == null) {

            throw new IllegalArgumentException(
                    "Appointment date must not be null");
        }

        if (appointmentNumber == null
                || appointmentNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Appointment number must not be blank");
        }

        try {

            return appointmentDAO
                    .findByAppointmentDateAndNumber(
                            appointmentDate,
                            appointmentNumber.trim());

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to search appointment",
                    exception);
        }
    }

    /**
     * Saves an appointment and returns the generated
     * database appointment ID.
     */
    public int saveAppointment(
            final Appointment appointment)
            throws SQLException {

        if (appointment == null) {

            throw new IllegalArgumentException(
                    "Appointment must not be null");
        }

        if (appointment.getAppointmentNumber() == null
                || appointment.getAppointmentNumber().isBlank()) {

            throw new IllegalArgumentException(
                    "Appointment number must not be blank");
        }

        if (appointment.getPatientId() <= 0) {

            throw new IllegalArgumentException(
                    "Patient ID must be greater than 0");
        }

        if (appointment.getDentistId() <= 0) {

            throw new IllegalArgumentException(
                    "Dentist ID must be greater than 0");
        }

        if (appointment.getAppointmentDate() == null) {

            throw new IllegalArgumentException(
                    "Appointment date must not be empty");
        }

        if (appointment.getAppointmentTime() == null) {

            throw new IllegalArgumentException(
                    "Appointment time must not be empty");
        }

        if (appointment.getStatus() == null
                || appointment.getStatus().isBlank()) {

            throw new IllegalArgumentException(
                    "Appointment status must not be blank");
        }

        try {

            return appointmentDAO
                    .saveAppointment(
                            appointment);

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

    /**
     * Generates the next daily appointment number.
     */
    public String generateNextAppointmentNumber(
            final LocalDate appointmentDate) {

        if (appointmentDate == null) {

            throw new IllegalArgumentException(
                    "Appointment date must not be null");
        }

        try {

            return appointmentDAO
                    .generateNextAppointmentNumber(
                            appointmentDate);

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to generate appointment number",
                    exception);
        }
    }
}