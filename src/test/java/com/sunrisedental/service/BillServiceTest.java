package com.sunrisedental.service;

import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BillServiceTest {

    private BillDAO billDAO;
    private BillService billService;

    @BeforeEach
    void setUp() {

        billDAO = mock(BillDAO.class);
        billService = new BillService(billDAO);
    }

    @Test
    void shouldSaveBillSuccessfully()
            throws SQLException {

        final Bill bill =
                new Bill(
                        1,
                        1,
                        new BigDecimal("3500.00")
                );

        when(billDAO.saveBill(bill))
                .thenReturn(true);

        final boolean result =
                billService.saveBill(bill);

        assertTrue(
                result,
                "Bill should be saved successfully");

        verify(billDAO)
                .saveBill(bill);
    }

    @Test
    void shouldRejectNullBill() {

        assertThrows(
                IllegalArgumentException.class,
                () -> billService.saveBill(null)
        );
    }
}