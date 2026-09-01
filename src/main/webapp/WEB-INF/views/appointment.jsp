<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Appointment Management</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-body">

<%
    request.setAttribute(
            "activePage",
            "create-appointment");
%>

<div class="dashboard-shell">

    <%@ include file="includes/sidebar.jsp" %>

    <main class="dashboard-main">

        <div class="page-container main-content">

            <div class="page-title">

                <h1>Appointment Management</h1>

                <p>
                    Search existing appointments or create a new booking.
                </p>

                <a class="btn btn-secondary"
                   href="${pageContext.request.contextPath}/appointments/all">

                    <i class="bi bi-list-ul"></i>
                    View All Appointments

                </a>

            </div>


            <!-- SUCCESS MESSAGE -->
            <% if (request.getAttribute("successMessage") != null) { %>

            <div class="alert alert-success">
                ${successMessage}
            </div>

            <% } %>


            <!-- NEXT STEP -->
            <% if (request.getAttribute("createdAppointment") != null) { %>

            <div class="card">

                <h2>Appointment Created</h2>

                <p>
                    The appointment has been created successfully.
                    You can continue to treatment management if another
                    treatment needs to be added later.
                </p>

                <a class="btn btn-primary"
                   href="${pageContext.request.contextPath}/treatments?appointmentId=${createdAppointment.appointmentId}">

                    <i class="bi bi-clipboard2-pulse"></i>
                    Manage Treatments

                </a>

            </div>

            <% } %>


            <!-- ERROR MESSAGE -->
            <% if (request.getAttribute("errorMessage") != null) { %>

            <div class="alert alert-error">
                ${errorMessage}
            </div>

            <% } %>


            <!-- SEARCH APPOINTMENT -->
            <div class="card">

                <h2>Search Appointment</h2>

                <form method="get"
                      action="${pageContext.request.contextPath}/appointments">

                    <div class="grid grid-2">

                        <div class="form-group">

                            <label for="searchAppointmentDate">
                                Appointment Date
                            </label>

                            <input class="form-control"
                                   type="date"
                                   id="searchAppointmentDate"
                                   name="appointmentDate"
                                   required>

                        </div>


                        <div class="form-group">

                            <label for="searchAppointmentNumber">
                                Appointment Number
                            </label>

                            <input class="form-control"
                                   type="text"
                                   id="searchAppointmentNumber"
                                   name="appointmentNumber"
                                   placeholder="Example: A-001"
                                   required>

                        </div>

                    </div>


                    <button class="btn btn-primary"
                            type="submit">

                        <i class="bi bi-search"></i>
                        Search Appointment

                    </button>

                </form>

            </div>


            <!-- SEARCH RESULT -->
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


            <!-- CREATE APPOINTMENT -->
            <div class="card">

                <div class="appointment-form-header">

                    <div>

                        <h2>Create Appointment</h2>

                        <p>
                            Enter appointment details and optionally
                            select one or more treatments.
                        </p>

                    </div>

                    <div class="appointment-form-icon">

                        <i class="bi bi-calendar2-plus"></i>

                    </div>

                </div>


                <form method="post"
                      action="${pageContext.request.contextPath}/appointments">


                    <!-- PATIENT + DENTIST -->
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

                                        for (com.sunrisedental.model.Patient patient
                                                : patients) {
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


                            <div class="appointment-add-patient">

                                <a class="btn-link"
                                   href="${pageContext.request.contextPath}/patients/new">

                                    <i class="bi bi-person-plus"></i>
                                    Add New Patient

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

                                        for (com.sunrisedental.model.Dentist dentist
                                                : dentists) {
                                %>

                                <option value="<%= dentist.getDentistId() %>">

                                    Dr. <%= dentist.getFullName() %>

                                </option>

                                <%
                                        }
                                    }
                                %>

                            </select>

                        </div>

                    </div>


                    <!-- DATE + TIME -->
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


                    <!-- STATUS -->
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


                    <!-- MULTIPLE TREATMENTS -->
                    <div class="form-group treatment-selection-section">

                        <div class="treatment-selection-heading">

                            <div>

                                <label>
                                    Treatments
                                </label>

                                <p class="form-help-text">
                                    Select one or more treatments if they are already known.
                                    You can also add treatments later.
                                </p>

                            </div>

                            <i class="bi bi-activity"
                               aria-hidden="true"></i>

                        </div>


                        <div class="treatment-selection-grid">

                            <%
                                java.util.List<com.sunrisedental.model.TreatmentType>
                                        treatmentTypes =
                                        (java.util.List<com.sunrisedental.model.TreatmentType>)
                                                request.getAttribute("treatmentTypes");

                                if (treatmentTypes != null
                                        && !treatmentTypes.isEmpty()) {

                                    for (com.sunrisedental.model.TreatmentType treatmentType
                                            : treatmentTypes) {
                            %>

                            <label class="treatment-option">

                                <input type="checkbox"
                                       name="treatmentTypeIds"
                                       class="treatment-checkbox"
                                       value="<%= treatmentType.getTreatmentTypeId() %>"
                                       data-fee="<%= treatmentType.getTreatmentFee() %>">

                                <div class="treatment-option-content">

                                    <strong>
                                        <%= treatmentType.getTreatmentName() %>
                                    </strong>

                                    <span>
                                        Rs.
                                        <%= String.format(
                                                "%,.2f",
                                                treatmentType.getTreatmentFee()) %>
                                    </span>

                                </div>

                            </label>

                            <%
                                    }

                                } else {
                            %>

                            <div class="treatment-empty-message">

                                <i class="bi bi-info-circle"
                                   aria-hidden="true"></i>

                                No treatment types are currently available.

                            </div>

                            <%
                                }
                            %>

                        </div>


                        <div class="treatment-total-bar">

                            <span class="treatment-total-label">
                                Estimated Treatment Total:
                            </span>

                            <span class="treatment-total-value"
                                  id="estimatedTreatmentTotal">
                                Rs. 0.00
                            </span>

                        </div>

                    </div>


                    <!-- NOTES -->
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


                    <!-- SAVE -->
                    <div class="appointment-form-actions">

                        <button class="btn btn-primary"
                                type="submit">

                            <i class="bi bi-calendar2-check"></i>
                            Save Appointment

                        </button>


                        <a class="btn btn-secondary"
                           href="${pageContext.request.contextPath}/appointments/all">

                            <i class="bi bi-list-ul"></i>
                            All Appointments

                        </a>

                    </div>

                </form>

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

            const checkboxes =
                document.querySelectorAll(
                    ".treatment-checkbox"
                );

            const totalElement =
                document.getElementById(
                    "estimatedTreatmentTotal"
                );

            function updateTreatmentTotal() {

                let total = 0;

                checkboxes.forEach(
                    function (checkbox) {

                        if (checkbox.checked) {

                            const fee =
                                parseFloat(
                                    checkbox.dataset.fee
                                );

                            if (!Number.isNaN(fee)) {
                                total += fee;
                            }
                        }
                    }
                );

                totalElement.textContent =
                    "Rs. "
                    + total.toLocaleString(
                        "en-US",
                        {
                            minimumFractionDigits: 2,
                            maximumFractionDigits: 2
                        }
                    );
            }

            checkboxes.forEach(
                function (checkbox) {

                    checkbox.addEventListener(
                        "change",
                        updateTreatmentTotal
                    );
                }
            );

            updateTreatmentTotal();
        }
    );
</script>

</body>
</html>
