package com.sunrisedental.controller;

import com.sunrisedental.dao.PatientDAO;
import com.sunrisedental.model.Patient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientManagementControllerTest {

    private PatientDAO patientDAO;
    private PatientManagementController controller;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {

        patientDAO =
                mock(PatientDAO.class);

        controller =
                new PatientManagementController(
                        patientDAO);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        session =
                mock(HttpSession.class);

        dispatcher =
                mock(RequestDispatcher.class);

        when(request.getSession(false))
                .thenReturn(session);

        when(request.getSession())
                .thenReturn(session);

        when(session.getAttribute("role"))
                .thenReturn("ADMIN");

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");
    }

    @Test
    void shouldLoadManagePatientsPage()
            throws Exception {

        final Patient patient =
                new Patient(
                        1,
                        "John",
                        "Silva",
                        "0771234567",
                        "john@email.com",
                        LocalDate.of(
                                1995,
                                5,
                                10),
                        "Colombo"
                );

        when(request.getServletPath())
                .thenReturn("/patients/manage");

        when(patientDAO.findAllPatients())
                .thenReturn(
                        List.of(patient));

        when(request.getRequestDispatcher(
                "/WEB-INF/views/manage-patients.jsp"))
                .thenReturn(dispatcher);

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        eq("patients"),
                        eq(List.of(patient)));

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }

    @Test
    void shouldRejectNonAdminUser()
            throws Exception {

        when(session.getAttribute("role"))
                .thenReturn("RECEPTIONIST");

        when(request.getServletPath())
                .thenReturn("/patients/manage");

        controller.doGet(
                request,
                response);

        verify(response)
                .sendError(
                        HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void shouldLoadPatientForEditing()
            throws Exception {

        final Patient patient =
                new Patient(
                        2,
                        "Nimal",
                        "Perera",
                        "0711234567",
                        "nimal@email.com",
                        LocalDate.of(
                                1990,
                                1,
                                20),
                        "Kandy"
                );

        when(request.getServletPath())
                .thenReturn("/patients/edit");

        when(request.getParameter("patientId"))
                .thenReturn("2");

        when(patientDAO.findById(2))
                .thenReturn(
                        Optional.of(patient));

        when(request.getRequestDispatcher(
                "/WEB-INF/views/edit-patient.jsp"))
                .thenReturn(dispatcher);

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        "patient",
                        patient);

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }

    @Test
    void shouldUpdatePatient()
            throws Exception {

        when(request.getServletPath())
                .thenReturn("/patients/edit");

        when(request.getParameter("patientId"))
                .thenReturn("3");

        when(request.getParameter("firstName"))
                .thenReturn("Kamal");

        when(request.getParameter("lastName"))
                .thenReturn("Fernando");

        when(request.getParameter("phone"))
                .thenReturn("0779999999");

        when(request.getParameter("email"))
                .thenReturn("kamal@email.com");

        when(request.getParameter("dateOfBirth"))
                .thenReturn("1998-02-10");

        when(request.getParameter("address"))
                .thenReturn("Galle");

        when(patientDAO.updatePatient(
                any(Patient.class)))
                .thenReturn(true);

        controller.doPost(
                request,
                response);

        verify(patientDAO)
                .updatePatient(
                        any(Patient.class));

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/patients/manage");
    }

    @Test
    void shouldDeletePatient()
            throws Exception {

        when(request.getServletPath())
                .thenReturn("/patients/delete");

        when(request.getParameter("patientId"))
                .thenReturn("4");

        when(patientDAO.deletePatient(4))
                .thenReturn(true);

        controller.doPost(
                request,
                response);

        verify(patientDAO)
                .deletePatient(4);

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/patients/manage");
    }
}