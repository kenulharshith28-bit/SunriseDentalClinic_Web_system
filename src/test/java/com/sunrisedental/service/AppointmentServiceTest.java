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
    void shouldReturnAppointmentWhenDateAndNumberMatch()
            throws SQLException {

        final LocalDate appointmentDate =
                LocalDate.of(
                        2026,
                        8,
                        28);

        final Appointment appointment =
                new Appointment(
                        1,
                        "A-001"
                );

        when(appointmentDAO
                .findByAppointmentDateAndNumber(
                        appointmentDate,
                        "A-001"))
                .thenReturn(
                        Optional.of(
                                appointment));

        final Optional<Appointment> result =
                appointmentService
                        .searchAppointment(
                                appointmentDate,
                                " A-001 ");

        assertTrue(
                result.isPresent(),
                "Appointment should be returned when date and number match");

        verify(appointmentDAO)
                .findByAppointmentDateAndNumber(
                        appointmentDate,
                        "A-001");
    }

    @Test
    void shouldRejectNullAppointmentDateWhenSearchingByDateAndNumber() {

        assertThrows(
                IllegalArgumentException.class,
                () -> appointmentService
                        .searchAppointment(
                                null,
                                "A-001")
        );
    }

    @Test
    void shouldGenerateNextAppointmentNumberForDate()
            throws SQLException {

        final LocalDate appointmentDate =
                LocalDate.of(
                        2026,
                        8,
                        28);

        when(appointmentDAO
                .generateNextAppointmentNumber(
                        appointmentDate))
                .thenReturn(
                        "A-004");

        final String appointmentNumber =
                appointmentService
                        .generateNextAppointmentNumber(
                                appointmentDate);

        assertEquals(
                "A-004",
                appointmentNumber);

        verify(appointmentDAO)
                .generateNextAppointmentNumber(
                        appointmentDate);
    }

    @Test
    void shouldRejectNullAppointmentDateWhenGeneratingNumber() {

        assertThrows(
                IllegalArgumentException.class,
                () -> appointmentService
                        .generateNextAppointmentNumber(
                                null)
        );
    }

    @Test
    void shouldSaveAppointmentSuccessfully()
            throws SQLException {

        final Appointment appointment =
                createValidAppointment();

        final int generatedAppointmentId =
                25;

        when(appointmentDAO
                .saveAppointment(
                        appointment))
                .thenReturn(
                        generatedAppointmentId);

        final int result =
                appointmentService
                        .saveAppointment(
                                appointment);

        assertEquals(
                generatedAppointmentId,
                result,
                "Generated appointment ID should be returned");

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