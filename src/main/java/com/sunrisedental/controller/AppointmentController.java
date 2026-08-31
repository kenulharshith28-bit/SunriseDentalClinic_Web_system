package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAOImpl;
import com.sunrisedental.dao.DentistDAO;
import com.sunrisedental.dao.DentistDAOImpl;
import com.sunrisedental.dao.PatientDAO;
import com.sunrisedental.dao.PatientDAOImpl;
import com.sunrisedental.model.Dentist;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Patient;

import com.sunrisedental.service.AppointmentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/appointments")
public class AppointmentController extends HttpServlet {

    private final AppointmentService appointmentService;
    private final PatientDAO patientDAO;
    private final DentistDAO dentistDAO;

    public AppointmentController() {

        try {
            this.appointmentService =
                    new AppointmentService(
                            new AppointmentDAOImpl());

            this.patientDAO =
                    new PatientDAOImpl();

            this.dentistDAO =
                    new DentistDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize appointment controller",
                    exception);
        }
    }

    public AppointmentController(
            final AppointmentService appointmentService) {

        this.appointmentService =
                appointmentService;

        this.patientDAO = null;
        this.dentistDAO = null;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final String appointmentNumber =
                request.getParameter(
                        "appointmentNumber");

        try {

            loadDropdownData(request);

            if (appointmentNumber == null) {

                forwardToAppointmentPage(
                        request,
                        response);

                return;
            }

            if (appointmentNumber.isBlank()) {

                request.setAttribute(
                        "errorMessage",
                        "Appointment number must not be blank");

                forwardToAppointmentPage(
                        request,
                        response);

                return;
            }

            final Optional<Appointment> appointment =
                    appointmentService
                            .searchAppointment(
                                    appointmentNumber);

            if (appointment.isPresent()) {

                request.setAttribute(
                        "appointment",
                        appointment.get());

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Appointment not found");
            }

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to load appointment page",
                    exception);
        }
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final String appointmentNumber =
                    request.getParameter(
                            "appointmentNumber");

            final int patientId =
                    Integer.parseInt(
                            request.getParameter(
                                    "patientId"));

            final int dentistId =
                    Integer.parseInt(
                            request.getParameter(
                                    "dentistId"));

            final LocalDate appointmentDate =
                    LocalDate.parse(
                            request.getParameter(
                                    "appointmentDate"));

            final LocalTime appointmentTime =
                    LocalTime.parse(
                            request.getParameter(
                                    "appointmentTime"));

            final String status =
                    request.getParameter(
                            "status");

            final String notes =
                    request.getParameter(
                            "notes");

            final Appointment appointment =
                    new Appointment(
                            0,
                            appointmentNumber,
                            patientId,
                            dentistId,
                            appointmentDate,
                            appointmentTime,
                            status,
                            notes
                    );

            final boolean saved =
                    appointmentService
                            .saveAppointment(
                                    appointment);

            if (saved) {

                request.setAttribute(
                        "successMessage",
                        "Appointment saved successfully");
            }

            loadDropdownData(request);

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Patient ID and Dentist ID must be valid");

            loadDropdownDataSafely(request);

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (SQLIntegrityConstraintViolationException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Appointment number already exists");

            loadDropdownDataSafely(request);

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

            loadDropdownDataSafely(request);

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to save appointment",
                    exception);
        }
    }

    private void loadDropdownData(
            final HttpServletRequest request)
            throws SQLException {

        if (patientDAO == null
                || dentistDAO == null) {

            return;
        }

        final List<Patient> patients =
                patientDAO.findAllPatients();

        final List<Dentist> dentists =
                dentistDAO.findAllDentists();

        request.setAttribute(
                "patients",
                patients);

        request.setAttribute(
                "dentists",
                dentists);
    }

    private void loadDropdownDataSafely(
            final HttpServletRequest request) {

        try {

            loadDropdownData(request);

        } catch (SQLException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Unable to load patient or dentist data");
        }
    }

    private void forwardToAppointmentPage(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/appointment.jsp");

        dispatcher.forward(
                request,
                response);
    }
}