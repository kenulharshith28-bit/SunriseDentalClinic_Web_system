package com.sunrisedental.dao;

import com.sunrisedental.model.Treatment;
import com.sunrisedental.util.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAOImpl implements TreatmentDAO {

    private final Connection connection;

    public TreatmentDAOImpl(
            final Connection connection) {

        this.connection = connection;
    }

    public TreatmentDAOImpl()
            throws SQLException {

        this(
                DBConnectionFactory
                        .getInstance()
                        .getConnection()
        );
    }

    @Override
    public boolean saveTreatment(
            final Treatment treatment)
            throws SQLException {

        if (treatment == null) {
            throw new IllegalArgumentException(
                    "Treatment must not be null");
        }

        final String sql =
                "INSERT INTO treatments "
                        + "(appointment_id, treatment_type_id, description) "
                        + "VALUES (?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    treatment.getAppointmentId());

            statement.setInt(
                    2,
                    treatment.getTreatmentTypeId());

            statement.setString(
                    3,
                    treatment.getDescription());

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to save treatment",
                    exception);
        }
    }

    @Override
    public List<Treatment> findByAppointmentId(
            final int appointmentId)
            throws SQLException {

        final String sql =
                "SELECT treatment_id, appointment_id, "
                        + "treatment_type_id, description "
                        + "FROM treatments "
                        + "WHERE appointment_id = ? "
                        + "ORDER BY treatment_id";

        final List<Treatment> treatments =
                new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    appointmentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    final Treatment treatment =
                            new Treatment(
                                    resultSet.getInt(
                                            "treatment_id"),
                                    resultSet.getInt(
                                            "appointment_id"),
                                    resultSet.getInt(
                                            "treatment_type_id"),
                                    resultSet.getString(
                                            "description")
                            );

                    treatments.add(
                            treatment);
                }
            }

            return treatments;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to load treatments",
                    exception);
        }
    }

    @Override
    public int getTreatmentCount()
            throws SQLException {

        final String sql =
                "SELECT COUNT(*) AS total "
                        + "FROM treatments";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

            return 0;
        }
    }
}
