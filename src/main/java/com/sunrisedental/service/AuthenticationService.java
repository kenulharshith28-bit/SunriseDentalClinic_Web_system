package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;

import java.sql.SQLException;
import java.util.Optional;

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

        return authenticateUser(
                username,
                credential)
                .isPresent();
    }

    public Optional<User> authenticateUser(
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
                    "Password must not be blank");
        }

        try {

            final Optional<User> user =
                    userDAO.findByUsername(
                            username);

            if (user.isEmpty()) {
                return Optional.empty();
            }

            if (!user.get()
                    .getPasswordHash()
                    .equals(credential)) {

                return Optional.empty();
            }

            return user;

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to authenticate user",
                    exception);
        }
    }
}