package com.sunrisedental.controller;

import com.sunrisedental.dao.BillDAOImpl;
import com.sunrisedental.dao.TreatmentDAOImpl;
import com.sunrisedental.dao.TreatmentTypeDAOImpl;

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
import java.util.Optional;

@WebServlet("/bills")
public class BillController extends HttpServlet {

    private final BillService billService;

    public BillController() {

        this.billService =
                createBillService();
    }

    public BillController(
            final BillService billService) {

        this.billService =
                billService;
    }

    private static BillService createBillService() {

        try {

            return new BillService(
                    new BillDAOImpl(),
                    new TreatmentDAOImpl(),
                    new TreatmentTypeDAOImpl(),
                    new StandardBillCalculator()
            );

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize bill controller",
                    exception);
        }
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final String billIdValue =
                request.getParameter(
                        "billId");

        if (billIdValue == null
                || billIdValue.isBlank()) {

            forwardToBillPage(
                    request,
                    response);

            return;
        }

        try {

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
                    "Unable to search bill",
                    exception);
        }
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final int appointmentId =
                    Integer.parseInt(
                            request.getParameter(
                                    "appointmentId"));

            final BigDecimal totalAmount =
                    billService
                            .calculateBillTotal(
                                    appointmentId);

            if (totalAmount.compareTo(
                    BigDecimal.ZERO) <= 0) {

                request.setAttribute(
                        "errorMessage",
                        "No treatments found for this appointment");

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
                        "Bill saved successfully");

                request.setAttribute(
                        "calculatedTotal",
                        totalAmount);
            }

            forwardToBillPage(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Appointment ID must be a valid number");

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
                    "Unable to save bill",
                    exception);
        }
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