package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;
import com.sunrisedental.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ReportControllerTest {

    private ReportService reportService;
    private AppointmentDAO appointmentDAO;
    private BillDAO billDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ReportController controller;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {

        reportService =
                mock(ReportService.class);

        appointmentDAO =
                mock(AppointmentDAO.class);

        billDAO =
                mock(BillDAO.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        dispatcher =
                mock(RequestDispatcher.class);

        controller =
                new ReportController(
                        reportService,
                        appointmentDAO,
                        billDAO);

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

        when(appointmentDAO.findAllAppointments())
                .thenReturn(
                        List.of());

        controller.doGet(
                request,
                response);

        verify(appointmentDAO)
                .findAllAppointments();
    }

    @Test
    void shouldDisplayGeneratedReportOnPage()
            throws Exception {

        final Appointment appointment =
                new Appointment(
                        1,
                        "A-001",
                        1,
                        1,
                        LocalDate.of(
                                2026,
                                8,
                                28),
                        LocalTime.of(
                                10,
                                0),
                        "SCHEDULED",
                        "Regular checkup"
                );

        final List<Appointment> appointments =
                List.of(
                        appointment);

        when(request.getParameter(
                "reportType"))
                .thenReturn("appointment");

        when(appointmentDAO.findAllAppointments())
                .thenReturn(
                        appointments);

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        "appointments",
                        appointments);

        verify(request)
                .setAttribute(
                        "reportType",
                        "appointment");

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }

    @Test
    void shouldShowErrorForUnsupportedReportType()
            throws Exception {

        when(request.getParameter(
                "reportType"))
                .thenReturn("unknown");

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Unsupported report type");

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }

    @Test
    void shouldDisplayBillingReportOnPage()
            throws Exception {

        final List<Bill> bills =
                List.of(
                        new Bill(
                                1,
                                4,
                                new BigDecimal("3500.00")));

        when(request.getParameter(
                "reportType"))
                .thenReturn("bill");

        when(billDAO.findAllBills())
                .thenReturn(
                        bills);

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        "bills",
                        bills);

        verify(request)
                .setAttribute(
                        "reportType",
                        "bill");

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }
}
