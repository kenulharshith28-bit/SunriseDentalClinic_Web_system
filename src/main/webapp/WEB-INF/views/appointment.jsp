<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Appointment Management</title>

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

        <h1>Appointment Management</h1>

        <p>
            Search existing appointments or create a new booking.
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

        <h2>Search Appointment</h2>

        <form method="get"
              action="${pageContext.request.contextPath}/appointments">

            <div class="form-group">

                <label for="searchAppointmentNumber">
                    Appointment Number
                </label>

                <input class="form-control"
                       type="text"
                       id="searchAppointmentNumber"
                       name="appointmentNumber"
                       placeholder="Example: A-004"
                       required>

            </div>

            <button class="btn btn-primary"
                    type="submit">

                Search Appointment

            </button>

        </form>

    </div>

    <% if (request.getAttribute("appointment") != null) { %>

    <div class="card">

        <h2>Appointment Details</h2>

        <div class="table-wrapper">

            <table>

                <tr>
                    <th>Appointment Number</th>
                    <td>${appointment.appointmentNumber}</td>
                </tr>

                <tr>
                    <th>Patient ID</th>
                    <td>${appointment.patientId}</td>
                </tr>

                <tr>
                    <th>Dentist ID</th>
                    <td>${appointment.dentistId}</td>
                </tr>

                <tr>
                    <th>Date</th>
                    <td>${appointment.appointmentDate}</td>
                </tr>

                <tr>
                    <th>Time</th>
                    <td>${appointment.appointmentTime}</td>
                </tr>

                <tr>
                    <th>Status</th>
                    <td>${appointment.status}</td>
                </tr>

                <tr>
                    <th>Notes</th>
                    <td>${appointment.notes}</td>
                </tr>

            </table>

        </div>

    </div>

    <% } %>

    <div class="card">

        <h2>Create Appointment</h2>

        <form method="post"
              action="${pageContext.request.contextPath}/appointments">

            <div class="grid grid-2">

                <div class="form-group">

                    <label for="appointmentNumber">
                        Appointment Number
                    </label>

                    <input class="form-control"
                           type="text"
                           id="appointmentNumber"
                           name="appointmentNumber"
                           placeholder="Example: A-005"
                           required>

                </div>

                <div class="form-group">

                    <label for="status">
                        Status
                    </label>

                    <select class="form-control"
                            id="status"
                            name="status"
                            required>

                        <option value="SCHEDULED">
                            Scheduled
                        </option>

                        <option value="COMPLETED">
                            Completed
                        </option>

                        <option value="CANCELLED">
                            Cancelled
                        </option>

                    </select>

                </div>

            </div>

            <div class="grid grid-2">

                <div class="form-group">

                    <label for="patientId">
                        Patient
                    </label>

                    <select class="form-control"
                            id="patientId"
                            name="patientId"
                            required>

                        <option value="">
                            Select patient
                        </option>

                        <%
                            java.util.List<com.sunrisedental.model.Patient> patients =
                                    (java.util.List<com.sunrisedental.model.Patient>)
                                            request.getAttribute("patients");

                            if (patients != null) {
                                for (com.sunrisedental.model.Patient patient : patients) {
                        %>

                        <option value="<%= patient.getPatientId() %>">
                            <%= patient.getFullName() %>
                            - ID: <%= patient.getPatientId() %>
                            - <%= patient.getPhone() %>
                        </option>

                        <%
                                }
                            }
                        %>

                    </select>

                    <div style="margin-top: 8px;">

                        <a class="btn-link"
                           href="${pageContext.request.contextPath}/patients/new">

                            + Add New Patient

                        </a>

                    </div>

                </div>

                <div class="form-group">

                    <label for="dentistId">
                        Dentist
                    </label>

                    <select class="form-control"
                            id="dentistId"
                            name="dentistId"
                            required>

                        <option value="">
                            Select dentist
                        </option>

                        <%
                            java.util.List<com.sunrisedental.model.Dentist> dentists =
                                    (java.util.List<com.sunrisedental.model.Dentist>)
                                            request.getAttribute("dentists");

                            if (dentists != null) {
                                for (com.sunrisedental.model.Dentist dentist : dentists) {
                        %>

                        <option value="<%= dentist.getDentistId() %>">
                            <%= dentist.getFullName() %>
                        </option>

                        <%
                                }
                            }
                        %>

                    </select>

                </div>

            </div>

            <div class="grid grid-2">

                <div class="form-group">

                    <label for="appointmentDate">
                        Appointment Date
                    </label>

                    <input class="form-control"
                           type="date"
                           id="appointmentDate"
                           name="appointmentDate"
                           required>

                </div>

                <div class="form-group">

                    <label for="appointmentTime">
                        Appointment Time
                    </label>

                    <input class="form-control"
                           type="time"
                           id="appointmentTime"
                           name="appointmentTime"
                           required>

                </div>

            </div>

            <div class="form-group">

                <label for="notes">
                    Notes
                </label>

                <textarea class="form-control"
                          id="notes"
                          name="notes"
                          rows="4"
                          placeholder="Add any notes about this appointment"></textarea>

            </div>

            <button class="btn btn-primary"
                    type="submit">

                Save Appointment

            </button>

        </form>

    </div>

</div>

<div class="footer">
    Sunrise Dental Clinic Management System
</div>

</body>
</html>