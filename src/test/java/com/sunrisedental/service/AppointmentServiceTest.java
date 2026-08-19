package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for AppointmentService.
 */
class AppointmentServiceTest {

    private AppointmentDAO appointmentDAO;
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {

        appointmentDAO = mock(AppointmentDAO.class);

        appointmentService =
                new AppointmentService(appointmentDAO);
    }

    @Test
    void shouldReturnAppointmentWhenAppointmentExists()
            throws SQLException {

        // ARRANGE
        final Appointment appointment =
                new Appointment(
                        1,
                        "A-001"
                );

        when(appointmentDAO
                .findByAppointmentNumber("A-001"))
                .thenReturn(
                        Optional.of(appointment));

        // ACT
        final Optional<Appointment> result =
                appointmentService
                        .searchAppointment("A-001");

        // ASSERT
        assertTrue(
                result.isPresent(),
                "Appointment should be returned when it exists");

        assertEquals(
                "A-001",
                result.get()
                        .getAppointmentNumber());

        verify(appointmentDAO)
                .findByAppointmentNumber(
                        "A-001");
    }

    @Test
    void shouldRejectBlankAppointmentNumber() {

        // ARRANGE
        final String appointmentNumber = "   ";

        // ACT & ASSERT
        assertThrows(
                IllegalArgumentException.class,
                () -> appointmentService
                        .searchAppointment(
                                appointmentNumber)
        );
    }

    @Test
    void shouldSaveAppointmentSuccessfully()
            throws SQLException {

        // ARRANGE
        final Appointment appointment =
                new Appointment(
                        1,
                        "A-001"
                );

        when(appointmentDAO
                .saveAppointment(appointment))
                .thenReturn(true);

        // ACT
        final boolean result =
                appointmentService
                        .saveAppointment(appointment);

        // ASSERT
        assertTrue(
                result,
                "Appointment should be saved successfully");

        verify(appointmentDAO)
                .saveAppointment(appointment);
    }

    @Test
    void shouldRejectNullAppointmentBeforeSaving() {

        assertThrows(
                IllegalArgumentException.class,
                () -> appointmentService
                        .saveAppointment(null)
        );
    }

    @Test
    void shouldProvideMeaningfulSQLExceptionWhenAppointmentSearchFails()
            throws SQLException {

        // ARRANGE
        when(appointmentDAO
                .findByAppointmentNumber("A-001"))
                .thenThrow(
                        new SQLException(
                                "Database connection failed"));

        // ACT
        final SQLException exception =
                assertThrows(
                        SQLException.class,
                        () -> appointmentService
                                .searchAppointment(
                                        "A-001"));

        // ASSERT
        assertTrue(
                exception.getMessage()
                        .contains(
                                "Failed to search appointment"),
                "SQLException should describe the failed appointment search");
    }
}