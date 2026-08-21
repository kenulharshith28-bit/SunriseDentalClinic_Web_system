package com.sunrisedental.controller;

import com.sunrisedental.service.AuthenticationService;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.io.IOException;

public class LoginController extends HttpServlet {

    private final AuthenticationService authenticationService;

    public LoginController(
            final AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final String username =
                request.getParameter("username");

        final String password =
                request.getParameter("password");

        try {
            final boolean authenticated =
                    authenticationService.authenticate(
                            username,
                            password);

            if (authenticated) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/dashboard");

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Invalid username or password");

                final RequestDispatcher dispatcher =
                        request.getRequestDispatcher(
                                "/WEB-INF/views/login.jsp");

                dispatcher.forward(
                        request,
                        response);
            }

        } catch (SQLException exception) {
            throw new ServletException(
                    "Unable to authenticate user",
                    exception);
        }
    }
}