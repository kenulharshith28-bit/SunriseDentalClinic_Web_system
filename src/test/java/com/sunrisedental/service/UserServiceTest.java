package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserDAO userDAO;
    private UserService userService;

    @BeforeEach
    void setUp() {

        userDAO = mock(UserDAO.class);
        userService = new UserService(userDAO);
    }

    @Test
    void shouldReturnUserWhenUsernameExists()
            throws SQLException {

        final User user =
                new User(
                        1,
                        "admin",
                        "hashed-password",
                        "ADMIN"
                );

        when(userDAO.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        final Optional<User> result =
                userService.findUser("admin");

        assertTrue(
                result.isPresent(),
                "User should be returned when username exists");

        assertEquals(
                "admin",
                result.get().getUsername());

        verify(userDAO)
                .findByUsername("admin");
    }

    @Test
    void shouldRejectBlankUsername() {

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.findUser("   ")
        );
    }
}