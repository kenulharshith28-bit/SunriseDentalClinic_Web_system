package com.sunrisedental.dao;

import com.sunrisedental.model.Bill;

import java.sql.SQLException;

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
}