package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.DentistDAO;
import com.sunrisedental.dao.PatientDAO;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AppointmentControllerTest {

    private AppointmentService appointmentService;
    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;
    private DentistDAO dentistDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private AppointmentController controller;

    @BeforeEach
    void setUp() {

        appointmentService =
                mock(AppointmentService.class);

        appointmentDAO =
                mock(AppointmentDAO.class);

        patientDAO =
                mock(PatientDAO.class);

        dentistDAO =
                mock(DentistDAO.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        session =
                mock(HttpSession.class);

        dispatcher =
                mock(RequestDispatcher.class);

        controller =
                new AppointmentController(
                        appointmentService);

        when(request.getRequestDispatcher(
                "/WEB-INF/views/appointment.jsp"))
                .thenReturn(dispatcher);

        when(request.getSession())
                .thenReturn(session);
    }

    @Test
    void shouldSearchAppointmentUsingRequestParameter()
            throws Exception {

        when(request.getParameter(
                "appointmentNumber"))
                .thenReturn("A-001");

        when(request.getParameter(
                "appointmentDate"))
                .thenReturn("2026-08-28");

        controller.doGet(
                request,
                response);

        verify(appointmentService)
                .searchAppointment(
                        LocalDate.of(
                                2026,
                                8,
                                28),
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

        when(request.getParameter(
                "appointmentDate"))
                .thenReturn("2026-08-28");

        when(appointmentService
                .searchAppointment(
                        LocalDate.of(
                                2026,
                                8,
                                28),
                        "A-001"))
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

        when(request.getParameter(
                "appointmentDate"))
                .thenReturn("2026-08-28");

        when(appointmentService
                .searchAppointment(
                        LocalDate.of(
                                2026,
                                8,
                                28),
                        "A-001"))
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

        when(request.getParameter(
                "appointmentDate"))
                .thenReturn("2026-08-28");

        controller.doGet(
                request,
                response);

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }

    @Test
    void shouldShowAllAppointmentsNewestFirst()
            throws Exception {

        controller =
                new AppointmentController(
                        appointmentService,
                        appointmentDAO,
                        patientDAO,
                        dentistDAO);

        final RequestDispatcher allAppointmentsDispatcher =
                mock(RequestDispatcher.class);

        final Appointment olderAppointment =
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
                        "Older appointment");

        final Appointment newerAppointment =
                new Appointment(
                        2,
                        "A-002",
                        2,
                        2,
                        LocalDate.of(
                                2026,
                                8,
                                29),
                        LocalTime.of(
                                11,
                                0),
                        "COMPLETED",
                        "Newer appointment");

        when(request.getServletPath())
                .thenReturn("/appointments/all");

        when(request.getRequestDispatcher(
                "/WEB-INF/views/appointments-all.jsp"))
                .thenReturn(allAppointmentsDispatcher);

        when(appointmentDAO.findAllAppointments())
                .thenReturn(
                        List.of(
                                olderAppointment,
                                newerAppointment));

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        eq("appointments"),
                        argThat(value -> {
                            if (!(value instanceof List<?> appointments)) {
                                return false;
                            }

                            return appointments.size() == 2
                                    && newerAppointment.equals(
                                    appointments.get(0))
                                    && olderAppointment.equals(
                                    appointments.get(1));
                        }));

        verify(allAppointmentsDispatcher)
                .forward(
                        request,
                        response);
    }

    @Test
    void shouldSaveAppointmentUsingRequestParameters()
            throws Exception {

        mockAppointmentFormParameters();

        when(appointmentService.generateNextAppointmentNumber(
                LocalDate.of(
                        2026,
                        8,
                        28)))
                .thenReturn("A-001");

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
    void shouldRedirectToBillingWhenAppointmentIsSaved()
            throws Exception {

        when(request.getServletPath())
                .thenReturn("/appointments");

        when(request.getParameter(
                "patientId"))
                .thenReturn("1");

        when(request.getParameter(
                "dentistId"))
                .thenReturn("1");

        when(request.getParameter(
                "appointmentDate"))
                .thenReturn("2026-09-01");

        when(request.getParameter(
                "appointmentTime"))
                .thenReturn("10:00");

        when(request.getParameter(
                "status"))
                .thenReturn("SCHEDULED");

        when(request.getParameter(
                "notes"))
                .thenReturn("Regular checkup");

        when(request.getParameterValues(
                "treatmentTypeIds"))
                .thenReturn(null);

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");

        when(appointmentService.generateNextAppointmentNumber(
                LocalDate.of(
                        2026,
                        9,
                        1)))
                .thenReturn("A-001");

        when(appointmentService
                .saveAppointment(
                        any(Appointment.class)))
                .thenReturn(25);

        controller.doPost(
                request,
                response);

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/bills"
                                + "?appointmentId=25"
                                + "&appointmentDate=2026-09-01"
                                + "&appointmentNumber=A-001"
                                + "&created=true");
    }

    private void mockAppointmentFormParameters() {

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

        when(request.getParameterValues(
                "treatmentTypeIds"))
                .thenReturn(null);
    }
}
