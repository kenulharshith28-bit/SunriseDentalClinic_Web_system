package com.sunrisedental.controller;

import com.sunrisedental.model.User;
import com.sunrisedental.service.AuthenticationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    private AuthenticationService authenticationService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private HttpSession session;
    private LoginController controller;

    @BeforeEach
    void setUp() {

        authenticationService =
                mock(AuthenticationService.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        dispatcher =
                mock(RequestDispatcher.class);

        session =
                mock(HttpSession.class);

        controller =
                new LoginController(
                        authenticationService);

        when(request.getRequestDispatcher(
                "/WEB-INF/views/login.jsp"))
                .thenReturn(dispatcher);

        when(request.getSession())
                .thenReturn(session);

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");
    }

    @Test
    void shouldAuthenticateUsingRequestParameters()
            throws Exception {

        when(request.getParameter(
                "username"))
                .thenReturn("admin");

        when(request.getParameter(
                "password"))
                .thenReturn("admin123");

        when(authenticationService
                .authenticateUser(
                        "admin",
                        "admin123"))
                .thenReturn(
                        Optional.empty());

        controller.doPost(
                request,
                response);

        verify(authenticationService)
                .authenticateUser(
                        "admin",
                        "admin123");
    }

    @Test
    void shouldShowSuccessMessageBeforeDashboardRedirectWhenAuthenticationSucceeds()
            throws Exception {

        final User user =
                new User(
                        1,
                        "admin",
                        "admin123",
                        "ADMIN"
                );

        when(request.getParameter(
                "username"))
                .thenReturn("admin");

        when(request.getParameter(
                "password"))
                .thenReturn("admin123");

        when(authenticationService
                .authenticateUser(
                        "admin",
                        "admin123"))
                .thenReturn(
                        Optional.of(user));

        controller.doPost(
                request,
                response);

        verify(session)
                .setAttribute(
                        "userId",
                        1);

        verify(session)
                .setAttribute(
                        "username",
                        "admin");

        verify(session)
                .setAttribute(
                        "role",
                        "ADMIN");

        verify(request)
                .setAttribute(
                        "successMessage",
                        "Login Successful! Welcome to Sunrise Dental Clinic.");

        verify(request)
                .setAttribute(
                        "dashboardPath",
                        "/SunriseDentalClinic/dashboard");

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }

    @Test
    void shouldShowErrorWhenAuthenticationFails()
            throws Exception {

        when(request.getParameter(
                "username"))
                .thenReturn("admin");

        when(request.getParameter(
                "password"))
                .thenReturn("wrong-password");

        when(authenticationService
                .authenticateUser(
                        "admin",
                        "wrong-password"))
                .thenReturn(
                        Optional.empty());

        controller.doPost(
                request,
                response);

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Invalid username or password. Please try again.");

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }
}
