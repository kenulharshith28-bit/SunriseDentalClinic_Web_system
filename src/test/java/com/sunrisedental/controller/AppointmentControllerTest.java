package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AppointmentControllerTest {

    private AppointmentService appointmentService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AppointmentController controller;

    @BeforeEach
    void setUp() {

        appointmentService =
                mock(AppointmentService.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        controller =
                new AppointmentController(
                        appointmentService);
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
}