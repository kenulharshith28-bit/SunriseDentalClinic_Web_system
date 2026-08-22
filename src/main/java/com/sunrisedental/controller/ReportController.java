package com.sunrisedental.controller;

import com.sunrisedental.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ReportController extends HttpServlet {

    private final ReportService reportService;

    public ReportController(
            final ReportService reportService) {

        this.reportService = reportService;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        // Report handling not implemented.
    }
}