<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Register Staff</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="topbar">

    <div class="page-container topbar-content">

        <div class="brand">
            Sunrise Dental Clinic
        </div>

        <div class="nav-links">

            <a href="${pageContext.request.contextPath}/dashboard">
                Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/appointments">
                Appointments
            </a>

            <a href="${pageContext.request.contextPath}/treatments">
                Treatments
            </a>

            <a href="${pageContext.request.contextPath}/bills">
                Billing
            </a>

            <a href="${pageContext.request.contextPath}/reports">
                Reports
            </a>

        </div>

    </div>

</div>

<div class="page-container main-content">

    <div class="page-title">

<<<<<<< HEAD
        <h1>Register Staff</h1>

        <p>
            Create a new receptionist account for the clinic system.
=======
        <h1>Staff Management</h1>

        <p>
            Register receptionist accounts and manage existing users.
>>>>>>> main
        </p>

    </div>

    <% if (request.getAttribute("successMessage") != null) { %>

    <div class="alert alert-success">
        ${successMessage}
    </div>

    <% } %>

    <% if (request.getAttribute("errorMessage") != null) { %>

    <div class="alert alert-error">
        ${errorMessage}
    </div>

    <% } %>

<<<<<<< HEAD
    <div class="card">

        <h2>Staff Account Details</h2>

        <form method="post"
              action="${pageContext.request.contextPath}/users/register">

            <div class="form-group">

                <label for="username">
                    Username
                </label>

                <input class="form-control"
                       type="text"
                       id="username"
                       name="username"
                       placeholder="Enter staff username"
                       required>

            </div>

            <div class="form-group">

                <label for="password">
                    Password
                </label>

                <input class="form-control"
                       type="password"
                       id="password"
                       name="password"
                       placeholder="Enter temporary password"
                       required>

            </div>

            <div class="form-group">

                <label>
                    Role
                </label>

                <input class="form-control"
                       type="text"
                       value="RECEPTIONIST"
                       disabled>

            </div>

            <button class="btn btn-primary"
                    type="submit">

                Register Staff

            </button>

            <a class="btn btn-secondary"
               href="${pageContext.request.contextPath}/dashboard">

                Cancel

            </a>

        </form>
=======
    <div class="grid grid-2">

        <div class="card">

            <h2>Register Staff</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/users/register">

                <div class="form-group">

                    <label for="username">
                        Username
                    </label>

                    <input class="form-control"
                           type="text"
                           id="username"
                           name="username"
                           placeholder="Enter staff username"
                           required>

                </div>

                <div class="form-group">

                    <label for="password">
                        Password
                    </label>

                    <input class="form-control"
                           type="password"
                           id="password"
                           name="password"
                           placeholder="Enter temporary password"
                           required>

                </div>

                <div class="form-group">

                    <label>
                        Role
                    </label>

                    <input class="form-control"
                           type="text"
                           value="RECEPTIONIST"
                           disabled>

                </div>

                <button class="btn btn-primary"
                        type="submit">

                    Register Staff

                </button>

            </form>

        </div>

        <div class="card">

            <h2>Staff Access</h2>

            <p>
                Receptionists can manage appointments,
                treatments, billing and reports.
            </p>

            <p>
                Administrative functions remain restricted
                to ADMIN accounts.
            </p>

        </div>

    </div>


    <div class="card">

        <h2>System Users</h2>

        <div class="table-wrapper">

            <table>

                <thead>

                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Action</th>
                </tr>

                </thead>

                <tbody>

                <%
                    java.util.List<com.sunrisedental.model.User> users =
                            (java.util.List<com.sunrisedental.model.User>)
                                    request.getAttribute("users");

                    Integer currentUserId =
                            (Integer) session.getAttribute("userId");

                    if (users != null && !users.isEmpty()) {

                        for (com.sunrisedental.model.User user : users) {
                %>

                <tr>

                    <td>
                        <%= user.getUserId() %>
                    </td>

                    <td>
                        <%= user.getUsername() %>
                    </td>

                    <td>
                        <%= user.getRole() %>
                    </td>

                    <td>

                        <%
                            if (currentUserId != null
                                    && currentUserId == user.getUserId()) {
                        %>

                        <span>
                            Current Account
                        </span>

                        <%
                        } else {
                        %>

                        <form method="post"
                              action="${pageContext.request.contextPath}/users/register">

                            <input type="hidden"
                                   name="action"
                                   value="delete">

                            <input type="hidden"
                                   name="userId"
                                   value="<%= user.getUserId() %>">

                            <button class="btn btn-danger"
                                    type="submit"
                                    onclick="return confirm('Are you sure you want to remove this user?');">

                                Remove

                            </button>

                        </form>

                        <%
                            }
                        %>

                    </td>

                </tr>

                <%
                    }

                } else {
                %>

                <tr>

                    <td colspan="4">
                        No users found.
                    </td>

                </tr>

                <%
                    }
                %>

                </tbody>

            </table>

        </div>
>>>>>>> main

    </div>

</div>

<div class="footer">
    Sunrise Dental Clinic Management System
</div>

</body>
</html>