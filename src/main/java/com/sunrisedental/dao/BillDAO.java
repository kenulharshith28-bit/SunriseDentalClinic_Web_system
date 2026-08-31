package com.sunrisedental.dao;

import com.sunrisedental.model.Bill;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Defines persistence operations for bills.
 */
public interface BillDAO {

    /**
     * Saves a bill.
     */
    boolean saveBill(final Bill bill)
            throws SQLException;

    /**
     * Finds a bill using its unique bill ID.
     */
    Optional<Bill> findByBillId(
            final int billId)
            throws SQLException;


    List<Bill> findAllBills()
            throws SQLException;
}