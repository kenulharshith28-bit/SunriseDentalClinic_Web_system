package com.sunrisedental.controller;

import com.sunrisedental.service.AppointmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AppointmentController extends HttpServlet {

    private final AppointmentService appointmentService;

    public AppointmentController(
            final AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        // Search handling will be added after the test fails.
    }
}