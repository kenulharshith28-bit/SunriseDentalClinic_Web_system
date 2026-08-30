package com.sunrisedental.controller;

import com.sunrisedental.dao.UserDAOImpl;
import com.sunrisedental.model.User;
import com.sunrisedental.service.AuthenticationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final AuthenticationService authenticationService;

    public LoginController() {

        this.authenticationService =
                createAuthenticationService();
    }

    public LoginController(
            final AuthenticationService authenticationService) {

        this.authenticationService =
                authenticationService;
    }

    private static AuthenticationService
    createAuthenticationService() {

        try {

            return new AuthenticationService(
                    new UserDAOImpl());

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize login controller",
                    exception);
        }
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/login.jsp");

        dispatcher.forward(
                request,
                response);
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final String username =
                request.getParameter(
                        "username");

        final String password =
                request.getParameter(
                        "password");

        try {

            final Optional<User> authenticatedUser =
                    authenticationService
                            .authenticateUser(
                                    username,
                                    password);

            if (authenticatedUser.isPresent()) {

                final User user =
                        authenticatedUser.get();

                final HttpSession session =
                        request.getSession();

                session.setAttribute(
                        "userId",
                        user.getUserId());

                session.setAttribute(
                        "username",
                        user.getUsername());

                session.setAttribute(
                        "role",
                        user.getRole());

                response.sendRedirect(
                        request.getContextPath()
                                + "/dashboard");

                return;
            }

            request.setAttribute(
                    "errorMessage",
                    "Invalid username or password");

            forwardToLogin(
                    request,
                    response);

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

            forwardToLogin(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to authenticate user",
                    exception);
        }
    }

    private void forwardToLogin(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/login.jsp");

        dispatcher.forward(
                request,
                response);
    }
}