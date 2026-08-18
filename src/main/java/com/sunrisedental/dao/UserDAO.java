package com.sunrisedental.dao;

import com.sunrisedental.model.User;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Defines persistence operations for system users.
 */
public interface UserDAO {

    /**
     * Finds a user using the username.
     *
     * @param username username to search for
     * @return matching user when found, otherwise an empty Optional
     * @throws SQLException if a database operation fails
     */
    Optional<User> findByUsername(
            final String username)
            throws SQLException;
}