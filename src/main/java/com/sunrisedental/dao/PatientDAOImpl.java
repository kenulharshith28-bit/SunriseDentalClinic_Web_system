package com.sunrisedental.dao;

import com.sunrisedental.model.Patient;
import com.sunrisedental.util.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDAOImpl
        implements PatientDAO {

    private final Connection connection;

    public PatientDAOImpl(
            final Connection connection) {

        this.connection =
                connection;
    }

    public PatientDAOImpl()
            throws SQLException {

        this(
                DBConnectionFactory
                        .getInstance()
                        .getConnection()
        );
    }

    @Override
    public List<Patient> findAllPatients()
            throws SQLException {

        final String sql =
                "SELECT patient_id, first_name, last_name, "
                        + "phone, email, date_of_birth, address "
                        + "FROM patients "
                        + "ORDER BY first_name, last_name";

        final List<Patient> patients =
                new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                patients.add(
                        mapPatient(
                                resultSet));
            }

            return patients;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to load patients",
                    exception);
        }
    }

    @Override
    public Optional<Patient> findById(
            final int patientId)
            throws SQLException {

        if (patientId <= 0) {

            throw new IllegalArgumentException(
                    "Patient ID must be greater than zero");
        }

        final String sql =
                "SELECT patient_id, first_name, last_name, "
                        + "phone, email, date_of_birth, address "
                        + "FROM patients "
                        + "WHERE patient_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    patientId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return Optional.of(
                            mapPatient(
                                    resultSet));
                }

                return Optional.empty();
            }

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to find patient",
                    exception);
        }
    }

    @Override
    public boolean savePatient(
            final Patient patient)
            throws SQLException {

        if (patient == null) {

            throw new IllegalArgumentException(
                    "Patient must not be null");
        }

        final String sql =
                "INSERT INTO patients "
                        + "(first_name, last_name, phone, email, "
                        + "date_of_birth, address) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            setPatientValues(
                    statement,
                    patient);

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to save patient",
                    exception);
        }
    }

    @Override
    public boolean updatePatient(
            final Patient patient)
            throws SQLException {

        if (patient == null) {

            throw new IllegalArgumentException(
                    "Patient must not be null");
        }

        if (patient.getPatientId() <= 0) {

            throw new IllegalArgumentException(
                    "Patient ID must be greater than zero");
        }

        final String sql =
                "UPDATE patients "
                        + "SET first_name = ?, "
                        + "last_name = ?, "
                        + "phone = ?, "
                        + "email = ?, "
                        + "date_of_birth = ?, "
                        + "address = ? "
                        + "WHERE patient_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            setPatientValues(
                    statement,
                    patient);

            statement.setInt(
                    7,
                    patient.getPatientId());

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to update patient",
                    exception);
        }
    }

    @Override
    public boolean deletePatient(
            final int patientId)
            throws SQLException {

        if (patientId <= 0) {

            throw new IllegalArgumentException(
                    "Patient ID must be greater than zero");
        }

        final String sql =
                "DELETE FROM patients "
                        + "WHERE patient_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    patientId);

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            /*
             * Do not hide the original database exception.
             *
             * The controller will use it to show a friendly
             * message when the patient is linked to
             * appointments.
             */
            throw exception;
        }
    }

    @Override
    public int getPatientCount()
            throws SQLException {

        final String sql =
                "SELECT COUNT(*) AS total "
                        + "FROM patients";

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
                    "Failed to get patient count",
                    exception);
        }
    }

    private Patient mapPatient(
            final ResultSet resultSet)
            throws SQLException {

        final java.sql.Date dateOfBirthValue =
                resultSet.getDate(
                        "date_of_birth");

        return new Patient(
                resultSet.getInt(
                        "patient_id"),

                resultSet.getString(
                        "first_name"),

                resultSet.getString(
                        "last_name"),

                resultSet.getString(
                        "phone"),

                resultSet.getString(
                        "email"),

                dateOfBirthValue == null
                        ? null
                        : dateOfBirthValue
                        .toLocalDate(),

                resultSet.getString(
                        "address")
        );
    }

    private void setPatientValues(
            final PreparedStatement statement,
            final Patient patient)
            throws SQLException {

        statement.setString(
                1,
                patient.getFirstName());

        statement.setString(
                2,
                patient.getLastName());

        statement.setString(
                3,
                patient.getPhone());

        statement.setString(
                4,
                patient.getEmail());

        if (patient.getDateOfBirth() != null) {

            statement.setDate(
                    5,
                    java.sql.Date.valueOf(
                            patient.getDateOfBirth()));

        } else {

            statement.setNull(
                    5,
                    Types.DATE);
        }

        statement.setString(
                6,
                patient.getAddress());
    }
}