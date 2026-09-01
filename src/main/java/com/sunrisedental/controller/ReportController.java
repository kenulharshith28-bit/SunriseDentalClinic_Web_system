package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.AppointmentDAOImpl;
import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.dao.BillDAOImpl;
import com.sunrisedental.report.ReportGeneratorFactory;
import com.sunrisedental.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reports")
public class ReportController extends HttpServlet {

    private ReportService reportService;
    private AppointmentDAO appointmentDAO;
    private BillDAO billDAO;

    public ReportController() {
    }

    public ReportController(
            final ReportService reportService) {

        this.reportService = reportService;
    }

    public ReportController(
            final ReportService reportService,
            final AppointmentDAO appointmentDAO,
            final BillDAO billDAO) {

        this.reportService = reportService;
        this.appointmentDAO = appointmentDAO;
        this.billDAO = billDAO;
    }

    @Override
    public void init()
            throws ServletException {

        try {

            if (reportService == null) {

                reportService =
                        new ReportService(
                                new ReportGeneratorFactory());
            }

            if (appointmentDAO == null) {
                appointmentDAO =
                        new AppointmentDAOImpl();
            }

            if (billDAO == null) {
                billDAO =
                        new BillDAOImpl();
            }

        } catch (SQLException exception) {

            throw new ServletException(
                    "Failed to initialize report controller",
                    exception);
        }
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

            if ("appointment".equals(
                    reportType)) {

                request.setAttribute(
                        "appointments",
                        appointmentDAO
                                .findAllAppointments());

                request.setAttribute(
                        "reportType",
                        "appointment");

            } else if ("bill".equals(
                    reportType)) {

                request.setAttribute(
                        "bills",
                        billDAO.findAllBills());

                request.setAttribute(
                        "reportType",
                        "bill");

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Unsupported report type");
            }

        } catch (SQLException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Failed to load appointment report");

        } catch (IllegalArgumentException
                 | IllegalStateException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());
        }

        forwardToReportPage(
                request,
                response);
    }

    private void forwardToReportPage(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                        "/WEB-INF/views/report.jsp")
                .forward(
                        request,
                        response);
    }
}
