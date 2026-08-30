package com.sunrisedental.controller;

import com.sunrisedental.dao.TreatmentDAO;
import com.sunrisedental.dao.TreatmentDAOImpl;
import com.sunrisedental.dao.TreatmentTypeDAO;
import com.sunrisedental.dao.TreatmentTypeDAOImpl;
import com.sunrisedental.model.Treatment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/treatments")
public class TreatmentController extends HttpServlet {

    private final TreatmentDAO treatmentDAO;
    private final TreatmentTypeDAO treatmentTypeDAO;

    public TreatmentController() {

        try {
            this.treatmentDAO =
                    new TreatmentDAOImpl();

            this.treatmentTypeDAO =
                    new TreatmentTypeDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize treatment controller",
                    exception);
        }
    }

    public TreatmentController(
            final TreatmentDAO treatmentDAO,
            final TreatmentTypeDAO treatmentTypeDAO) {

        this.treatmentDAO =
                treatmentDAO;

        this.treatmentTypeDAO =
                treatmentTypeDAO;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setAttribute(
                    "treatmentTypes",
                    treatmentTypeDAO
                            .findAllTreatmentTypes());

            forwardToPage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to load treatments",
                    exception);
        }
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final int appointmentId =
                    Integer.parseInt(
                            request.getParameter(
                                    "appointmentId"));

            final int treatmentTypeId =
                    Integer.parseInt(
                            request.getParameter(
                                    "treatmentTypeId"));

            final String description =
                    request.getParameter(
                            "description");

            final Treatment treatment =
                    new Treatment(
                            0,
                            appointmentId,
                            treatmentTypeId,
                            description
                    );

            final boolean saved =
                    treatmentDAO
                            .saveTreatment(
                                    treatment);

            if (saved) {

                request.setAttribute(
                        "successMessage",
                        "Treatment assigned successfully");
            }

            request.setAttribute(
                    "treatmentTypes",
                    treatmentTypeDAO
                            .findAllTreatmentTypes());

            forwardToPage(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Appointment and treatment IDs must be valid");

            try {
                request.setAttribute(
                        "treatmentTypes",
                        treatmentTypeDAO
                                .findAllTreatmentTypes());

            } catch (SQLException ignored) {
            }

            forwardToPage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to assign treatment",
                    exception);
        }
    }

    private void forwardToPage(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/treatment.jsp");

        dispatcher.forward(
                request,
                response);
    }
}