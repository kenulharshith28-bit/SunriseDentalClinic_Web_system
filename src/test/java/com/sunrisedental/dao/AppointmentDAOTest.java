package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for AppointmentDAOImpl.
 */
class AppointmentDAOTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Appointment appointment;
    private AppointmentDAO appointmentDAO;

    @BeforeEach
    void setUp() {

        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        appointment = mock(Appointment.class);

        appointmentDAO =
                new AppointmentDAOImpl(connection);
    }

    @Test
    void shouldSaveAppointmentSuccessfully()
            throws SQLException {

        when(appointment.getAppointmentNumber())
                .thenReturn("A-001");

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(1);

        final boolean result =
                appointmentDAO.saveAppointment(appointment);

        assertTrue(
                result,
                "Appointment should be saved successfully");

        verify(preparedStatement)
                .setString(1, "A-001");

        verify(preparedStatement)
                .executeUpdate();
    }

    @Test
    void shouldRejectNullAppointment() {

        assertThrows(
                IllegalArgumentException.class,
                () -> appointmentDAO.saveAppointment(null));
    }

    @Test
    void shouldProvideMeaningfulSQLExceptionWhenSaveFails()
            throws SQLException {

        when(appointment.getAppointmentNumber())
                .thenReturn("A-001");

        when(connection.prepareStatement(anyString()))
                .thenThrow(
                        new SQLException("Database connection failed"));

        final SQLException exception =
                assertThrows(
                        SQLException.class,
                        () -> appointmentDAO
                                .saveAppointment(appointment));

        assertTrue(
                exception.getMessage()
                        .contains("Failed to save appointment"),
                "SQLException should describe the failed DAO operation");
    }

    @Test
    void shouldReturnEmptyWhenAppointmentDoesNotExist()
            throws SQLException {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(false);

        final Optional<Appointment> result =
                appointmentDAO.findByAppointmentNumber(
                        "A-999");

        assertNotNull(
                result,
                "DAO should return an Optional instead of null");

        assertTrue(
                result.isEmpty(),
                "Result should be empty when appointment does not exist");

        verify(preparedStatement)
                .setString(1, "A-999");

        verify(preparedStatement)
                .executeQuery();
    }
}