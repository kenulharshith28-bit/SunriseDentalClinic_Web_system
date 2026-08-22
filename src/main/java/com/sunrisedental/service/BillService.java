package com.sunrisedental.service;

import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.model.Bill;
import java.util.Optional;

import java.sql.SQLException;

public class BillService {

    private final BillDAO billDAO;

    public BillService(final BillDAO billDAO) {
        this.billDAO = billDAO;
    }

    public boolean saveBill(final Bill bill)
            throws SQLException {

        if (bill == null) {
            throw new IllegalArgumentException(
                    "Bill must not be null");
        }

        try {
            return billDAO.saveBill(bill);

        } catch (SQLException exception) {
            throw new SQLException(
                    "Failed to save bill",
                    exception);
        }
    }

    public Optional<Bill> findBill(
            final int billId)
            throws SQLException {

        if (billId <= 0) {
            throw new IllegalArgumentException(
                    "Bill ID must be greater than zero");
        }

        try {
            return billDAO.findByBillId(billId);

        } catch (SQLException exception) {
            throw new SQLException(
                    "Failed to find bill",
                    exception);
        }
    }
}