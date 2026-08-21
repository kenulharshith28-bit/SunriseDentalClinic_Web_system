package com.sunrisedental.controller;

import com.sunrisedental.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        // Bill lookup will be added after the test fails.
    }
}