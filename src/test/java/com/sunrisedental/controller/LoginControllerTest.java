package com.sunrisedental.controller;

import com.sunrisedental.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.RequestDispatcher;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    private AuthenticationService authenticationService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private LoginController controller;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {

        authenticationService =
                mock(AuthenticationService.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        controller =
                new LoginController(
                        authenticationService);
        dispatcher =
                mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(
                "/WEB-INF/views/login.jsp"))
                .thenReturn(dispatcher);
    }

    @Test
    void shouldAuthenticateUsingRequestParameters()
            throws Exception {

        when(request.getParameter(
                "username"))
                .thenReturn("receptionist");

        when(request.getParameter(
                "password"))
                .thenReturn("stored-credential");

        controller.doPost(
                request,
                response);

        verify(authenticationService)
                .authenticate(
                        "receptionist",
                        "stored-credential");
    }

    @Test
    void shouldRedirectToDashboardWhenAuthenticationSucceeds()
            throws Exception {

        when(request.getParameter(
                "username"))
                .thenReturn("receptionist");

        when(request.getParameter(
                "password"))
                .thenReturn("stored-credential");

        when(authenticationService.authenticate(
                "receptionist",
                "stored-credential"))
                .thenReturn(true);

        when(request.getContextPath())
                .thenReturn(
                        "/sunrise-dental-clinic");

        controller.doPost(
                request,
                response);

        verify(response)
                .sendRedirect(
                        "/sunrise-dental-clinic/dashboard");
    }

    @Test
    void shouldShowErrorWhenAuthenticationFails()
            throws Exception {

        when(request.getParameter(
                "username"))
                .thenReturn("receptionist");

        when(request.getParameter(
                "password"))
                .thenReturn("wrong-password");

        when(authenticationService.authenticate(
                "receptionist",
                "wrong-password"))
                .thenReturn(false);

        controller.doPost(
                request,
                response);

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "Invalid username or password");

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }
}