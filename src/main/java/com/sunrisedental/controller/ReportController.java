package com.sunrisedental.controller;

import com.sunrisedental.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

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

        final String reportType =
                request.getParameter("reportType");

        final String report =
                reportService.generateReport(
                        reportType);

        request.setAttribute(
                "report",
                report);

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/report.jsp");

        dispatcher.forward(
                request,
                response);
    }
}