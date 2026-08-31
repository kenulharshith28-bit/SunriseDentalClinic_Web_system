package com.sunrisedental.controller;

import com.sunrisedental.dao.DentistDAO;
import com.sunrisedental.dao.DentistDAOImpl;
import com.sunrisedental.model.Dentist;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/dentists")
public class DentistController extends HttpServlet {

    private DentistDAO dentistDAO;

    @Override
    public void init()
            throws ServletException {

        try {

            dentistDAO =
                    new DentistDAOImpl();

        } catch (SQLException exception) {

            throw new ServletException(
                    "Failed to initialize DentistDAO",
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

            final List<Dentist> dentists =
                    dentistDAO.findAllDentists();

            request.setAttribute(
                    "dentists",
                    dentists);

            request.getRequestDispatcher(
                            "/WEB-INF/views/dentists.jsp")
                    .forward(
                            request,
                            response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Failed to load dentists",
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

            final String dentistIdValue =
                    request.getParameter("dentistId");

            try {

                final int dentistId =
                        Integer.parseInt(dentistIdValue);

                final boolean deleted =
                        dentistDAO.deleteDentist(
                                dentistId);

                if (deleted) {

                    request.setAttribute(
                            "successMessage",
                            "Dentist removed successfully");

                } else {

                    request.setAttribute(
                            "errorMessage",
                            "Dentist was not found");
                }

            } catch (NumberFormatException exception) {

                request.setAttribute(
                        "errorMessage",
                        "Invalid dentist ID");

            } catch (SQLException exception) {

                request.setAttribute(
                        "errorMessage",
                        "Dentist cannot be removed because appointments are linked to this dentist");
            }

            doGet(
                    request,
                    response);

            return;
        }

        final String firstName =
                request.getParameter(
                        "firstName");

        final String lastName =
                request.getParameter(
                        "lastName");

        final String specialization =
                request.getParameter(
                        "specialization");

        final String phone =
                request.getParameter(
                        "phone");

        final String email =
                request.getParameter(
                        "email");

        if (firstName == null
                || firstName.isBlank()
                || lastName == null
                || lastName.isBlank()) {

            request.setAttribute(
                    "errorMessage",
                    "First name and last name are required");

            doGet(
                    request,
                    response);

            return;
        }

        final Dentist dentist =
                new Dentist(
                        0,
                        firstName.trim(),
                        lastName.trim(),
                        specialization,
                        phone,
                        email
                );

        try {

            dentistDAO.saveDentist(
                    dentist);

            request.setAttribute(
                    "successMessage",
                    "Dentist added successfully");

            doGet(
                    request,
                    response);

        } catch (SQLException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Failed to add dentist");

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
