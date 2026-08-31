package com.sunrisedental.controller;

import com.sunrisedental.report.ReportGeneratorFactory;
import com.sunrisedental.service.ReportService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reports")
public class ReportController extends HttpServlet {

    private final ReportService reportService;

    public ReportController() {

        this.reportService =
                new ReportService(
                        new ReportGeneratorFactory());
    }

    public ReportController(
            final ReportService reportService) {

        this.reportService =
                reportService;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final String reportType =
                request.getParameter(
                        "reportType");

        if (reportType == null
                || reportType.isBlank()) {

            forwardToReportPage(
                    request,
                    response);

            return;
        }

        try {

            final String report =
                    reportService
                            .generateReport(
                                    reportType);

            request.setAttribute(
                    "report",
                    report);

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

        } catch (IllegalStateException exception) {

            throw new ServletException(
                    "Unable to generate report",
                    exception);
        }

        forwardToReportPage(
                request,
                response);
    }

    private void forwardToReportPage(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/report.jsp");

        dispatcher.forward(
                request,
                response);
    }
}