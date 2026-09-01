<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>All Appointments</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-body">

<%
    request.setAttribute(
            "activePage",
            "appointments");
%>

<div class="dashboard-shell">

<%@ include file="includes/sidebar.jsp" %>

<main class="dashboard-main">

    <div class="page-container main-content">

        <div class="page-title">

            <h1>All Appointments</h1>

            <p>
                View appointment records and filter by date or status.
            </p>

            <a class="btn btn-secondary"
               href="${pageContext.request.contextPath}/appointments">

                Appointment Management

            </a>

        </div>

        <% if (request.getAttribute("errorMessage") != null) { %>

        <div class="alert alert-error">
            ${errorMessage}
        </div>

        <% } %>

        <div class="card">

            <h2>Filter Appointments</h2>

            <form method="get"
                  action="${pageContext.request.contextPath}/appointments/all">

                <div class="grid grid-2">

                    <div class="form-group">

                        <label for="appointmentDate">
                            Appointment Date
                        </label>

                        <input class="form-control"
                               type="date"
                               id="appointmentDate"
                               name="appointmentDate"
                               value="${filterAppointmentDate}">

                    </div>

                    <div class="form-group">

                        <label for="status">
                            Status
                        </label>

                        <select class="form-control"
                                id="status"
                                name="status">

                            <option value="ALL">
                                ALL
                            </option>

                            <option value="SCHEDULED"
                                    <%= "SCHEDULED".equals(request.getAttribute("filterStatus")) ? "selected" : "" %>>
                                SCHEDULED
                            </option>

                            <option value="COMPLETED"
                                    <%= "COMPLETED".equals(request.getAttribute("filterStatus")) ? "selected" : "" %>>
                                COMPLETED
                            </option>

                            <option value="CANCELLED"
                                    <%= "CANCELLED".equals(request.getAttribute("filterStatus")) ? "selected" : "" %>>
                                CANCELLED
                            </option>

                        </select>

                    </div>

                </div>

                <div class="form-actions">

                    <button class="btn btn-primary"
                            type="submit">

                        <i class="bi bi-funnel"
                           aria-hidden="true"></i>
                        Apply Filters

                    </button>

                    <a class="btn btn-secondary"
                       href="${pageContext.request.contextPath}/appointments/all">

                        Clear

                    </a>

                </div>

            </form>

        </div>

        <div class="card">

            <h2>Appointments</h2>

            <div class="table-wrapper">

                <table>

                    <thead>

                    <tr>
                        <th>Appointment Number</th>
                        <th>Appointment Date</th>
                        <th>Appointment Time</th>
                        <th>Patient</th>
                        <th>Dentist</th>
                        <th>Status</th>
                        <th>Notes</th>
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
                            ID: <%= appointment.getPatientId() %>
                        </td>

                        <td>
                            ID: <%= appointment.getDentistId() %>
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

        </div>

    </div>

    <div class="footer">
        Sunrise Dental Clinic Management System
    </div>

</main>

</div>

</body>
</html>
