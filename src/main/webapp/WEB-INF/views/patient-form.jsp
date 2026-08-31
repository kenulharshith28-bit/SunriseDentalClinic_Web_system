<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add New Patient</title>

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

        <h1>Add New Patient</h1>

        <p>
            Register a patient before creating their appointment.
        </p>

    </div>

    <% if (request.getAttribute("errorMessage") != null) { %>

    <div class="alert alert-error">
        ${errorMessage}
    </div>

    <% } %>

    <div class="card">

        <form method="post"
              action="${pageContext.request.contextPath}/patients/new">

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

                <label for="phone">
                    Phone Number
                </label>

                <input class="form-control"
                       type="text"
                       id="phone"
                       name="phone"
                       placeholder="Example: 0771234567">

            </div>

            <button class="btn btn-primary"
                    type="submit">

                Save Patient

            </button>

            <a class="btn btn-secondary"
               href="${pageContext.request.contextPath}/appointments">

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