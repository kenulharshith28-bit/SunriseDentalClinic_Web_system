package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationServiceTest {

    private UserDAO userDAO;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {

        userDAO =
                mock(UserDAO.class);

        authenticationService =
                new AuthenticationService(
                        userDAO);
    }

    @Test
    void shouldAuthenticateUserWhenCredentialsMatch()
            throws Exception {

        final User user =
                new User(
                        1,
                        "receptionist",
                        "stored-credential",
                        "RECEPTIONIST"
                );

        when(userDAO.findByUsername(
                "receptionist"))
                .thenReturn(
                        Optional.of(user));

        final boolean authenticated =
                authenticationService.authenticate(
                        "receptionist",
                        "stored-credential");

        assertTrue(
                authenticated,
                "User should be authenticated when credentials match");
    }
}