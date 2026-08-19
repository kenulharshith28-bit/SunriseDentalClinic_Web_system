package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;

    public UserService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> findUser(
            final String username)
            throws SQLException {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Username must not be blank");
        }

        return userDAO.findByUsername(username);
    }
}