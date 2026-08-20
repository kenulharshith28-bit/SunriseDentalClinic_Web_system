package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;

import com.sunrisedental.model.Appointment;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.sql.SQLException;
import java.util.Optional;

import java.io.IOException;

public class AppointmentController extends HttpServlet {

    private final AppointmentService appointmentService;

    public AppointmentController(
            final AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
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
                request.getParameter("appointmentNumber");

        final Appointment appointment =
                new Appointment(
                        0,
                        appointmentNumber);

        try {
            appointmentService
                    .saveAppointment(appointment);

        } catch (SQLException exception) {
            throw new ServletException(
                    "Unable to save appointment",
                    exception);
        }
    }
}