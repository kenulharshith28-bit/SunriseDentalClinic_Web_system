<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

    <title>Clinic Reports</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <style>

        .report-paper {
            background: #ffffff;
            max-width: 1100px;
            margin: 25px auto;
            padding: 35px;
            border: 1px solid #dcecf3;
            border-radius: 14px;
            box-sizing: border-box;
        }

        .report-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            gap: 20px;
            padding-bottom: 18px;
            margin-bottom: 25px;
            border-bottom: 2px solid #0f766e;
        }

        .report-brand {
            display: flex;
            align-items: center;
            gap: 14px;
        }

        .report-brand-icon {
            width: 48px;
            height: 48px;
            border-radius: 12px;
            background: #dff6fa;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 23px;
            color: #0f766e;
        }

        .report-header h1 {
            margin: 0;
            font-size: 26px;
            color: #173042;
        }

        .report-header p {
            margin: 5px 0 0;
            color: #70858f;
        }

        .report-type-label {
            font-weight: 600;
            color: #173042;
            text-align: right;
        }

        .report-title {
            margin-bottom: 22px;
        }

        .report-title h2 {
            margin: 0 0 5px;
            font-size: 22px;
            color: #173042;
        }

        .report-title p {
            margin: 0;
            color: #70858f;
        }

        .report-table-wrapper {
            width: 100%;
            overflow-x: auto;
        }

        .report-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            font-size: 14px;
        }

        .report-table th {
            background: #eaf8fa;
            color: #173042;
            font-weight: 600;
            padding: 12px;
            border: 1px solid #d7e8ed;
            text-align: left;
        }

        .report-table td {
            padding: 12px;
            border: 1px solid #d7e8ed;
            vertical-align: top;
        }

        .report-table tbody tr:nth-child(even) {
            background: #f8fbfc;
        }

        .report-footer {
            margin-top: 30px;
            padding-top: 15px;
            border-top: 1px solid #dcecf3;
            display: flex;
            justify-content: space-between;
            gap: 15px;
            color: #70858f;
            font-size: 12px;
        }

        .report-actions {
            max-width: 1100px;
            margin: 20px auto;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        .billing-report-content {
            white-space: pre-wrap;
            font-family: Arial, sans-serif;
            line-height: 1.7;
            margin: 0;
            color: #173042;
        }

        @media (max-width: 700px) {

            .report-paper {
                padding: 20px;
            }

            .report-header {
                flex-direction: column;
            }

            .report-type-label {
                text-align: left;
            }

            .report-footer {
                flex-direction: column;
            }

            .report-table {
                font-size: 12px;
            }

            .report-table th,
            .report-table td {
                padding: 8px;
            }
        }

        @media print {

            body {
                background: white !important;
            }

            .topbar,
            .page-title,
            .report-generator-card,
            .report-actions,
            .footer {
                display: none !important;
            }

            .page-container,
            .main-content {
                width: 100% !important;
                max-width: 100% !important;
                margin: 0 !important;
                padding: 0 !important;
            }

            .report-paper {
                width: 100%;
                max-width: 100%;
                margin: 0;
                padding: 0;
                border: none;
                border-radius: 0;
                box-shadow: none;
            }

            .report-table-wrapper {
                overflow: visible;
            }

            .report-table {
                width: 100%;
                font-size: 10px;
            }

            .report-table th,
            .report-table td {
                padding: 6px;
            }

            .report-table thead {
                display: table-header-group;
            }

            .report-table tr {
                page-break-inside: avoid;
            }

            @page {
                size: A4 landscape;
                margin: 12mm;
            }
        }

    </style>

</head>


<body class="dashboard-body">

<%
    request.setAttribute(
            "activePage",
            "reports");
%>


<div class="dashboard-shell">

<%@ include file="includes/sidebar.jsp" %>

<main class="dashboard-main">


<div class="page-container main-content">


    <!-- PAGE TITLE -->
    <div class="page-title">

        <h1>
            Reports
        </h1>

        <p>
            Generate and print clinic reports.
        </p>

    </div>


    <!-- ERROR -->
    <% if (request.getAttribute("errorMessage") != null) { %>

    <div class="alert alert-error">
        ${errorMessage}
    </div>

    <% } %>


    <!-- GENERATE REPORT -->
    <div class="card report-generator-card">

        <h2>
            Generate Report
        </h2>

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

                <i class="bi bi-file-earmark-bar-graph"></i>

                Generate Report

            </button>

        </form>

    </div>



    <!-- ========================================
         APPOINTMENT REPORT
         ======================================== -->

    <% if ("appointment".equals(
            request.getAttribute("reportType"))) { %>


    <div class="report-actions">

        <button class="btn btn-primary"
                type="button"
                onclick="window.print();">

            <i class="bi bi-printer"></i>

            Print Report

        </button>

    </div>


    <div class="report-paper">


        <!-- REPORT HEADER -->
        <div class="report-header">

            <div class="report-brand">

                <div class="report-brand-icon">

                    <i class="bi bi-heart-pulse-fill"></i>

                </div>

                <div>

                    <h1>
                        Sunrise Dental Clinic
                    </h1>

                    <p>
                        Clinic Management System
                    </p>

                </div>

            </div>


            <div class="report-type-label">

                Appointment Report

            </div>

        </div>


        <!-- REPORT TITLE -->
        <div class="report-title">

            <h2>
                Appointment Report
            </h2>

            <p>
                Complete appointment records maintained
                by Sunrise Dental Clinic.
            </p>

        </div>


        <!-- APPOINTMENT TABLE -->
        <div class="report-table-wrapper">

            <table class="report-table">

                <thead>

                <tr>

                    <th>
                        Appointment No.
                    </th>

                    <th>
                        Date
                    </th>

                    <th>
                        Time
                    </th>

                    <th>
                        Patient ID
                    </th>

                    <th>
                        Dentist ID
                    </th>

                    <th>
                        Status
                    </th>

                    <th>
                        Notes
                    </th>

                </tr>

                </thead>


                <tbody>

                <%
                    java.util.List<com.sunrisedental.model.Appointment>
                            appointments =
                            (java.util.List<com.sunrisedental.model.Appointment>)
                                    request.getAttribute("appointments");

                    if (appointments != null
                            && !appointments.isEmpty()) {

                        for (com.sunrisedental.model.Appointment appointment
                                : appointments) {
                %>


                <tr>

                    <td>
                        <strong>
                            <%= appointment.getAppointmentNumber() %>
                        </strong>
                    </td>


                    <td>
                        <%= appointment.getAppointmentDate() %>
                    </td>


                    <td>
                        <%= appointment.getAppointmentTime() %>
                    </td>


                    <td>
                        <%= appointment.getPatientId() %>
                    </td>


                    <td>
                        <%= appointment.getDentistId() %>
                    </td>


                    <td>
                        <%= appointment.getStatus() %>
                    </td>


                    <td>

                        <%
                            if (appointment.getNotes() != null
                                    && !appointment.getNotes().isBlank()) {
                        %>

                        <%= appointment.getNotes() %>

                        <%
                        } else {
                        %>

                        -

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

                    <td colspan="7"
                        style="text-align:center;">

                        No appointments found.

                    </td>

                </tr>


                <%
                    }
                %>

                </tbody>

            </table>

        </div>


        <!-- REPORT FOOTER -->
        <div class="report-footer">

            <span>
                Sunrise Dental Clinic
            </span>

            <span>
                Confidential Clinic Record
            </span>

        </div>

    </div>


    <% } %>



    <!-- ========================================
         BILLING REPORT
         ======================================== -->

    <% if ("bill".equals(
            request.getAttribute("reportType"))) { %>


    <div class="report-actions">

        <button class="btn btn-primary"
                type="button"
                onclick="window.print();">

            <i class="bi bi-printer"></i>

            Print Report

        </button>

    </div>


    <div class="report-paper">


        <!-- REPORT HEADER -->
        <div class="report-header">

            <div class="report-brand">

                <div class="report-brand-icon">

                    <i class="bi bi-heart-pulse-fill"></i>

                </div>

                <div>

                    <h1>
                        Sunrise Dental Clinic
                    </h1>

                    <p>
                        Clinic Management System
                    </p>

                </div>

            </div>


            <div class="report-type-label">
                Billing Report
            </div>

        </div>


        <div class="report-title">

            <h2>
                Billing Report
            </h2>

            <p>
                Billing records maintained by
                Sunrise Dental Clinic.
            </p>

        </div>


        <div class="report-table-wrapper">

            <table class="report-table">

                <thead>

                <tr>
                    <th>Bill ID</th>
                    <th>Appointment ID</th>
                    <th>Total Amount</th>
                </tr>

                </thead>

                <tbody>

                <%
                    java.util.List<com.sunrisedental.model.Bill> bills =
                            (java.util.List<com.sunrisedental.model.Bill>)
                                    request.getAttribute("bills");

                    if (bills != null
                            && !bills.isEmpty()) {

                        for (com.sunrisedental.model.Bill bill
                                : bills) {
                %>

                <tr>

                    <td>
                        <strong>
                            <%= bill.getBillId() %>
                        </strong>
                    </td>

                    <td>
                        <%= bill.getAppointmentId() %>
                    </td>

                    <td>
                        Rs. <%= bill.getTotalAmount() %>
                    </td>

                </tr>

                <%
                        }

                    } else {
                %>

                <tr>

                    <td colspan="3"
                        style="text-align:center;">

                        No billing records found.

                    </td>

                </tr>

                <%
                    }
                %>

                </tbody>

            </table>

        </div>


        <div class="report-footer">

            <span>
                Sunrise Dental Clinic
            </span>

            <span>
                Confidential Clinic Record
            </span>

        </div>

    </div>


    <% } %>


</div>


<div class="footer">
    Sunrise Dental Clinic Management System
</div>

</main>

</div>

</body>

</html>
