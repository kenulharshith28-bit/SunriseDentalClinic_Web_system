package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.util.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public class AppointmentDAOImpl implements AppointmentDAO {

    private final Connection connection;

    public AppointmentDAOImpl(
            final Connection connection) {

        this.connection = connection;
    }

    public AppointmentDAOImpl()
            throws SQLException {

        this(
                DBConnectionFactory
                        .getInstance()
                        .getConnection()
        );
    }

    @Override
    public boolean saveAppointment(
            final Appointment appointment)
            throws SQLException {

        if (appointment == null) {
            throw new IllegalArgumentException(
                    "Appointment must not be null");
        }

        final String sql =
                "INSERT INTO appointments "
                        + "(appointment_number, patient_id, dentist_id, "
                        + "appointment_date, appointment_time, status, notes) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    appointment.getAppointmentNumber());

            statement.setInt(
                    2,
                    appointment.getPatientId());

            statement.setInt(
                    3,
                    appointment.getDentistId());

            statement.setDate(
                    4,
                    java.sql.Date.valueOf(
                            appointment.getAppointmentDate()));

            statement.setTime(
                    5,
                    java.sql.Time.valueOf(
                            appointment.getAppointmentTime()));

            statement.setString(
                    6,
                    appointment.getStatus());

            statement.setString(
                    7,
                    appointment.getNotes());

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLIntegrityConstraintViolationException exception) {

            if (exception.getErrorCode() == 1062) {
                throw exception;
            }

            throw new SQLException(
                    "Failed to save appointment",
                    exception);

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

                final Appointment appointment =
                        new Appointment(
                                resultSet.getInt(
                                        "appointment_id"),
                                resultSet.getString(
                                        "appointment_number"),
                                resultSet.getInt(
                                        "patient_id"),
                                resultSet.getInt(
                                        "dentist_id"),
                                resultSet.getDate(
                                                "appointment_date")
                                        .toLocalDate(),
                                resultSet.getTime(
                                                "appointment_time")
                                        .toLocalTime(),
                                resultSet.getString(
                                        "status"),
                                resultSet.getString(
                                        "notes")
                        );

                return Optional.of(
                        appointment);
            }

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to find appointment",
                    exception);
        }
    }
}
