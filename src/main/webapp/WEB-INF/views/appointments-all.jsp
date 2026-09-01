<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

    <title>Manage Appointments</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>


<body class="dashboard-body">

<%
    request.setAttribute(
            "activePage",
            "manage-appointments");
%>


<div class="dashboard-shell">

    <%@ include file="includes/sidebar.jsp" %>


    <main class="dashboard-main">

        <div class="page-container main-content">


            <!-- PAGE TITLE -->
            <div class="page-title">

                <h1>Manage Appointments</h1>

                <p>
                    View complete appointment information,
                    filter records and manage scheduled appointments.
                </p>

            </div>


            <!-- SUCCESS -->
            <% if (request.getAttribute("successMessage") != null) { %>

            <div class="alert alert-success">

                <i class="bi bi-check-circle"
                   aria-hidden="true"></i>

                ${successMessage}

            </div>

            <% } %>


            <!-- ERROR -->
            <% if (request.getAttribute("errorMessage") != null) { %>

            <div class="alert alert-error">

                <i class="bi bi-exclamation-circle"
                   aria-hidden="true"></i>

                ${errorMessage}

            </div>

            <% } %>


            <!-- FILTERS -->
            <div class="card">

                <div class="appointment-form-header">

                    <div>

                        <h2>
                            Filter Appointments
                        </h2>

                        <p>
                            Filter appointment records by date or status.
                        </p>

                    </div>

                    <div class="appointment-form-icon">

                        <i class="bi bi-funnel"
                           aria-hidden="true"></i>

                    </div>

                </div>


                <form method="get"
                      action="${pageContext.request.contextPath}/appointments/all">


                    <div class="grid grid-2">


                        <!-- DATE -->
                        <div class="form-group">

                            <label for="appointmentDate">

                                <i class="bi bi-calendar3"
                                   aria-hidden="true"></i>

                                Appointment Date

                            </label>

                            <input class="form-control"
                                   type="date"
                                   id="appointmentDate"
                                   name="appointmentDate"
                                   value="${filterAppointmentDate}">

                        </div>


                        <!-- STATUS -->
                        <div class="form-group">

                            <label for="status">

                                <i class="bi bi-check-circle"
                                   aria-hidden="true"></i>

                                Status

                            </label>

                            <select class="form-control"
                                    id="status"
                                    name="status">


                                <option value="ALL"
                                        <%= "ALL".equals(
                                                request.getAttribute(
                                                        "filterStatus"))
                                                ? "selected"
                                                : "" %>>

                                    ALL

                                </option>


                                <option value="SCHEDULED"
                                        <%= "SCHEDULED".equals(
                                                request.getAttribute(
                                                        "filterStatus"))
                                                ? "selected"
                                                : "" %>>

                                    SCHEDULED

                                </option>


                                <option value="COMPLETED"
                                        <%= "COMPLETED".equals(
                                                request.getAttribute(
                                                        "filterStatus"))
                                                ? "selected"
                                                : "" %>>

                                    COMPLETED

                                </option>


                                <option value="CANCELLED"
                                        <%= "CANCELLED".equals(
                                                request.getAttribute(
                                                        "filterStatus"))
                                                ? "selected"
                                                : "" %>>

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

                            <i class="bi bi-x-circle"
                               aria-hidden="true"></i>

                            Clear

                        </a>

                    </div>

                </form>

            </div>


            <!-- APPOINTMENT TABLE -->
            <div class="card">

                <div class="appointment-form-header">

                    <div>

                        <h2>
                            Appointment Records
                        </h2>

                        <p>
                            Complete patient, dentist and appointment details.
                        </p>

                    </div>

                    <div class="appointment-form-icon">

                        <i class="bi bi-calendar2-week"
                           aria-hidden="true"></i>

                    </div>

                </div>


                <%
                    /*
                     * -----------------------------------------
                     * LOAD DATA FROM CONTROLLER
                     * -----------------------------------------
                     */

                    java.util.List<com.sunrisedental.model.Appointment>
                            appointments =
                            (java.util.List<com.sunrisedental.model.Appointment>)
                                    request.getAttribute(
                                            "appointments");


                    java.util.List<com.sunrisedental.model.Patient>
                            patients =
                            (java.util.List<com.sunrisedental.model.Patient>)
                                    request.getAttribute(
                                            "patients");


                    java.util.List<com.sunrisedental.model.Dentist>
                            dentists =
                            (java.util.List<com.sunrisedental.model.Dentist>)
                                    request.getAttribute(
                                            "dentists");


                    /*
                     * Create lookup maps so we can convert:
                     *
                     * patientId -> Patient
                     * dentistId -> Dentist
                     */

                    java.util.Map<Integer, com.sunrisedental.model.Patient>
                            patientMap =
                            new java.util.HashMap<>();


                    java.util.Map<Integer, com.sunrisedental.model.Dentist>
                            dentistMap =
                            new java.util.HashMap<>();


                    if (patients != null) {

                        for (com.sunrisedental.model.Patient patient
                                : patients) {

                            patientMap.put(
                                    patient.getPatientId(),
                                    patient);
                        }
                    }


                    if (dentists != null) {

                        for (com.sunrisedental.model.Dentist dentist
                                : dentists) {

                            dentistMap.put(
                                    dentist.getDentistId(),
                                    dentist);
                        }
                    }
                %>


                <div class="table-wrapper">

                    <table class="appointment-management-table">

                        <thead>

                        <tr>

                            <th>
                                Appointment
                            </th>

                            <th>
                                Patient Details
                            </th>

                            <th>
                                Dentist
                            </th>

                            <th>
                                Schedule
                            </th>

                            <th>
                                Status
                            </th>

                            <th>
                                Notes
                            </th>

                            <th>
                                Action
                            </th>

                        </tr>

                        </thead>


                        <tbody>


                        <%
                            if (appointments != null
                                    && !appointments.isEmpty()) {

                                for (com.sunrisedental.model.Appointment appointment
                                        : appointments) {


                                    com.sunrisedental.model.Patient patient =
                                            patientMap.get(
                                                    appointment
                                                            .getPatientId());


                                    com.sunrisedental.model.Dentist dentist =
                                            dentistMap.get(
                                                    appointment
                                                            .getDentistId());
                        %>


                        <tr>


                            <!-- APPOINTMENT NUMBER -->
                            <td>

                                <div class="appointment-number-cell">

                                    <i class="bi bi-calendar2-check"
                                       aria-hidden="true"></i>

                                    <strong>
                                        <%= appointment
                                                .getAppointmentNumber() %>
                                    </strong>

                                </div>

                            </td>


                            <!-- PATIENT DETAILS -->
                            <td>

                                <% if (patient != null) { %>

                                <div class="appointment-person-details">

                                    <strong>
                                        <%= patient.getFullName() %>
                                    </strong>


                                    <span>

                                        <i class="bi bi-telephone"
                                           aria-hidden="true"></i>

                                        <%
                                            if (patient.getPhone() != null
                                                    && !patient.getPhone()
                                                    .isBlank()) {
                                        %>

                                        <%= patient.getPhone() %>

                                        <%
                                        } else {
                                        %>

                                        Not provided

                                        <%
                                            }
                                        %>

                                    </span>


                                    <span>

                                        <i class="bi bi-envelope"
                                           aria-hidden="true"></i>

                                        <%
                                            if (patient.getEmail() != null
                                                    && !patient.getEmail()
                                                    .isBlank()) {
                                        %>

                                        <%= patient.getEmail() %>

                                        <%
                                        } else {
                                        %>

                                        Not provided

                                        <%
                                            }
                                        %>

                                    </span>


                                    <span>

                                        <i class="bi bi-geo-alt"
                                           aria-hidden="true"></i>

                                        <%
                                            if (patient.getAddress() != null
                                                    && !patient.getAddress()
                                                    .isBlank()) {
                                        %>

                                        <%= patient.getAddress() %>

                                        <%
                                        } else {
                                        %>

                                        Not provided

                                        <%
                                            }
                                        %>

                                    </span>

                                </div>


                                <% } else { %>

                                <span>
                                    Patient information unavailable
                                </span>

                                <% } %>

                            </td>


                            <!-- DENTIST -->
                            <td>

                                <% if (dentist != null) { %>

                                <div class="appointment-person-details">

                                    <strong>
                                        Dr.
                                        <%= dentist.getFullName() %>
                                    </strong>


                                    <%
                                        if (dentist.getSpecialization() != null
                                                && !dentist
                                                .getSpecialization()
                                                .isBlank()) {
                                    %>

                                    <span>

                                        <i class="bi bi-person-badge"
                                           aria-hidden="true"></i>

                                        <%= dentist
                                                .getSpecialization() %>

                                    </span>

                                    <%
                                        }
                                    %>

                                </div>


                                <% } else { %>

                                <span>
                                    Dentist information unavailable
                                </span>

                                <% } %>

                            </td>


                            <!-- DATE + TIME -->
                            <td>

                                <div class="appointment-schedule-details">

                                    <span>

                                        <i class="bi bi-calendar3"
                                           aria-hidden="true"></i>

                                        <%= appointment
                                                .getAppointmentDate() %>

                                    </span>


                                    <span>

                                        <i class="bi bi-clock"
                                           aria-hidden="true"></i>

                                        <%= appointment
                                                .getAppointmentTime() %>

                                    </span>

                                </div>

                            </td>


                            <!-- STATUS -->
                            <td>

                                <%
                                    String statusValue =
                                            appointment.getStatus();

                                    String statusClass =
                                            "status-pill";

                                    if ("SCHEDULED".equalsIgnoreCase(
                                            statusValue)) {

                                        statusClass +=
                                                " status-scheduled";

                                    } else if ("COMPLETED".equalsIgnoreCase(
                                            statusValue)) {

                                        statusClass +=
                                                " status-completed";

                                    } else if ("CANCELLED".equalsIgnoreCase(
                                            statusValue)) {

                                        statusClass +=
                                                " status-cancelled";
                                    }
                                %>


                                <span class="<%= statusClass %>">

                                    <%= statusValue %>

                                </span>

                            </td>


                            <!-- NOTES -->
                            <td>

                                <%
                                    if (appointment.getNotes() != null
                                            && !appointment.getNotes()
                                            .isBlank()) {
                                %>

                                <div class="appointment-notes">

                                    <%= appointment.getNotes() %>

                                </div>

                                <%
                                } else {
                                %>

                                <span class="appointment-empty-value">
                                    -
                                </span>

                                <%
                                    }
                                %>

                            </td>


                            <!-- ACTION -->
                            <td>

                                <%
                                    if ("SCHEDULED".equalsIgnoreCase(
                                            appointment.getStatus())) {
                                %>


                                <form method="post"
                                      action="${pageContext.request.contextPath}/appointments/all"
                                      class="appointment-cancel-form">


                                    <input type="hidden"
                                           name="appointmentId"
                                           value="<%= appointment
                                                   .getAppointmentId() %>">


                                    <button class="btn btn-danger btn-small"
                                            type="submit">

                                        <i class="bi bi-x-circle"
                                           aria-hidden="true"></i>

                                        Cancel

                                    </button>

                                </form>


                                <%
                                } else {
                                %>

                                <span class="appointment-empty-value">
                                    -
                                </span>

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
                                class="appointment-empty-table">

                                <i class="bi bi-calendar-x"
                                   aria-hidden="true"></i>

                                <p>
                                    No appointments found.
                                </p>

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


<script>

    document.addEventListener(
        "DOMContentLoaded",
        function () {

            const cancelForms =
                document.querySelectorAll(
                    ".appointment-cancel-form"
                );


            cancelForms.forEach(
                function (form) {

                    form.addEventListener(
                        "submit",
                        function (event) {

                            const confirmed =
                                window.confirm(
                                    "Are you sure you want to cancel this appointment?"
                                );

                            if (!confirmed) {

                                event.preventDefault();
                            }
                        }
                    );
                }
            );
        }
    );

</script>


</body>

</html>