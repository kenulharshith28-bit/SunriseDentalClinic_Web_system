package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;

import java.sql.SQLException;

/**
 * Defines persistence operations for appointments.
 */
public interface AppointmentDAO {

    /**
     * Saves an appointment.
     *
     * @param appointment appointment to save
     * @return true when the appointment is saved successfully
     * @throws SQLException if a database operation fails
     */
    boolean saveAppointment(final Appointment appointment)
            throws SQLException;
}