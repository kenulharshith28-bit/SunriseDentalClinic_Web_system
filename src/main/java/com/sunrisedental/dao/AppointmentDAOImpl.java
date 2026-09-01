package com.sunrisedental.dao;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.util.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public int saveAppointment(
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
                     connection.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

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

            if (affectedRows != 1) {

                throw new SQLException(
                        "Failed to save appointment");
            }

            try (ResultSet generatedKeys =
                         statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {

                    return generatedKeys.getInt(1);
                }

                throw new SQLException(
                        "Appointment saved but generated ID was not returned");
            }

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
                "SELECT appointment_id, appointment_number, "
                        + "patient_id, dentist_id, "
                        + "appointment_date, appointment_time, "
                        + "status, notes "
                        + "FROM appointments "
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
                        createAppointmentFromResultSet(
                                resultSet);

                return Optional.of(
                        appointment);
            }

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to find appointment",
                    exception);
        }
    }

    @Override
    public Optional<Appointment>
    findByAppointmentDateAndNumber(
            final LocalDate appointmentDate,
            final String appointmentNumber)
            throws SQLException {

        final String sql =
                "SELECT appointment_id, appointment_number, "
                        + "patient_id, dentist_id, "
                        + "appointment_date, appointment_time, "
                        + "status, notes "
                        + "FROM appointments "
                        + "WHERE appointment_date = ? "
                        + "AND appointment_number = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDate(
                    1,
                    java.sql.Date.valueOf(
                            appointmentDate));

            statement.setString(
                    2,
                    appointmentNumber);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (!resultSet.next()) {

                    return Optional.empty();
                }

                final Appointment appointment =
                        createAppointmentFromResultSet(
                                resultSet);

                return Optional.of(
                        appointment);
            }

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to find appointment",
                    exception);
        }
    }

    @Override
    public List<Appointment> findAllAppointments()
            throws SQLException {

        final String sql =
                "SELECT appointment_id, appointment_number, "
                        + "patient_id, dentist_id, "
                        + "appointment_date, appointment_time, "
                        + "status, notes "
                        + "FROM appointments "
                        + "ORDER BY appointment_date, appointment_time";

        final List<Appointment> appointments =
                new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                appointments.add(
                        createAppointmentFromResultSet(
                                resultSet));
            }

            return appointments;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to load appointments",
                    exception);
        }
    }

    @Override
    public String generateNextAppointmentNumber(
            final LocalDate appointmentDate)
            throws SQLException {

        if (appointmentDate == null) {

            throw new IllegalArgumentException(
                    "Appointment date must not be null");
        }

        final String sql =
                "SELECT appointment_number "
                        + "FROM appointments "
                        + "WHERE appointment_date = ? "
                        + "ORDER BY appointment_id DESC "
                        + "LIMIT 1";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDate(
                    1,
                    java.sql.Date.valueOf(
                            appointmentDate));

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (!resultSet.next()) {

                    return "A-001";
                }

                final String lastNumber =
                        resultSet.getString(
                                "appointment_number");

                final int currentNumber =
                        Integer.parseInt(
                                lastNumber.substring(2));

                final int nextNumber =
                        currentNumber + 1;

                return String.format(
                        "A-%03d",
                        nextNumber);
            }

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to generate appointment number",
                    exception);
        }
    }

    @Override
    public int getAppointmentCount()
            throws SQLException {

        final String sql =
                "SELECT COUNT(*) AS total "
                        + "FROM appointments";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {

                return resultSet.getInt(
                        "total");
            }

            return 0;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to get appointment count",
                    exception);
        }
    }

    @Override
    public List<Integer>
    getAppointmentCountsForCurrentWeek()
            throws SQLException {

        final String sql =
                "SELECT DAYOFWEEK(appointment_date) AS day_number, "
                        + "COUNT(*) AS appointment_count "
                        + "FROM appointments "
                        + "WHERE YEARWEEK(appointment_date, 1) "
                        + "= YEARWEEK(CURDATE(), 1) "
                        + "GROUP BY DAYOFWEEK(appointment_date)";

        final List<Integer> counts =
                new ArrayList<>(
                        Arrays.asList(
                                0, 0, 0, 0, 0, 0, 0));

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                final int mysqlDay =
                        resultSet.getInt(
                                "day_number");

                final int appointmentCount =
                        resultSet.getInt(
                                "appointment_count");

                final int index;

                /*
                 * MySQL DAYOFWEEK:
                 *
                 * Sunday = 1
                 * Monday = 2
                 * Tuesday = 3
                 * Wednesday = 4
                 * Thursday = 5
                 * Friday = 6
                 * Saturday = 7
                 *
                 * Our list uses:
                 *
                 * Monday = 0
                 * ...
                 * Sunday = 6
                 */

                if (mysqlDay == 1) {

                    index = 6;

                } else {

                    index = mysqlDay - 2;
                }

                counts.set(
                        index,
                        appointmentCount);
            }

            return counts;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to load weekly appointment counts",
                    exception);
        }
    }

    private Appointment createAppointmentFromResultSet(
            final ResultSet resultSet)
            throws SQLException {

        return new Appointment(
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
    }

    @Override
    public boolean cancelAppointment(
            final int appointmentId)
            throws SQLException {

        final String sql =
                "UPDATE appointments "
                        + "SET status = 'CANCELLED' "
                        + "WHERE appointment_id = ? "
                        + "AND status = 'SCHEDULED'";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    appointmentId);

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to cancel appointment",
                    exception);
        }
    }


    @Override
    public int cancelExpiredAppointments()
            throws SQLException {

        final String sql =
                "UPDATE appointments "
                        + "SET status = 'CANCELLED' "
                        + "WHERE status = 'SCHEDULED' "
                        + "AND TIMESTAMP("
                        + "appointment_date, appointment_time"
                        + ") < NOW()";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            return statement.executeUpdate();

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to update expired appointments",
                    exception);
        }
    }
}