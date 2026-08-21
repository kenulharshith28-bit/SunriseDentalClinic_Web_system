package com.sunrisedental.controller;

import com.sunrisedental.service.AuthenticationService;

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

        // Login handling will be added after the test fails.
    }
}