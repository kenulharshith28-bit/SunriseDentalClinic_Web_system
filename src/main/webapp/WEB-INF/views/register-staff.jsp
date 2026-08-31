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

        <h1>Register Staff</h1>

        <p>
            Create a new receptionist account for the clinic system.
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

    </div>

</div>

<div class="footer">
    Sunrise Dental Clinic Management System
</div>

</body>
</html>