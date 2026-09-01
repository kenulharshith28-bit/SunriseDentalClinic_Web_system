package com.sunrisedental.controller;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.dao.UserDAOImpl;
import com.sunrisedental.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/users/admins")
public class AdminManagementController extends HttpServlet {

    private final UserDAO userDAO;

    public AdminManagementController() {

        try {

            this.userDAO =
                    new UserDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize admin management controller",
                    exception);
        }
    }

    public AdminManagementController(
            final UserDAO userDAO) {

        this.userDAO =
                userDAO;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard");

            return;
        }

        try {

            loadAdmins(
                    request);

            forwardToPage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Failed to load administrators",
                    exception);
        }
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard");

            return;
        }

        final String action =
                request.getParameter(
                        "action");

        if ("delete".equals(action)) {

            deleteAdmin(
                    request,
                    response);

            return;
        }

        createAdmin(
                request,
                response);
    }

    private void createAdmin(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final String username =
                request.getParameter(
                        "username");

        final String password =
                request.getParameter(
                        "password");

        if (username == null
                || username.isBlank()
                || password == null
                || password.isBlank()) {

            request.setAttribute(
                    "errorMessage",
                    "Username and password are required");

            loadAdminsSafely(
                    request);

            forwardToPage(
                    request,
                    response);

            return;
        }

        try {

            final Optional<User> existingUser =
                    userDAO.findByUsername(
                            username.trim());

            if (existingUser.isPresent()) {

                request.setAttribute(
                        "errorMessage",
                        "Username already exists");

                loadAdmins(
                        request);

                forwardToPage(
                        request,
                        response);

                return;
            }

            final User admin =
                    new User(
                            0,
                            username.trim(),
                            password,
                            "ADMIN"
                    );

            final boolean saved =
                    userDAO.saveUser(
                            admin);

            if (saved) {

                request.setAttribute(
                        "successMessage",
                        "Admin account created successfully");

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Admin account could not be created");
            }

            loadAdmins(
                    request);

            forwardToPage(
                    request,
                    response);

        } catch (SQLException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Unable to create admin account");

            loadAdminsSafely(
                    request);

            forwardToPage(
                    request,
                    response);
        }
    }

    private void deleteAdmin(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final String userIdValue =
                request.getParameter(
                        "userId");

        try {

            final int userId =
                    Integer.parseInt(
                            userIdValue);

            final List<User> admins =
                    findAdmins();

            final Optional<User> targetAdmin =
                    admins.stream()
                            .filter(user -> user.getUserId() == userId)
                            .findFirst();

            if (targetAdmin.isEmpty()) {

                request.setAttribute(
                        "errorMessage",
                        "Admin account was not found");

                loadAdmins(
                        request);

                forwardToPage(
                        request,
                        response);

                return;
            }

            if (isCurrentAdmin(
                    request,
                    targetAdmin.get())) {

                request.setAttribute(
                        "errorMessage",
                        "You cannot remove your own admin account");

                request.setAttribute(
                        "admins",
                        admins);

                forwardToPage(
                        request,
                        response);

                return;
            }

            final boolean deleted =
                    userDAO.deleteUser(
                            userId);

            if (deleted) {

                request.setAttribute(
                        "successMessage",
                        "Admin account removed successfully");

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Admin account was not found");
            }

            loadAdmins(
                    request);

            forwardToPage(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Invalid admin ID");

            loadAdminsSafely(
                    request);

            forwardToPage(
                    request,
                    response);

        } catch (SQLException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Unable to remove admin account");

            loadAdminsSafely(
                    request);

            forwardToPage(
                    request,
                    response);
        }
    }

    private void loadAdmins(
            final HttpServletRequest request)
            throws SQLException {

        request.setAttribute(
                "admins",
                findAdmins());
    }

    private void loadAdminsSafely(
            final HttpServletRequest request) {

        try {

            loadAdmins(
                    request);

        } catch (SQLException exception) {

            request.setAttribute(
                    "admins",
                    List.of());
        }
    }

    private List<User> findAdmins()
            throws SQLException {

        return userDAO.findAllUsers()
                .stream()
                .filter(user ->
                        "ADMIN".equals(
                                user.getRole()))
                .toList();
    }

    private boolean isCurrentAdmin(
            final HttpServletRequest request,
            final User targetAdmin) {

        final HttpSession session =
                request.getSession(false);

        if (session == null) {
            return false;
        }

        final Object currentUserId =
                session.getAttribute(
                        "userId");

        if (currentUserId instanceof Number number
                && number.intValue() == targetAdmin.getUserId()) {

            return true;
        }

        final String currentUsername =
                (String) session.getAttribute(
                        "username");

        return currentUsername != null
                && currentUsername.equals(
                        targetAdmin.getUsername());
    }

    private boolean isAdmin(
            final HttpServletRequest request) {

        final HttpSession session =
                request.getSession(false);

        if (session == null) {
            return false;
        }

        final String role =
                (String) session.getAttribute(
                        "role");

        return "ADMIN".equals(role);
    }

    private void forwardToPage(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/manage-admins.jsp");

        dispatcher.forward(
                request,
                response);
    }
}
