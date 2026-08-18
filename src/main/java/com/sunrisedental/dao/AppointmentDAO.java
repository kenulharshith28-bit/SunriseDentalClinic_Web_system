package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Defines persistence operations for appointments.
 */
public interface AppointmentDAO {

    /**
     * saves an appointment.
     *
     * @param appointment appointment to save
     * @return true when the appointment is saved successfully
     * @throws SQLException if a database operation fails
     */
    boolean saveAppointment(final Appointment appointment)
            throws SQLException;

    /**
     * ffinds an appointment using it's unique appointment number.
     *
     * @param appointmentNumber unique appointment number
     * @return appointment when found, otherwise an empty Optional
     * @throws SQLException if a database operation fails
     */
    Optional<Appointment> findByAppointmentNumber(
            final String appointmentNumber)
            throws SQLException;
}

