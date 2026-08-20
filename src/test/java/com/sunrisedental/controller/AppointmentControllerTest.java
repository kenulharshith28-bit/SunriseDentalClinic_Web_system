package com.sunrisedental.controller;


import com.sunrisedental.model.Appointment;
import javax.servlet.RequestDispatcher;
import java.util.Optional;
import com.sunrisedental.service.AppointmentService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AppointmentControllerTest {

    private AppointmentService appointmentService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private AppointmentController controller;

    @BeforeEach
    void setUp() {

        appointmentService =
                mock(AppointmentService.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        dispatcher =
                mock(RequestDispatcher.class);

        controller =
                new AppointmentController(
                        appointmentService);

        when(request.getRequestDispatcher(
                "/WEB-INF/views/appointment.jsp"))
                .thenReturn(dispatcher);
    }

    @Test
    void shouldSearchAppointmentUsingRequestParameter()
            throws Exception {

        when(request.getParameter(
                "appointmentNumber"))
                .thenReturn("A-001");

        controller.doGet(
                request,
                response);

        verify(appointmentService)
                .searchAppointment(
                        "A-001");
    }

    @Test
    void shouldShowFoundAppointmentOnPage()
            throws Exception {

        final Appointment appointment =
                new Appointment(
                        1,
                        "A-001"
                );

        when(request.getParameter(
                "appointmentNumber"))
                .thenReturn("A-001");

        when(appointmentService
                .searchAppointment("A-001"))
                .thenReturn(
                        Optional.of(appointment));

        when(request.getRequestDispatcher(
                "/WEB-INF/views/appointment.jsp"))
                .thenReturn(dispatcher);

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        "appointment",
                        appointment);

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }

    @Test
    void shouldShowMessageWhenAppointmentDoesNotExist()
            throws Exception {

        when(request.getParameter(
                "appointmentNumber"))
                .thenReturn("A-001");

        when(appointmentService
                .searchAppointment("A-001"))
                .thenReturn(Optional.empty());

        when(request.getRequestDispatcher(
                "/WEB-INF/views/appointment.jsp"))
                .thenReturn(dispatcher);

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Appointment not found");

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }

    @Test
    void shouldShowValidationMessageForBlankAppointmentNumber()
            throws Exception {

        when(request.getParameter(
                "appointmentNumber"))
                .thenReturn("");

        when(appointmentService
                .searchAppointment(""))
                .thenThrow(
                        new IllegalArgumentException(
                                "Appointment number must not be blank"));

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Appointment number must not be blank");

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }
}