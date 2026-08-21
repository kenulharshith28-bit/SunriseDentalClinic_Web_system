package com.sunrisedental.controller;

import com.sunrisedental.service.BillService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    void setUp() {

        billService =
                mock(BillService.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        controller =
                new BillController(
                        billService);
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
}