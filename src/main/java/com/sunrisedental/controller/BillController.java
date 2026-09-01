package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.AppointmentDAOImpl;
import com.sunrisedental.dao.BillDAOImpl;
import com.sunrisedental.dao.TreatmentDAOImpl;
import com.sunrisedental.dao.TreatmentTypeDAOImpl;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;

import com.sunrisedental.service.BillService;
import com.sunrisedental.service.StandardBillCalculator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@WebServlet("/bills")
public class BillController extends HttpServlet {

    private final BillService billService;
    private final AppointmentDAO appointmentDAO;

    public BillController() {

        try {

            this.billService =
                    createBillService();

            this.appointmentDAO =
                    new AppointmentDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize bill controller",
                    exception);
        }
    }

    /*
     * Kept for older unit tests.
     */
    public BillController(
            final BillService billService) {

        this.billService =
                billService;

        this.appointmentDAO =
                null;
    }

    /*
     * Full constructor for tests that need appointment data.
     */
    public BillController(
            final BillService billService,
            final AppointmentDAO appointmentDAO) {

        this.billService =
                billService;

        this.appointmentDAO =
                appointmentDAO;
    }

    private static BillService createBillService()
            throws SQLException {

        return new BillService(
                new BillDAOImpl(),
                new TreatmentDAOImpl(),
                new TreatmentTypeDAOImpl(),
                new StandardBillCalculator()
        );
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * These values may come directly from
         * Create Appointment after saving.
         */
        final String appointmentIdValue =
                request.getParameter(
                        "appointmentId");

        final String appointmentDateValue =
                request.getParameter(
                        "appointmentDate");

        final String appointmentNumber =
                request.getParameter(
                        "appointmentNumber");

        final String created =
                request.getParameter(
                        "created");

        /*
         * Preserve preselected appointment information.
         */
        if (appointmentIdValue != null
                && !appointmentIdValue.isBlank()) {

            request.setAttribute(
                    "appointmentId",
                    appointmentIdValue);
        }

        if (appointmentDateValue != null
                && !appointmentDateValue.isBlank()) {

            request.setAttribute(
                    "selectedAppointmentDate",
                    appointmentDateValue);
        }

        if (appointmentNumber != null
                && !appointmentNumber.isBlank()) {

            request.setAttribute(
                    "selectedAppointmentNumber",
                    appointmentNumber);
        }

        if ("true".equalsIgnoreCase(created)) {

            request.setAttribute(
                    "infoMessage",
                    "Appointment created successfully. "
                            + "Generate the bill when ready.");
        }

        try {

            /*
             * Load appointments for the dropdown.
             */
            loadAppointments(
                    request);

            /*
             * Existing bill search functionality.
             */
            final String billIdValue =
                    request.getParameter(
                            "billId");

            if (billIdValue != null
                    && !billIdValue.isBlank()) {

                searchBill(
                        request,
                        billIdValue);
            }

            forwardToBillPage(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Bill ID must be a valid number");

            forwardToBillPage(
                    request,
                    response);

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

            forwardToBillPage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to load billing page",
                    exception);
        }
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final String appointmentIdValue =
                    request.getParameter(
                            "appointmentId");

            final String appointmentDateValue =
                    request.getParameter(
                            "appointmentDate");

            final String appointmentNumber =
                    request.getParameter(
                            "appointmentNumber");

            final int appointmentId =
                    resolveAppointmentId(
                            appointmentIdValue,
                            appointmentDateValue,
                            appointmentNumber);

            /*
             * Calculate total from all treatments already
             * attached to this appointment.
             */
            final BigDecimal totalAmount =
                    billService
                            .calculateBillTotal(
                                    appointmentId);

            if (totalAmount.compareTo(
                    BigDecimal.ZERO) <= 0) {

                request.setAttribute(
                        "errorMessage",
                        "No treatments were selected for this appointment");

                preserveSelection(
                        request,
                        appointmentId,
                        appointmentDateValue,
                        appointmentNumber);

                loadAppointmentsSafely(
                        request);

                forwardToBillPage(
                        request,
                        response);

                return;
            }

            final Bill bill =
                    new Bill(
                            0,
                            appointmentId,
                            totalAmount
                    );

            final boolean saved =
                    billService
                            .saveBill(
                                    bill);

            if (saved) {

                request.setAttribute(
                        "successMessage",
                        "Bill generated successfully");

                request.setAttribute(
                        "calculatedTotal",
                        totalAmount);

                request.setAttribute(
                        "generatedAppointmentNumber",
                        appointmentNumber);

                request.setAttribute(
                        "generatedAppointmentDate",
                        appointmentDateValue);
            }

            preserveSelection(
                    request,
                    appointmentId,
                    appointmentDateValue,
                    appointmentNumber);

            loadAppointments(
                    request);

            forwardToBillPage(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Please select a valid appointment");

            loadAppointmentsSafely(
                    request);

            forwardToBillPage(
                    request,
                    response);

        } catch (DateTimeParseException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Appointment date must be valid");

            loadAppointmentsSafely(
                    request);

            forwardToBillPage(
                    request,
                    response);

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

            loadAppointmentsSafely(
                    request);

            forwardToBillPage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to generate bill",
                    exception);
        }
    }

    private int resolveAppointmentId(
            final String appointmentIdValue,
            final String appointmentDateValue,
            final String appointmentNumber)
            throws SQLException {

        /*
         * If the page already supplied an appointment ID
         * from the dropdown, use it.
         */
        if (appointmentIdValue != null
                && !appointmentIdValue.isBlank()) {

            return Integer.parseInt(
                    appointmentIdValue);
        }

        /*
         * Otherwise resolve the appointment using
         * the date + appointment number entered by the user.
         */
        if (appointmentDateValue == null
                || appointmentDateValue.isBlank()) {

            throw new IllegalArgumentException(
                    "Appointment date is required");
        }

        if (appointmentNumber == null
                || appointmentNumber.isBlank()) {

            throw new IllegalArgumentException(
                    "Appointment number is required");
        }

        if (appointmentDAO == null) {

            throw new IllegalStateException(
                    "Appointment lookup is unavailable");
        }

        final LocalDate appointmentDate =
                LocalDate.parse(
                        appointmentDateValue);

        final Optional<Appointment> appointment =
                appointmentDAO
                        .findByAppointmentDateAndNumber(
                                appointmentDate,
                                appointmentNumber.trim());

        if (appointment.isEmpty()) {

            throw new IllegalArgumentException(
                    "Appointment not found for the selected date");
        }

        return appointment
                .get()
                .getAppointmentId();
    }

    private void searchBill(
            final HttpServletRequest request,
            final String billIdValue)
            throws SQLException {

        final int billId =
                Integer.parseInt(
                        billIdValue);

        final Optional<Bill> bill =
                billService
                        .findBill(
                                billId);

        if (bill.isPresent()) {

            request.setAttribute(
                    "bill",
                    bill.get());

        } else {

            request.setAttribute(
                    "errorMessage",
                    "Bill not found");
        }
    }

    private void loadAppointments(
            final HttpServletRequest request)
            throws SQLException {

        if (appointmentDAO == null) {
            return;
        }

        final List<Appointment> appointments =
                appointmentDAO
                        .findAllAppointments();

        request.setAttribute(
                "appointments",
                appointments);
    }

    private void loadAppointmentsSafely(
            final HttpServletRequest request) {

        try {

            loadAppointments(
                    request);

        } catch (SQLException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Unable to load appointments");
        }
    }

    private void preserveSelection(
            final HttpServletRequest request,
            final int appointmentId,
            final String appointmentDate,
            final String appointmentNumber) {

        request.setAttribute(
                "appointmentId",
                appointmentId);

        request.setAttribute(
                "selectedAppointmentDate",
                appointmentDate);

        request.setAttribute(
                "selectedAppointmentNumber",
                appointmentNumber);
    }

    private void forwardToBillPage(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/bill.jsp");

        dispatcher.forward(
                request,
                response);
    }
}