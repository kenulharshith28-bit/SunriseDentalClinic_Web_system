<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Manage Admins</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-body">

<%
    request.setAttribute(
            "activePage",
            "admins");
%>

<div class="dashboard-shell">

<%@ include file="includes/sidebar.jsp" %>

<main class="dashboard-main">

<div class="page-container main-content">

    <div class="page-title">

        <h1>Manage Admins</h1>

        <p>
            Create administrator accounts and manage existing administrators.
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

    <div class="admin-management-grid">

        <div class="card">

            <h2>Create Admin Account</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/users/admins">

                <div class="grid grid-2 admin-form-grid">

                    <div class="form-group">

                        <label for="username">
                            Username
                        </label>

                        <input class="form-control"
                               type="text"
                               id="username"
                               name="username"
                               placeholder="Enter admin username"
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

                </div>

                <div class="form-group">

                    <label>
                        Role
                    </label>

                    <input class="form-control"
                           type="text"
                           value="ADMIN"
                           disabled>

                </div>

                <button class="btn btn-primary"
                        type="submit">

                    <i class="bi bi-shield-plus"
                       aria-hidden="true"></i>

                    Create Admin

                </button>

            </form>

        </div>

    </div>

    <div class="card">

        <h2>Administrator Accounts</h2>

        <div class="table-wrapper">

            <table class="admin-table">

                <thead>

                <tr>
                    <th>User ID</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Action</th>
                </tr>

                </thead>

                <tbody>

                <%
                    java.util.List<com.sunrisedental.model.User> admins =
                            (java.util.List<com.sunrisedental.model.User>)
                                    request.getAttribute("admins");

                    Object currentUserIdObject =
                            session.getAttribute("userId");

                    Integer currentUserId =
                            currentUserIdObject instanceof Number
                                    ? ((Number) currentUserIdObject).intValue()
                                    : null;

                    String currentUsername =
                            (String) session.getAttribute("username");

                    if (admins != null && !admins.isEmpty()) {

                        for (com.sunrisedental.model.User admin : admins) {

                            boolean currentAccount =
                                    currentUserId != null
                                            && currentUserId == admin.getUserId();

                            if (!currentAccount
                                    && currentUsername != null) {

                                currentAccount =
                                        currentUsername.equals(
                                                admin.getUsername());
                            }
                %>

                <tr>

                    <td>
                        <%= admin.getUserId() %>
                    </td>

                    <td>
                        <%= admin.getUsername() %>
                    </td>

                    <td>
                        <%= admin.getRole() %>
                    </td>

                    <td>

                        <% if (currentAccount) { %>

                        <span class="status-label">
                            Current Account
                        </span>

                        <% } else { %>

                        <form class="table-action-form"
                              method="post"
                              action="${pageContext.request.contextPath}/users/admins">

                            <input type="hidden"
                                   name="action"
                                   value="delete">

                            <input type="hidden"
                                   name="userId"
                                   value="<%= admin.getUserId() %>">

                            <button class="btn btn-danger"
                                    type="submit"
                                    onclick="return confirm('Are you sure you want to remove this admin?');">

                                Remove

                            </button>

                        </form>

                        <% } %>

                    </td>

                </tr>

                <%
                        }

                    } else {
                %>

                <tr>

                    <td colspan="4">
                        No administrator accounts found.
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
