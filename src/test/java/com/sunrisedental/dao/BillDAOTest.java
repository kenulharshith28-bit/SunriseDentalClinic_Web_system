package com.sunrisedental.dao;

import com.sunrisedental.model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for BillDAOImpl.
 */
class BillDAOTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Bill bill;
    private BillDAO billDAO;

    @BeforeEach
    void setUp() {

        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        bill = mock(Bill.class);

        billDAO = new BillDAOImpl(connection);
    }

    @Test
    void shouldSaveBillSuccessfully()
            throws SQLException {

        // ARRANGE
        when(bill.getAppointmentId())
                .thenReturn(1);

        when(bill.getTotalAmount())
                .thenReturn(
                        new BigDecimal("4500.00"));

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(1);

        // ACT
        final boolean result =
                billDAO.saveBill(bill);

        // ASSERT
        assertTrue(
                result,
                "Bill should be saved successfully");

        verify(preparedStatement)
                .setInt(1, 1);

        verify(preparedStatement)
                .setBigDecimal(
                        2,
                        new BigDecimal("4500.00"));

        verify(preparedStatement)
                .executeUpdate();
    }

    @Test
    void shouldRejectNullBill() {

        assertThrows(
                IllegalArgumentException.class,
                () -> billDAO.saveBill(null));
    }

    @Test
    void shouldProvideMeaningfulSQLExceptionWhenBillSaveFails()
            throws SQLException {

        when(bill.getAppointmentId())
                .thenReturn(1);

        when(bill.getTotalAmount())
                .thenReturn(
                        new BigDecimal("4500.00"));

        when(connection.prepareStatement(anyString()))
                .thenThrow(
                        new SQLException(
                                "Database connection failed"));

        final SQLException exception =
                assertThrows(
                        SQLException.class,
                        () -> billDAO.saveBill(bill));

        assertTrue(
                exception.getMessage()
                        .contains(
                                "Failed to save bill"),
                "SQLException should describe the failed bill persistence operation");
    }
}