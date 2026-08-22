package com.sunrisedental.controller;

import com.sunrisedental.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportControllerTest {

    private ReportService reportService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ReportController controller;

    @BeforeEach
    void setUp() {

        reportService =
                mock(ReportService.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        controller =
                new ReportController(
                        reportService);
    }

    @Test
    void shouldGenerateReportUsingRequestParameter()
            throws Exception {

        when(request.getParameter(
                "reportType"))
                .thenReturn("appointment");

        controller.doGet(
                request,
                response);

        verify(reportService)
                .generateReport(
                        "appointment");
    }
}