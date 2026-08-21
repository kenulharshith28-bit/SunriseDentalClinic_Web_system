package com.sunrisedental.controller;

import com.sunrisedental.service.AuthenticationService;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            authenticationService.authenticate(
                    username,
                    password);

        } catch (SQLException exception) {
            throw new ServletException(
                    "Unable to authenticate user",
                    exception);
        }
    }
}