<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Manage Dentists</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-body">

<%
    request.setAttribute(
            "activePage",
            "dentists");
%>

<div class="dashboard-shell">

<%@ include file="includes/sidebar.jsp" %>

<main class="dashboard-main">

<div class="page-container main-content">

    <div class="page-title">

        <h1>Manage Dentists</h1>

        <p>
            Add dentists and view all dentists registered
            in the clinic system.
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

        <!-- ADD DENTIST -->
        <div class="card">

            <h2>Add New Dentist</h2>

            <form method="post"
                  action="${pageContext.request.contextPath}/dentists">

                <div class="grid grid-2">

                    <div class="form-group">

                        <label for="firstName">
                            First Name
                        </label>

                        <input class="form-control"
                               type="text"
                               id="firstName"
                               name="firstName"
                               placeholder="Enter first name"
                               required>

                    </div>

                    <div class="form-group">

                        <label for="lastName">
                            Last Name
                        </label>

                        <input class="form-control"
                               type="text"
                               id="lastName"
                               name="lastName"
                               placeholder="Enter last name"
                               required>

                    </div>

                </div>

                <div class="form-group">

                    <label for="specialization">
                        Specialization
                    </label>

                    <input class="form-control"
                           type="text"
                           id="specialization"
                           name="specialization"
                           placeholder="Example: Orthodontist">

                </div>

                <div class="form-group">

                    <label for="phone">
                        Phone
                    </label>

                    <input class="form-control"
                           type="text"
                           id="phone"
                           name="phone"
                           placeholder="Enter phone number">

                </div>

                <div class="form-group">

                    <label for="email">
                        Email
                    </label>

                    <input class="form-control"
                           type="email"
                           id="email"
                           name="email"
                           placeholder="Enter email address">

                </div>

                <button class="btn btn-primary"
                        type="submit">

                    Add Dentist

                </button>

            </form>

        </div>


    </div>


    <!-- DENTIST LIST -->
    <div class="card">

        <h2>Registered Dentists</h2>

        <div class="table-wrapper">

            <table>

                <thead>

                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Specialization</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th>Action</th>
                </tr>

                </thead>

                <tbody>

                <%
                    java.util.List<com.sunrisedental.model.Dentist> dentists =
                            (java.util.List<com.sunrisedental.model.Dentist>)
                                    request.getAttribute("dentists");

                    if (dentists != null && !dentists.isEmpty()) {

                        for (com.sunrisedental.model.Dentist dentist
                                : dentists) {
                %>

                <tr>

                    <td>
                        <%= dentist.getDentistId() %>
                    </td>

                    <td>
                        Dr. <%= dentist.getFullName() %>
                    </td>

                    <td>
                        <%= dentist.getSpecialization() != null
                                ? dentist.getSpecialization()
                                : "-" %>
                    </td>

                    <td>
                        <%= dentist.getPhone() != null
                                ? dentist.getPhone()
                                : "-" %>
                    </td>

                    <td>
                        <%= dentist.getEmail() != null
                                ? dentist.getEmail()
                                : "-" %>
                    </td>

                    <td>

                        <form method="post"
                              action="${pageContext.request.contextPath}/dentists">

                            <input type="hidden"
                                   name="action"
                                   value="delete">

                            <input type="hidden"
                                   name="dentistId"
                                   value="<%= dentist.getDentistId() %>">

                            <button class="btn btn-danger"
                                    type="submit"
                                    onclick="return confirm('Are you sure you want to remove this dentist?');">

                                Remove

                            </button>

                        </form>

                    </td>

                </tr>

                <%
                    }

                } else {
                %>

                <tr>

                    <td colspan="6">
                        No dentists have been added yet.
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
