package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

/**
 * Defines persistence operations for appointments.
 */
public interface AppointmentDAO {

    /**
      saves an appointment.
     */
    int saveAppointment(
            Appointment appointment)
            throws SQLException;

    /**
     ffinds an appointment using it's unique appointment number.
     */
    Optional<Appointment> findByAppointmentNumber(
            final String appointmentNumber)
            throws SQLException;


    Optional<Appointment> findByAppointmentDateAndNumber(
            LocalDate appointmentDate,
            String appointmentNumber)
            throws SQLException;

    List<Appointment> findAllAppointments()
            throws SQLException;

    String generateNextAppointmentNumber(
            LocalDate appointmentDate)
            throws SQLException;

    int getAppointmentCount()
            throws SQLException;

    List<Integer> getAppointmentCountsForCurrentWeek()
            throws SQLException;
}
