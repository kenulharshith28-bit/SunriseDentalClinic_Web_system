package com.sunrisedental.dao;

import com.sunrisedental.model.Bill;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Defines persistence operations for bills.
 */
public interface BillDAO {

    /**
     * Saves a bill.
     *
     * @param bill bill to persist
     * @return true when the bill is saved successfully
     * @throws SQLException if a database operation fails
     */
    boolean saveBill(final Bill bill)
            throws SQLException;

    /**
     * Finds a bill using its unique bill ID.
     *
     * @param billId unique bill ID
     * @return matching bill when found, otherwise an empty Optional
     * @throws SQLException if a database operation fails
     */
    Optional<Bill> findByBillId(
            final int billId)
            throws SQLException;
}