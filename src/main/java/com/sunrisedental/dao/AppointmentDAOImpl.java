package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC implementation of appointment persistence operations.
 */
public class AppointmentDAOImpl implements AppointmentDAO {

    private final Connection connection;

    public AppointmentDAOImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean saveAppointment(final Appointment appointment)
            throws SQLException {

        // RED stage: persistence logic has not yet been implemented.
        return false;
    }
}