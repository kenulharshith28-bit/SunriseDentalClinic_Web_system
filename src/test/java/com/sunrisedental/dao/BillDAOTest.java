package com.sunrisedental.dao;

import com.sunrisedental.model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private ResultSet resultSet;
    private Bill bill;
    private BillDAO billDAO;

    @BeforeEach
    void setUp() {

        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
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

    @Test
    void shouldReturnEmptyWhenBillDoesNotExist()
            throws SQLException {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(false);

        final Optional<Bill> result =
                billDAO.findByBillId(999);

        assertNotNull(
                result,
                "DAO should return an Optional instead of null");

        assertTrue(
                result.isEmpty(),
                "Result should be empty when bill does not exist");

        verify(preparedStatement)
                .setInt(1, 999);

        verify(preparedStatement)
                .executeQuery();
    }

    @Test
    void shouldFindBillByBillId()
            throws SQLException {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true);

        when(resultSet.getInt("bill_id"))
                .thenReturn(10);

        when(resultSet.getInt("appointment_id"))
                .thenReturn(1);

        when(resultSet.getBigDecimal("total_amount"))
                .thenReturn(
                        new BigDecimal("4500.00"));

        final Optional<Bill> result =
                billDAO.findByBillId(10);

        assertTrue(
                result.isPresent(),
                "Bill should be returned when it exists");

        assertEquals(
                10,
                result.get().getBillId());

        assertEquals(
                1,
                result.get().getAppointmentId());

        assertEquals(
                new BigDecimal("4500.00"),
                result.get().getTotalAmount());

        verify(preparedStatement)
                .setInt(1, 10);

        verify(preparedStatement)
                .executeQuery();
    }
}