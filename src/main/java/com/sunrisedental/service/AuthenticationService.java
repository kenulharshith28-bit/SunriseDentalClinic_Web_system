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

        return userDAO.findByUsername(username)
                .map(user ->
                        user.getPasswordHash()
                                .equals(credential))
                .orElse(false);
    }
}