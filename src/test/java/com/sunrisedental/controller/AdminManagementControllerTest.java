package com.sunrisedental.controller;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminManagementControllerTest {

    private UserDAO userDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private AdminManagementController controller;

    @BeforeEach
    void setUp() {

        userDAO =
                mock(UserDAO.class);

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        session =
                mock(HttpSession.class);

        dispatcher =
                mock(RequestDispatcher.class);

        controller =
                new AdminManagementController(
                        userDAO);

        when(request.getSession(false))
                .thenReturn(session);

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");

        when(request.getRequestDispatcher(
                "/WEB-INF/views/manage-admins.jsp"))
                .thenReturn(dispatcher);
    }

    @Test
    void shouldLoadPageForAdmin()
            throws Exception {

        mockAdminSession();

        when(userDAO.findAllUsers())
                .thenReturn(
                        List.of(adminUser()));

        controller.doGet(
                request,
                response);

        verify(dispatcher)
                .forward(
                        request,
                        response);
    }

    @Test
    void shouldRejectNonAdmin()
            throws Exception {

        when(session.getAttribute("role"))
                .thenReturn("RECEPTIONIST");

        controller.doGet(
                request,
                response);

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/dashboard");
    }

    @Test
    void shouldCreateAnotherAdmin()
            throws Exception {

        mockAdminSession();

        when(request.getParameter("username"))
                .thenReturn("second-admin");

        when(request.getParameter("password"))
                .thenReturn("temporary-password");

        when(userDAO.findByUsername("second-admin"))
                .thenReturn(
                        Optional.empty());

        when(userDAO.saveUser(
                argThat(user ->
                        user != null
                                && "second-admin".equals(
                                user.getUsername())
                                && "temporary-password".equals(
                                user.getPasswordHash())
                                && "ADMIN".equals(
                                user.getRole()))))
                .thenReturn(true);

        when(userDAO.findAllUsers())
                .thenReturn(
                        List.of(
                                adminUser(),
                                new User(
                                        2,
                                        "second-admin",
                                        "temporary-password",
                                        "ADMIN")));

        controller.doPost(
                request,
                response);

        verify(request)
                .setAttribute(
                        "successMessage",
                        "Admin account created successfully");
    }

    @Test
    void shouldLoadOnlyAdminUsers()
            throws Exception {

        mockAdminSession();

        final User admin =
                adminUser();

        final User staff =
                new User(
                        3,
                        "reception",
                        "password",
                        "RECEPTIONIST");

        when(userDAO.findAllUsers())
                .thenReturn(
                        List.of(
                                staff,
                                admin));

        controller.doGet(
                request,
                response);

        verify(request)
                .setAttribute(
                        eq("admins"),
                        argThat(value -> {
                            if (!(value instanceof List<?> admins)) {
                                return false;
                            }

                            return admins.size() == 1
                                    && admin.equals(
                                    admins.get(0));
                        }));
    }

    @Test
    void shouldPreventCurrentAdminFromDeletingSelf()
            throws Exception {

        mockAdminSession();

        when(request.getParameter("action"))
                .thenReturn("delete");

        when(request.getParameter("userId"))
                .thenReturn("1");

        when(userDAO.findAllUsers())
                .thenReturn(
                        List.of(
                                adminUser(),
                                otherAdminUser()));

        controller.doPost(
                request,
                response);

        verify(userDAO,
                never())
                .deleteUser(1);

        verify(request)
                .setAttribute(
                        "errorMessage",
                        "You cannot remove your own admin account");
    }

    @Test
    void shouldDeleteAnotherAdmin()
            throws Exception {

        mockAdminSession();

        when(request.getParameter("action"))
                .thenReturn("delete");

        when(request.getParameter("userId"))
                .thenReturn("2");

        when(userDAO.findAllUsers())
                .thenReturn(
                        List.of(
                                adminUser(),
                                otherAdminUser()))
                .thenReturn(
                        List.of(
                                adminUser()));

        when(userDAO.deleteUser(2))
                .thenReturn(true);

        controller.doPost(
                request,
                response);

        verify(userDAO)
                .deleteUser(2);

        verify(request)
                .setAttribute(
                        "successMessage",
                        "Admin account removed successfully");
    }

    private void mockAdminSession() {

        when(session.getAttribute("role"))
                .thenReturn("ADMIN");

        when(session.getAttribute("userId"))
                .thenReturn(1);

        when(session.getAttribute("username"))
                .thenReturn("admin");
    }

    private User adminUser() {

        return new User(
                1,
                "admin",
                "admin-password",
                "ADMIN");
    }

    private User otherAdminUser() {

        return new User(
                2,
                "second-admin",
                "temporary-password",
                "ADMIN");
    }
}
