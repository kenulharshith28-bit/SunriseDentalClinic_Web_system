package com.sunrisedental.controller;

import com.sunrisedental.dao.PatientDAO;
import com.sunrisedental.dao.PatientDAOImpl;
import com.sunrisedental.model.Patient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/patients/new")
public class PatientController extends HttpServlet {

    private final PatientDAO patientDAO;

    public PatientController() {

        try {
            this.patientDAO =
                    new PatientDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize patient controller",
                    exception);
        }
    }

    public PatientController(
            final PatientDAO patientDAO) {

        this.patientDAO =
                patientDAO;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/patient-form.jsp");

        dispatcher.forward(
                request,
                response);
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final String firstName =
                request.getParameter(
                        "firstName");

        final String lastName =
                request.getParameter(
                        "lastName");

        final String phone =
                request.getParameter(
                        "phone");

        if (firstName == null
                || firstName.isBlank()
                || lastName == null
                || lastName.isBlank()) {

            request.setAttribute(
                    "errorMessage",
                    "First name and last name are required");

            final RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "/WEB-INF/views/patient-form.jsp");

            dispatcher.forward(
                    request,
                    response);

            return;
        }

        final Patient patient =
                new Patient(
                        0,
                        firstName,
                        lastName,
                        phone
                );

        try {

            final boolean saved =
                    patientDAO
                            .savePatient(
                                    patient);

            if (saved) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/appointments");

                return;
            }

            request.setAttribute(
                    "errorMessage",
                    "Patient could not be saved");

            final RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "/WEB-INF/views/patient-form.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to save patient",
                    exception);
        }
    }
}