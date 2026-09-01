package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.AppointmentDAOImpl;
import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.dao.BillDAOImpl;
import com.sunrisedental.dao.PatientDAO;
import com.sunrisedental.dao.PatientDAOImpl;
import com.sunrisedental.dao.TreatmentDAO;
import com.sunrisedental.dao.TreatmentDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

    private PatientDAO patientDAO;
    private AppointmentDAO appointmentDAO;
    private TreatmentDAO treatmentDAO;
    private BillDAO billDAO;

    @Override
    public void init()
            throws ServletException {

        try {

            appointmentDAO =
                    new AppointmentDAOImpl();

            patientDAO =
                    new PatientDAOImpl();

            treatmentDAO =
                    new TreatmentDAOImpl();

            billDAO =
                    new BillDAOImpl();

        } catch (SQLException exception) {

            throw new ServletException(
                    "Failed to initialize dashboard",
                    exception);
        }
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("username") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login");

            return;
        }

        try {

            final List<Integer> weeklyAppointmentCounts =
                    appointmentDAO
                            .getAppointmentCountsForCurrentWeek();

            request.setAttribute(
                    "weeklyAppointmentCounts",
                    weeklyAppointmentCounts);

            request.setAttribute(
                    "patientCount",
                    patientDAO.getPatientCount());

            request.setAttribute(
                    "appointmentCount",
                    appointmentDAO.getAppointmentCount());

            request.setAttribute(
                    "treatmentCount",
                    treatmentDAO.getTreatmentCount());

            request.setAttribute(
                    "billCount",
                    billDAO.getBillCount());

            request.getRequestDispatcher(
                            "/WEB-INF/views/dashboard.jsp")
                    .forward(
                            request,
                            response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Failed to load dashboard data",
                    exception);
        }
    }
}
