package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAOImpl;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@WebServlet("/appointments")
public class AppointmentController extends HttpServlet {

    private final AppointmentService appointmentService;

    public AppointmentController() {
        this.appointmentService =
                createAppointmentService();
    }

    public AppointmentController(
            final AppointmentService appointmentService) {

        this.appointmentService =
                appointmentService;
    }

    private static AppointmentService createAppointmentService() {

        try {
            return new AppointmentService(
                    new AppointmentDAOImpl());

        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Failed to initialize appointment controller",
                    exception);
        }
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final String appointmentNumber =
                request.getParameter("appointmentNumber");

        try {
            final Optional<Appointment> appointment =
                    appointmentService
                            .searchAppointment(
                                    appointmentNumber);

            final RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "/WEB-INF/views/appointment.jsp");

            if (appointment.isPresent()) {

                request.setAttribute(
                        "appointment",
                        appointment.get());

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Appointment not found");
            }

            dispatcher.forward(
                    request,
                    response);

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

            final RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "/WEB-INF/views/appointment.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to search appointment",
                    exception);
        }
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

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

        try {
            final boolean saved =
                    appointmentService
                            .saveAppointment(
                                    appointment);

            if (saved) {
                request.setAttribute(
                        "successMessage",
                        "Appointment saved successfully");
            }

            final RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "/WEB-INF/views/appointment.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

            final RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "/WEB-INF/views/appointment.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to save appointment",
                    exception);
        }
    }
}