package com.sunrisedental.dao;

import com.sunrisedental.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

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

        // user lookup has not yet been implemented.
        return Optional.empty();
    }
}