package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;

import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

/**
 * Defines persistence operations for appointments.
 */
public interface AppointmentDAO {

    /**
     * saves an appointment.
     */
    boolean saveAppointment(final Appointment appointment)
            throws SQLException;

    /**
     * ffinds an appointment using it's unique appointment number.
     */
    Optional<Appointment> findByAppointmentNumber(
            final String appointmentNumber)
            throws SQLException;

    List<Appointment> findAllAppointments()
            throws SQLException;
}

