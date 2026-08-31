package com.sunrisedental.report;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.BillDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;

class ReportGeneratorFactoryTest {

    private AppointmentDAO appointmentDAO;
    private BillDAO billDAO;
    private ReportGeneratorFactory factory;

    @BeforeEach
    void setUp() {

        appointmentDAO =
                mock(AppointmentDAO.class);

        billDAO =
                mock(BillDAO.class);

        factory =
                new ReportGeneratorFactory(
                        appointmentDAO,
                        billDAO);
    }

    @Test
    void shouldCreateAppointmentReportGenerator() {

        final ReportGenerator generator =
                factory.create(
                        "appointment");

        assertInstanceOf(
                AppointmentReportGenerator.class,
                generator);
    }

    @Test
    void shouldCreateBillReportGenerator() {

        final ReportGenerator generator =
                factory.create(
                        "bill");

        assertInstanceOf(
                BillReportGenerator.class,
                generator);
    }
}