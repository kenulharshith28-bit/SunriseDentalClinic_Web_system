package com.sunrisedental.service;

import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.model.Bill;

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

        return billDAO.saveBill(bill);
    }
}