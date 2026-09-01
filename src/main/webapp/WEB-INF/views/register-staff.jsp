<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Register Staff</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-body">

<%
    request.setAttribute(
            "activePage",
            "register-staff");
%>

<div class="dashboard-shell">

<%@ include file="includes/sidebar.jsp" %>

<main class="dashboard-main">

<div class="page-container main-content">

    <div class="page-title">

        <h1>Staff Management</h1>

        <p>
            Register receptionist accounts and manage existing users.
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

    </div>

</div>

<div class="footer">
    Sunrise Dental Clinic Management System
</div>

</main>

</div>

</body>
</html>
