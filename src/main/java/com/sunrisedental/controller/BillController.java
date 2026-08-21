package com.sunrisedental.controller;

import com.sunrisedental.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;

import java.io.IOException;

public class BillController extends HttpServlet {

    private final BillService billService;

    public BillController(
            final BillService billService) {

        this.billService = billService;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final String billIdValue =
                request.getParameter("billId");

        final int billId =
                Integer.parseInt(billIdValue);

        try {
            billService.findBill(billId);

        } catch (SQLException exception) {
            throw new ServletException(
                    "Unable to search bill",
                    exception);
        }
    }
}