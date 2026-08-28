package com.sunrisedental.dao;

import com.sunrisedental.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import com.sunrisedental.util.DBConnectionFactory;

/**
 * JDBC implementation of user persistence operations.
 */
public class UserDAOImpl implements UserDAO {

    private final Connection connection;

    public UserDAOImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findByUsername(
            final String username)
            throws SQLException {

        final String sql =
                "SELECT * FROM users "
                        + "WHERE username = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    username);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    final User user =
                            new User(
                                    resultSet.getInt(
                                            "user_id"),
                                    resultSet.getString(
                                            "username"),
                                    resultSet.getString(
                                            "password_hash"),
                                    resultSet.getString(
                                            "role")
                            );

                    return Optional.of(user);
                }

                return Optional.empty();
            }

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to find user",
                    exception);
        }
    }

    public UserDAOImpl()
            throws SQLException {

        this(
                DBConnectionFactory
                        .getInstance()
                        .getConnection()
        );
    }
}
