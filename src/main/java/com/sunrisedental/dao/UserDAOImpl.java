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

    @Override
    public boolean saveUser(
            final User user)
            throws SQLException {

        if (user == null) {
            throw new IllegalArgumentException(
                    "User must not be null");
        }

        final String sql =
                "INSERT INTO users "
                        + "(username, password_hash, role) "
                        + "VALUES (?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    user.getUsername());

            statement.setString(
                    2,
                    user.getPasswordHash());

            statement.setString(
                    3,
                    user.getRole());

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to save user",
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

    @Override
    public boolean updatePassword(
            final int userId,
            final String newPassword)
            throws SQLException {

        final String sql =
                "UPDATE users "
                        + "SET password_hash = ? "
                        + "WHERE user_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    newPassword);

            statement.setInt(
                    2,
                    userId);

            final int affectedRows =
                    statement.executeUpdate();

            return affectedRows == 1;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to update password",
                    exception);
        }
    }
}
