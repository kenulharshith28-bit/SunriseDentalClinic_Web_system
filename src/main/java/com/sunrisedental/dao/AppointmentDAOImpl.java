package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.sql.ResultSet;


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

        if (appointment == null) {
            throw new IllegalArgumentException(
                    "Appointment must not be null");
        }

        final String sql =
                "INSERT INTO appointments (appointment_number) VALUES (?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    appointment.getAppointmentNumber());

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to save appointment",
                    exception);
        }
    }

    @Override
    public Optional<Appointment> findByAppointmentNumber(
            final String appointmentNumber)
            throws SQLException {

        final String sql =
                "SELECT * FROM appointments "
                        + "WHERE appointment_number = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    appointmentNumber);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                final Appointment foundAppointment =
                        new Appointment(
                                resultSet.getInt(
                                        "appointment_id"),
                                resultSet.getString(
                                        "appointment_number"));

                return Optional.of(foundAppointment);
            }

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to find appointment",
                    exception);
        }
    }
}