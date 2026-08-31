package com.sunrisedental.dao;

import com.sunrisedental.model.TreatmentType;
import com.sunrisedental.util.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreatmentTypeDAOImpl implements TreatmentTypeDAO {

    private final Connection connection;

    public TreatmentTypeDAOImpl(
            final Connection connection) {

        this.connection = connection;
    }

    public TreatmentTypeDAOImpl()
            throws SQLException {

        this(
                DBConnectionFactory
                        .getInstance()
                        .getConnection()
        );
    }

    @Override
    public List<TreatmentType> findAllTreatmentTypes()
            throws SQLException {

        final String sql =
                "SELECT treatment_type_id, treatment_name, treatment_fee "
                        + "FROM treatment_types "
                        + "ORDER BY treatment_name";

        final List<TreatmentType> treatmentTypes =
                new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                final TreatmentType treatmentType =
                        new TreatmentType(
                                resultSet.getInt(
                                        "treatment_type_id"),
                                resultSet.getString(
                                        "treatment_name"),
                                resultSet.getBigDecimal(
                                        "treatment_fee")
                        );

                treatmentTypes.add(
                        treatmentType);
            }

            return treatmentTypes;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to load treatment types",
                    exception);
        }
    }

    @Override
    public Optional<TreatmentType> findById(
            final int treatmentTypeId)
            throws SQLException {

        final String sql =
                "SELECT treatment_type_id, treatment_name, treatment_fee "
                        + "FROM treatment_types "
                        + "WHERE treatment_type_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    treatmentTypeId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                final TreatmentType treatmentType =
                        new TreatmentType(
                                resultSet.getInt(
                                        "treatment_type_id"),
                                resultSet.getString(
                                        "treatment_name"),
                                resultSet.getBigDecimal(
                                        "treatment_fee")
                        );

                return Optional.of(
                        treatmentType);
            }

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to find treatment type",
                    exception);
        }
    }

    @Override
    public boolean saveTreatmentType(
            final TreatmentType treatmentType)
            throws SQLException {

        if (treatmentType == null) {
            throw new IllegalArgumentException(
                    "Treatment type must not be null");
        }

        final String sql =
                "INSERT INTO treatment_types "
                        + "(treatment_name, treatment_fee) "
                        + "VALUES (?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    treatmentType.getTreatmentName());

            statement.setBigDecimal(
                    2,
                    treatmentType.getTreatmentFee());

            return statement.executeUpdate() == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to save treatment type",
                    exception);
        }
    }

    @Override
    public boolean deleteTreatmentType(
            final int treatmentTypeId)
            throws SQLException {

        final String sql =
                "DELETE FROM treatment_types "
                        + "WHERE treatment_type_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    treatmentTypeId);

            return statement.executeUpdate() == 1;
        }
    }
}