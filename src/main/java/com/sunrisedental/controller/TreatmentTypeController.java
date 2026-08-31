package com.sunrisedental.controller;

import com.sunrisedental.dao.TreatmentTypeDAO;
import com.sunrisedental.dao.TreatmentTypeDAOImpl;
import com.sunrisedental.model.TreatmentType;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/treatment-types")
public class TreatmentTypeController extends HttpServlet {

    private TreatmentTypeDAO treatmentTypeDAO;

    @Override
    public void init()
            throws ServletException {

        try {

            treatmentTypeDAO =
                    new TreatmentTypeDAOImpl();

        } catch (SQLException exception) {

            throw new ServletException(
                    "Failed to initialize TreatmentTypeDAO",
                    exception);
        }
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard");

            return;
        }

        try {

            final List<TreatmentType> treatmentTypes =
                    treatmentTypeDAO.findAllTreatmentTypes();

            request.setAttribute(
                    "treatmentTypes",
                    treatmentTypes);

            request.getRequestDispatcher(
                            "/WEB-INF/views/treatment-types.jsp")
                    .forward(
                            request,
                            response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Failed to load treatment types",
                    exception);
        }
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard");

            return;
        }

        final String action =
                request.getParameter("action");

        if ("delete".equals(action)) {

            final String treatmentTypeIdValue =
                    request.getParameter("treatmentTypeId");

            try {

                final int treatmentTypeId =
                        Integer.parseInt(
                                treatmentTypeIdValue);

                final boolean deleted =
                        treatmentTypeDAO.deleteTreatmentType(
                                treatmentTypeId);

                if (deleted) {

                    request.setAttribute(
                            "successMessage",
                            "Treatment type removed successfully");

                } else {

                    request.setAttribute(
                            "errorMessage",
                            "Treatment type was not found");
                }

            } catch (NumberFormatException exception) {

                request.setAttribute(
                        "errorMessage",
                        "Invalid treatment type ID");

            } catch (SQLException exception) {

                request.setAttribute(
                        "errorMessage",
                        "Treatment type cannot be removed because treatments are linked to it");
            }

            doGet(
                    request,
                    response);

            return;
        }

        final String treatmentName =
                request.getParameter(
                        "treatmentName");

        final String treatmentFeeValue =
                request.getParameter(
                        "treatmentFee");

        if (treatmentName == null
                || treatmentName.isBlank()) {

            request.setAttribute(
                    "errorMessage",
                    "Treatment name is required");

            doGet(
                    request,
                    response);

            return;
        }

        if (treatmentFeeValue == null
                || treatmentFeeValue.isBlank()) {

            request.setAttribute(
                    "errorMessage",
                    "Treatment fee is required");

            doGet(
                    request,
                    response);

            return;
        }

        try {

            final BigDecimal treatmentFee =
                    new BigDecimal(
                            treatmentFeeValue);

            if (treatmentFee.compareTo(
                    BigDecimal.ZERO) < 0) {

                request.setAttribute(
                        "errorMessage",
                        "Treatment fee cannot be negative");

                doGet(
                        request,
                        response);

                return;
            }

            final TreatmentType treatmentType =
                    new TreatmentType(
                            0,
                            treatmentName.trim(),
                            treatmentFee
                    );

            treatmentTypeDAO.saveTreatmentType(
                    treatmentType);

            request.setAttribute(
                    "successMessage",
                    "Treatment type added successfully");

            doGet(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Treatment fee must be a valid number");

            doGet(
                    request,
                    response);

        } catch (SQLException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Failed to add treatment type");

            doGet(
                    request,
                    response);
        }
    }

    private boolean isAdmin(
            final HttpServletRequest request) {

        final HttpSession session =
                request.getSession(
                        false);

        if (session == null) {
            return false;
        }

        final String role =
                (String) session.getAttribute(
                        "role");

        return "ADMIN".equals(
                role);
    }
}
