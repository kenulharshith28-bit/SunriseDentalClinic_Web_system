package com.sunrisedental.dao;

import com.sunrisedental.model.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.sql.ResultSet;


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

        if (bill == null) {
            throw new IllegalArgumentException(
                    "Bill must not be null");
        }

        final String sql =
                "INSERT INTO bills "
                        + "(appointment_id, total_amount) "
                        + "VALUES (?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    bill.getAppointmentId());

            statement.setBigDecimal(
                    2,
                    bill.getTotalAmount());

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to save bill",
                    exception);
        }
    }

    @Override
    public Optional<Bill> findByBillId(
            final int billId)
            throws SQLException {

        final String sql =
                "SELECT * FROM bills "
                        + "WHERE bill_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    billId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                /*
                  Successful bill mapping have not implemented yet.
                 */
                return Optional.empty();
            }
        }
    }


}