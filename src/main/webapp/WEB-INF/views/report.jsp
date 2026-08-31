<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Reports</title>

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

        </div>

    </div>

</div>

<div class="page-container main-content">

    <div class="page-title">

        <h1>Reports</h1>

        <p>
            Generate appointment and billing reports
            using current clinic data.
        </p>

    </div>

    <% if (request.getAttribute("errorMessage") != null) { %>

    <div class="alert alert-error">
        ${errorMessage}
    </div>

    <% } %>

    <div class="card">

        <h2>Generate Report</h2>

        <form method="get"
              action="${pageContext.request.contextPath}/reports">

            <div class="form-group">

                <label for="reportType">
                    Report Type
                </label>

                <select class="form-control"
                        id="reportType"
                        name="reportType"
                        required>

                    <option value="">
                        Select report type
                    </option>

                    <option value="appointment">
                        Appointment Report
                    </option>

                    <option value="bill">
                        Billing Report
                    </option>

                </select>

            </div>

            <button class="btn btn-primary"
                    type="submit">

                Generate Report

            </button>

        </form>

    </div>

    <% if (request.getAttribute("report") != null) { %>

    <div class="card">

        <h2>Generated Report</h2>

        <pre style="
            white-space: pre-wrap;
            font-family: inherit;
            line-height: 1.7;
            margin: 0;
        ">${report}</pre>

    </div>

    <% } %>

    <div class="card">

        <h3>Available Reports</h3>

        <div class="grid grid-2">

            <div class="dashboard-card">

                <h3>Appointment Report</h3>

                <p>
                    Shows appointment numbers,
                    dates, times and status information.
                </p>

            </div>

            <div class="dashboard-card">

                <h3>Billing Report</h3>

                <p>
                    Shows bill IDs, appointment IDs
                    and total amounts.
                </p>

            </div>

        </div>

    </div>

</div>

<div class="footer">
    Sunrise Dental Clinic Management System
</div>

</body>
</html>