package com.sunrisedental.service;

import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void shouldProvideMeaningfulSQLExceptionWhenBillSaveFails()
            throws SQLException {

        final Bill bill =
                new Bill(
                        1,
                        1,
                        new BigDecimal("3500.00")
                );

        when(billDAO.saveBill(bill))
                .thenThrow(
                        new SQLException(
                                "Database connection failed"));

        final SQLException exception =
                assertThrows(
                        SQLException.class,
                        () -> billService.saveBill(bill));

        assertTrue(
                exception.getMessage()
                        .contains("Failed to save bill"),
                "SQLException should describe the failed bill save");
    }

    @Test
    void shouldReturnBillWhenBillExists()
            throws SQLException {

        final Bill bill =
                new Bill(
                        10,
                        1,
                        new BigDecimal("3500.00")
                );

        when(billDAO.findByBillId(10))
                .thenReturn(Optional.of(bill));

        final Optional<Bill> result =
                billService.findBill(10);

        assertTrue(
                result.isPresent(),
                "Bill should be returned when it exists");

        assertEquals(
                10,
                result.get().getBillId());

        assertEquals(
                new BigDecimal("3500.00"),
                result.get().getTotalAmount());

        verify(billDAO)
                .findByBillId(10);
    }
}