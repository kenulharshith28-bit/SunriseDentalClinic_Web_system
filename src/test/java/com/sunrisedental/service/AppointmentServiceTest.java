package com.sunrisedental.service;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.model.Appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AppointmentServiceTest {

    private AppointmentDAO appointmentDAO;
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {

        appointmentDAO =
                mock(AppointmentDAO.class);

        appointmentService =
                new AppointmentService(
                        appointmentDAO);
    }

    @Test
    void shouldReturnAppointmentWhenAppointmentExists()
            throws SQLException {

        final Appointment appointment =
                new Appointment(
                        1,
                        "A-001"
                );

        when(appointmentDAO
                .findByAppointmentNumber(
                        "A-001"))
                .thenReturn(
                        Optional.of(
                                appointment));

        final Optional<Appointment> result =
                appointmentService
                        .searchAppointment(
                                "A-001");

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

        final String appointmentNumber =
                "   ";

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

        final Appointment appointment =
                createValidAppointment();

        when(appointmentDAO
                .saveAppointment(
                        appointment))
                .thenReturn(true);

        final boolean result =
                appointmentService
                        .saveAppointment(
                                appointment);

        assertTrue(
                result,
                "Appointment should be saved successfully");

        verify(appointmentDAO)
                .saveAppointment(
                        appointment);
    }

    @Test
    void shouldRejectNullAppointmentBeforeSaving() {

        assertThrows(
                IllegalArgumentException.class,
                () -> appointmentService
                        .saveAppointment(
                                null)
        );
    }

    @Test
    void shouldProvideMeaningfulSQLExceptionWhenAppointmentSearchFails()
            throws SQLException {

        when(appointmentDAO
                .findByAppointmentNumber(
                        "A-001"))
                .thenThrow(
                        new SQLException(
                                "Database connection failed"));

        final SQLException exception =
                assertThrows(
                        SQLException.class,
                        () -> appointmentService
                                .searchAppointment(
                                        "A-001"));

        assertTrue(
                exception.getMessage()
                        .contains(
                                "Failed to search appointment"),
                "SQLException should describe the failed appointment search");
    }

    @Test
    void shouldProvideMeaningfulSQLExceptionWhenAppointmentSaveFails()
            throws SQLException {

        final Appointment appointment =
                createValidAppointment();

        when(appointmentDAO
                .saveAppointment(
                        appointment))
                .thenThrow(
                        new SQLException(
                                "Database connection failed"));

        final SQLException exception =
                assertThrows(
                        SQLException.class,
                        () -> appointmentService
                                .saveAppointment(
                                        appointment));

        assertTrue(
                exception.getMessage()
                        .contains(
                                "Failed to save appointment"),
                "SQLException should describe the failed appointment save");
    }

    private Appointment createValidAppointment() {

        return new Appointment(
                1,
                "A-001",
                1,
                1,
                LocalDate.of(
                        2026,
                        8,
                        29),
                LocalTime.of(
                        10,
                        0),
                "SCHEDULED",
                "Regular checkup"
        );
    }
}