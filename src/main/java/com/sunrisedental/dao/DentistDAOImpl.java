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
                "SELECT dentist_id, first_name, last_name "
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
                                        "last_name")
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
}