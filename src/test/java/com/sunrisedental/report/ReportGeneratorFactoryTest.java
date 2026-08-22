package com.sunrisedental.report;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReportGeneratorFactoryTest {

    @Test
    void shouldCreateAppointmentReportGenerator() {

        final ReportGeneratorFactory factory =
                new ReportGeneratorFactory();

        final ReportGenerator generator =
                factory.create("appointment");

        assertNotNull(
                generator,
                "Factory should create a report generator");

        assertInstanceOf(
                AppointmentReportGenerator.class,
                generator);
    }
}