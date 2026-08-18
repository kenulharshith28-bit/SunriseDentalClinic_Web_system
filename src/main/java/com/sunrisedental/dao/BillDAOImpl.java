package com.sunrisedental.dao;

import com.sunrisedental.model.Bill;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC implementation of bill persistence operations.
 */
public class BillDAOImpl implements BillDAO {

    private final Connection connection;

    public BillDAOImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean saveBill(final Bill bill)
            throws SQLException {

        // bill persistence
        // has not yet been implemented.
        return false;
    }
}