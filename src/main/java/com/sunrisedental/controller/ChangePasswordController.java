package com.sunrisedental.controller;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.dao.UserDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/users/change-password")
public class ChangePasswordController extends HttpServlet {

    private final UserDAO userDAO;

    public ChangePasswordController() {

        try {
            this.userDAO =
                    new UserDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize change password controller",
                    exception);
        }
    }

    public ChangePasswordController(
            final UserDAO userDAO) {

        this.userDAO =
                userDAO;
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

        forwardToPage(
                request,
                response);
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

        final String newPassword =
                request.getParameter(
                        "newPassword");

        final String confirmPassword =
                request.getParameter(
                        "confirmPassword");

        if (newPassword == null
                || newPassword.isBlank()) {

            request.setAttribute(
                    "errorMessage",
                    "New password must not be blank");

            forwardToPage(
                    request,
                    response);

            return;
        }

        if (!newPassword.equals(
                confirmPassword)) {

            request.setAttribute(
                    "errorMessage",
                    "Passwords do not match");

            forwardToPage(
                    request,
                    response);

            return;
        }

        final HttpSession session =
                request.getSession(false);

        final int userId =
                (Integer) session.getAttribute(
                        "userId");

        try {

            final boolean updated =
                    userDAO.updatePassword(
                            userId,
                            newPassword);

            if (updated) {

                request.setAttribute(
                        "successMessage",
                        "Password changed successfully");

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Password could not be changed");
            }

            forwardToPage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to change password",
                    exception);
        }
    }

    private boolean isAdmin(
            final HttpServletRequest request) {

        final HttpSession session =
                request.getSession(false);

        if (session == null) {
            return false;
        }

        final String role =
                (String) session.getAttribute(
                        "role");

        return "ADMIN".equals(role);
    }

    private void forwardToPage(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/change-password.jsp");

        dispatcher.forward(
                request,
                response);
    }
}