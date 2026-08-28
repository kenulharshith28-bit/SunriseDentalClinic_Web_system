package com.sunrisedental.controller;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
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
                .thenReturn(
                        Optional.empty());

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

    @Test
    void shouldSaveAppointmentUsingRequestParameters()
            throws Exception {

        mockAppointmentFormParameters();

        controller.doPost(
                request,
                response);

        verify(appointmentService)
                .saveAppointment(
                        argThat(appointment ->
                                appointment != null
                                        && "A-001".equals(
                                        appointment
                                                .getAppointmentNumber())
                                        && appointment
                                        .getPatientId() == 1
                                        && appointment
                                        .getDentistId() == 1
                                        && "2026-08-28".equals(
                                        appointment
                                                .getAppointmentDate()
                                                .toString())
                                        && "10:00".equals(
                                        appointment
                                                .getAppointmentTime()
                                                .toString())
                                        && "SCHEDULED".equals(
                                        appointment
                                                .getStatus())
                                        && "Regular checkup".equals(
                                        appointment
                                                .getNotes())
                        ));
    }

    @Test
    void shouldShowSuccessMessageWhenAppointmentIsSaved()
            throws Exception {

        mockAppointmentFormParameters();

        when(appointmentService
                .saveAppointment(
                        any(Appointment.class)))
                .thenReturn(true);

        controller.doPost(
                request,
                response);

        verify(request)
                .setAttribute(
                        "successMessage",
                        "Appointment saved successfully");

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }

    private void mockAppointmentFormParameters() {

        when(request.getParameter(
                "appointmentNumber"))
                .thenReturn("A-001");

        when(request.getParameter(
                "patientId"))
                .thenReturn("1");

        when(request.getParameter(
                "dentistId"))
                .thenReturn("1");

        when(request.getParameter(
                "appointmentDate"))
                .thenReturn("2026-08-28");

        when(request.getParameter(
                "appointmentTime"))
                .thenReturn("10:00");

        when(request.getParameter(
                "status"))
                .thenReturn("SCHEDULED");

        when(request.getParameter(
                "notes"))
                .thenReturn("Regular checkup");
    }
}