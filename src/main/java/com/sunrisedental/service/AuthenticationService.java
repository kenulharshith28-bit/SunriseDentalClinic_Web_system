package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;

import java.sql.SQLException;

public class AuthenticationService {

    private final UserDAO userDAO;

    public AuthenticationService(
            final UserDAO userDAO) {

        this.userDAO = userDAO;
    }

    public boolean authenticate(
            final String username,
            final String credential)
            throws SQLException {

        if (username == null
                || username.isBlank()) {

            throw new IllegalArgumentException(
                    "Username must not be blank");
        }

        if (credential == null
                || credential.isBlank()) {

            throw new IllegalArgumentException(
                    "Credential must not be blank");
        }

        try {
            return userDAO.findByUsername(username)
                    .map(user ->
                            user.getPasswordHash()
                                    .equals(credential))
                    .orElse(false);

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to authenticate user",
                    exception);
        }
    }
}