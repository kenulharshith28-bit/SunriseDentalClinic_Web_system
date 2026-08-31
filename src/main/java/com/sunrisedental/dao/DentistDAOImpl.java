package com.sunrisedental.dao;

import com.sunrisedental.model.Dentist;
import com.sunrisedental.util.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DentistDAOImpl implements DentistDAO {

    private final Connection connection;

    public DentistDAOImpl(
            final Connection connection) {

        this.connection = connection;
    }

    public DentistDAOImpl()
            throws SQLException {

        this(
                DBConnectionFactory
                        .getInstance()
                        .getConnection()
        );
    }

    @Override
    public List<Dentist> findAllDentists()
            throws SQLException {

        final String sql =
                "SELECT dentist_id, first_name, last_name, "
                        + "specialization, phone, email "
                        + "FROM dentists "
                        + "ORDER BY first_name, last_name";

        final List<Dentist> dentists =
                new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                final Dentist dentist =
                        new Dentist(
                                resultSet.getInt(
                                        "dentist_id"),
                                resultSet.getString(
                                        "first_name"),
                                resultSet.getString(
                                        "last_name"),
                                resultSet.getString(
                                        "specialization"),
                                resultSet.getString(
                                        "phone"),
                                resultSet.getString(
                                        "email")
                        );

                dentists.add(dentist);
            }

            return dentists;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to load dentists",
                    exception);
        }
    }

    @Override
    public boolean saveDentist(
            final Dentist dentist)
            throws SQLException {

        if (dentist == null) {

            throw new IllegalArgumentException(
                    "Dentist must not be null");
        }

        final String sql =
                "INSERT INTO dentists "
                        + "(first_name, last_name, "
                        + "specialization, phone, email) "
                        + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    dentist.getFirstName());

            statement.setString(
                    2,
                    dentist.getLastName());

            statement.setString(
                    3,
                    dentist.getSpecialization());

            statement.setString(
                    4,
                    dentist.getPhone());

            statement.setString(
                    5,
                    dentist.getEmail());

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to save dentist",
                    exception);
        }
    }

    @Override
    public boolean deleteDentist(
            final int dentistId)
            throws SQLException {

        final String sql =
                "DELETE FROM dentists "
                        + "WHERE dentist_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    dentistId);

            return statement.executeUpdate() == 1;

        } catch (SQLException exception) {

            throw exception;
        }
    }
}