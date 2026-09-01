package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AppointmentDAOTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ResultSet generatedKeys;
    private Appointment appointment;
    private AppointmentDAO appointmentDAO;

    @BeforeEach
    void setUp() {

        connection =
                mock(Connection.class);

        preparedStatement =
                mock(PreparedStatement.class);

        resultSet =
                mock(ResultSet.class);

        generatedKeys =
                mock(ResultSet.class);

        appointment =
                mock(Appointment.class);

        appointmentDAO =
                new AppointmentDAOImpl(
                        connection);
    }

    @Test
    void shouldSaveAppointmentSuccessfully()
            throws SQLException {

        when(appointment.getAppointmentNumber())
                .thenReturn("A-001");

        when(appointment.getPatientId())
                .thenReturn(1);

        when(appointment.getDentistId())
                .thenReturn(1);

        when(appointment.getAppointmentDate())
                .thenReturn(
                        LocalDate.of(
                                2026,
                                8,
                                28));

        when(appointment.getAppointmentTime())
                .thenReturn(
                        LocalTime.of(
                                10,
                                0));

        when(appointment.getStatus())
                .thenReturn(
                        "SCHEDULED");

        when(appointment.getNotes())
                .thenReturn(
                        "Initial appointment");

        /*
         * saveAppointment now requests generated keys,
         * so this mock must use the two-argument
         * prepareStatement method.
         */
        when(connection.prepareStatement(
                anyString(),
                eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(
                        preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(1);

        when(preparedStatement.getGeneratedKeys())
                .thenReturn(
                        generatedKeys);

        when(generatedKeys.next())
                .thenReturn(true);

        when(generatedKeys.getInt(1))
                .thenReturn(25);

        final int result =
                appointmentDAO
                        .saveAppointment(
                                appointment);

        assertEquals(
                25,
                result,
                "Generated appointment ID should be returned");

        verify(preparedStatement)
                .setString(
                        1,
                        "A-001");

        verify(preparedStatement)
                .setInt(
                        2,
                        1);

        verify(preparedStatement)
                .setInt(
                        3,
                        1);

        verify(preparedStatement)
                .setDate(
                        4,
                        java.sql.Date.valueOf(
                                LocalDate.of(
                                        2026,
                                        8,
                                        28)));

        verify(preparedStatement)
                .setTime(
                        5,
                        java.sql.Time.valueOf(
                                LocalTime.of(
                                        10,
                                        0)));

        verify(preparedStatement)
                .setString(
                        6,
                        "SCHEDULED");

        verify(preparedStatement)
                .setString(
                        7,
                        "Initial appointment");

        verify(preparedStatement)
                .executeUpdate();

        verify(preparedStatement)
                .getGeneratedKeys();

        verify(generatedKeys)
                .getInt(1);
    }

    @Test
    void shouldRejectNullAppointment() {

        assertThrows(
                IllegalArgumentException.class,
                () -> appointmentDAO
                        .saveAppointment(
                                null));
    }

    @Test
    void shouldProvideMeaningfulSQLExceptionWhenSaveFails()
            throws SQLException {

        when(appointment.getAppointmentNumber())
                .thenReturn(
                        "A-001");

        when(connection.prepareStatement(
                anyString(),
                eq(Statement.RETURN_GENERATED_KEYS)))
                .thenThrow(
                        new SQLException(
                                "Database connection failed"));

        final SQLException exception =
                assertThrows(
                        SQLException.class,
                        () -> appointmentDAO
                                .saveAppointment(
                                        appointment));

        assertTrue(
                exception.getMessage()
                        .contains(
                                "Failed to save appointment"),
                "SQLException should describe the failed DAO operation");
    }

    @Test
    void shouldReturnEmptyWhenAppointmentDoesNotExist()
            throws SQLException {

        when(connection.prepareStatement(
                anyString()))
                .thenReturn(
                        preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(
                        resultSet);

        when(resultSet.next())
                .thenReturn(
                        false);

        final Optional<Appointment> result =
                appointmentDAO
                        .findByAppointmentNumber(
                                "A-999");

        assertNotNull(
                result,
                "DAO should return an Optional instead of null");

        assertTrue(
                result.isEmpty(),
                "Result should be empty when appointment does not exist");

        verify(preparedStatement)
                .setString(
                        1,
                        "A-999");

        verify(preparedStatement)
                .executeQuery();
    }

    @Test
    void shouldFindAppointmentByAppointmentNumber()
            throws SQLException {

        when(connection.prepareStatement(
                anyString()))
                .thenReturn(
                        preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(
                        resultSet);

        when(resultSet.next())
                .thenReturn(
                        true);

        when(resultSet.getInt(
                "appointment_id"))
                .thenReturn(
                        1);

        when(resultSet.getString(
                "appointment_number"))
                .thenReturn(
                        "A-001");

        when(resultSet.getInt(
                "patient_id"))
                .thenReturn(
                        1);

        when(resultSet.getInt(
                "dentist_id"))
                .thenReturn(
                        1);

        when(resultSet.getDate(
                "appointment_date"))
                .thenReturn(
                        java.sql.Date.valueOf(
                                LocalDate.of(
                                        2026,
                                        8,
                                        28)));

        when(resultSet.getTime(
                "appointment_time"))
                .thenReturn(
                        java.sql.Time.valueOf(
                                LocalTime.of(
                                        10,
                                        0)));

        when(resultSet.getString(
                "status"))
                .thenReturn(
                        "SCHEDULED");

        when(resultSet.getString(
                "notes"))
                .thenReturn(
                        "Initial appointment");

        final Optional<Appointment> result =
                appointmentDAO
                        .findByAppointmentNumber(
                                "A-001");

        assertTrue(
                result.isPresent(),
                "Appointment should be returned when it exists");

        assertEquals(
                1,
                result.get()
                        .getAppointmentId());

        assertEquals(
                "A-001",
                result.get()
                        .getAppointmentNumber());

        assertEquals(
                1,
                result.get()
                        .getPatientId());

        assertEquals(
                1,
                result.get()
                        .getDentistId());

        assertEquals(
                LocalDate.of(
                        2026,
                        8,
                        28),
                result.get()
                        .getAppointmentDate());

        assertEquals(
                LocalTime.of(
                        10,
                        0),
                result.get()
                        .getAppointmentTime());

        assertEquals(
                "SCHEDULED",
                result.get()
                        .getStatus());

        assertEquals(
                "Initial appointment",
                result.get()
                        .getNotes());

        verify(preparedStatement)
                .setString(
                        1,
                        "A-001");

        verify(preparedStatement)
                .executeQuery();
    }

    @Test
    void shouldProvideMeaningfulSQLExceptionWhenLookupFails()
            throws SQLException {

        when(connection.prepareStatement(
                anyString()))
                .thenThrow(
                        new SQLException(
                                "Database connection failed"));

        final SQLException exception =
                assertThrows(
                        SQLException.class,
                        () -> appointmentDAO
                                .findByAppointmentNumber(
                                        "A-001"));

        assertTrue(
                exception.getMessage()
                        .contains(
                                "Failed to find appointment"),
                "SQLException should describe the failed lookup operation");
    }
}