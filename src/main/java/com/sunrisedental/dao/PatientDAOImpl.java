package com.sunrisedental.dao;

import com.sunrisedental.model.Patient;
import com.sunrisedental.util.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {

    private final Connection connection;

    public PatientDAOImpl(
            final Connection connection) {

        this.connection = connection;
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
                "SELECT patient_id, first_name, last_name, phone "
                        + "FROM patients "
                        + "ORDER BY first_name, last_name";

        final List<Patient> patients =
                new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                final Patient patient =
                        new Patient(
                                resultSet.getInt(
                                        "patient_id"),
                                resultSet.getString(
                                        "first_name"),
                                resultSet.getString(
                                        "last_name"),
                                resultSet.getString("phone")
                        );

                patients.add(patient);
            }

            return patients;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to load patients",
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
                        + "(first_name, last_name, phone) "
                        + "VALUES (?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    patient.getFirstName());

            statement.setString(
                    2,
                    patient.getLastName());

            statement.setString(
                    3,
                    patient.getPhone());

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to save patient",
                    exception);
        }
    }
}