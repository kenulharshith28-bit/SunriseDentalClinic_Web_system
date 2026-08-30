package com.sunrisedental.controller;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.dao.UserDAOImpl;
import com.sunrisedental.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/users/register")
public class RegisterStaffController extends HttpServlet {

    private final UserDAO userDAO;

    public RegisterStaffController() {

        try {
            this.userDAO =
                    new UserDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize staff registration controller",
                    exception);
        }
    }

    public RegisterStaffController(
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

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/register-staff.jsp");

        dispatcher.forward(
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

        final String username =
                request.getParameter(
                        "username");

        final String password =
                request.getParameter(
                        "password");

        if (username == null
                || username.isBlank()
                || password == null
                || password.isBlank()) {

            request.setAttribute(
                    "errorMessage",
                    "Username and password are required");

            forwardToPage(
                    request,
                    response);

            return;
        }

        final User user =
                new User(
                        0,
                        username,
                        password,
                        "RECEPTIONIST"
                );

        try {

            final boolean saved =
                    userDAO.saveUser(
                            user);

            if (saved) {

                request.setAttribute(
                        "successMessage",
                        "Staff user registered successfully");
            }

            forwardToPage(
                    request,
                    response);

        } catch (SQLException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Unable to register staff user");

            forwardToPage(
                    request,
                    response);
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
                        "/WEB-INF/views/register-staff.jsp");

        dispatcher.forward(
                request,
                response);
    }
}