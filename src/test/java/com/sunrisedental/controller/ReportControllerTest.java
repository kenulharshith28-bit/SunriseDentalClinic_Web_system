package com.sunrisedental.controller;

import com.sunrisedental.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ReportControllerTest {

    private ReportService reportService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ReportController controller;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {

        reportService =
                mock(ReportService.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        dispatcher =
                mock(RequestDispatcher.class);

        controller =
                new ReportController(
                        reportService);

        when(request.getRequestDispatcher(
                "/WEB-INF/views/report.jsp"))
                .thenReturn(dispatcher);
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

    @Test
    void shouldDisplayGeneratedReportOnPage()
            throws Exception {

        when(request.getParameter(
                "reportType"))
                .thenReturn("appointment");

        when(reportService.generateReport(
                "appointment"))
                .thenReturn(
                        "Appointment Report");

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        "report",
                        "Appointment Report");

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }
}