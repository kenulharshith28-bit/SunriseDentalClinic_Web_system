package com.sunrisedental.dao;

import com.sunrisedental.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for UserDAOImpl.
 */
class UserDAOTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {

        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        userDAO = new UserDAOImpl(connection);
    }

    @Test
    void shouldFindUserByUsername()
            throws SQLException {

        // ARRANGE
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true);

        when(resultSet.getInt("user_id"))
                .thenReturn(1);

        when(resultSet.getString("username"))
                .thenReturn("admin");

        when(resultSet.getString("password_hash"))
                .thenReturn("hashed-password");

        when(resultSet.getString("role"))
                .thenReturn("ADMIN");

        // ACT
        final Optional<User> result =
                userDAO.findByUsername("admin");

        // ASSERT
        assertTrue(
                result.isPresent(),
                "User should be returned when username exists");

        assertEquals(
                "admin",
                result.get().getUsername());

        verify(preparedStatement)
                .setString(1, "admin");

        verify(preparedStatement)
                .executeQuery();
    }
}