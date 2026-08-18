package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.model.Appointment;

import java.sql.SQLException;
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
     *
     * @param appointmentNumber appointment number to search for
     * @return matching appointment when found
     * @throws SQLException if persistence access fails
     */
    public Optional<Appointment> searchAppointment(
            final String appointmentNumber)
            throws SQLException {

        return appointmentDAO
                .findByAppointmentNumber(
                        appointmentNumber);
    }
}