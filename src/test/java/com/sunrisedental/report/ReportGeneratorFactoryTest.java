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

    @Test
    void shouldCreateBillReportGenerator() {

        final ReportGeneratorFactory factory =
                new ReportGeneratorFactory();

        final ReportGenerator generator =
                factory.create("bill");

        assertNotNull(
                generator,
                "Factory should create a bill report generator");

        assertInstanceOf(
                BillReportGenerator.class,
                generator);
    }
}