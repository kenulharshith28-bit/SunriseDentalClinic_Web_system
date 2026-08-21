package com.sunrisedental.controller;

import com.sunrisedental.service.BillService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sunrisedental.model.Bill;

import javax.servlet.RequestDispatcher;

import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BillControllerTest {

    private BillService billService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private BillController controller;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {

        billService =
                mock(BillService.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        dispatcher =
                mock(RequestDispatcher.class);

        controller =
                new BillController(
                        billService);

        when(request.getRequestDispatcher(
                "/WEB-INF/views/bill.jsp"))
                .thenReturn(dispatcher);
    }

    @Test
    void shouldSearchBillUsingRequestParameter()
            throws Exception {

        when(request.getParameter(
                "billId"))
                .thenReturn("10");

        controller.doGet(
                request,
                response);

        verify(billService)
                .findBill(10);
    }

    @Test
    void shouldShowFoundBillOnPage()
            throws Exception {

        final Bill bill =
                new Bill(
                        10,
                        1,
                        new BigDecimal("3500.00")
                );

        when(request.getParameter(
                "billId"))
                .thenReturn("10");

        when(billService.findBill(10))
                .thenReturn(
                        Optional.of(bill));

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        "bill",
                        bill);

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }
}