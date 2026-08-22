package com.sunrisedental.service;

import com.sunrisedental.report.ReportGenerator;
import com.sunrisedental.report.ReportGeneratorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportServiceTest {

    private ReportGeneratorFactory reportGeneratorFactory;
    private ReportGenerator reportGenerator;
    private ReportService reportService;

    @BeforeEach
    void setUp() {

        reportGeneratorFactory =
                mock(ReportGeneratorFactory.class);

        reportGenerator =
                mock(ReportGenerator.class);

        reportService =
                new ReportService(
                        reportGeneratorFactory);
    }

    @Test
    void shouldGenerateRequestedReport() {

        when(reportGeneratorFactory
                .create("appointment"))
                .thenReturn(reportGenerator);

        when(reportGenerator.generate())
                .thenReturn("Appointment Report");

        final String result =
                reportService.generateReport(
                        "appointment");

        assertEquals(
                "Appointment Report",
                result);
    }
}