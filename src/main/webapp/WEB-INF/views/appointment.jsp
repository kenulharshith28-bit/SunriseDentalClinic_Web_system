<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Create Appointment</title>

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


            <!-- PAGE HEADER -->
            <div class="page-title">

                <h1>Create Appointment</h1>

                <p>
                    Create a new booking and select the required treatments.
                </p>

            </div>


            <!-- ERROR MESSAGE -->
            <% if (request.getAttribute("errorMessage") != null) { %>

            <div class="alert alert-error">

                <i class="bi bi-exclamation-circle"
                   aria-hidden="true"></i>

                ${errorMessage}

            </div>

            <% } %>


            <!-- CREATE APPOINTMENT -->
            <div class="card">

                <div class="appointment-form-header">

                    <div>

                        <h2>Create Appointment</h2>

                        <p>
                            Enter the appointment details and optionally
                            select one or more treatments.
                        </p>

                    </div>

                    <div class="appointment-form-icon">

                        <i class="bi bi-calendar2-plus"
                           aria-hidden="true"></i>

                    </div>

                </div>


                <form method="post"
                      action="${pageContext.request.contextPath}/appointments">


                    <!-- PATIENT + DENTIST -->
                    <div class="grid grid-2">

                        <!-- PATIENT -->
                        <div class="form-group">

                            <label for="patientId">

                                <i class="bi bi-person"
                                   aria-hidden="true"></i>

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
                                    java.util.List<com.sunrisedental.model.Patient>
                                            patients =
                                            (java.util.List<com.sunrisedental.model.Patient>)
                                                    request.getAttribute(
                                                            "patients");

                                    if (patients != null) {

                                        for (com.sunrisedental.model.Patient patient
                                                : patients) {
                                %>

                                <option
                                        value="<%= patient.getPatientId() %>"
                                        data-name="<%= patient.getFullName() %>"
                                        data-phone="<%= patient.getPhone() == null
                                                ? ""
                                                : patient.getPhone() %>"
                                        data-email="<%= patient.getEmail() == null
                                                ? ""
                                                : patient.getEmail() %>"
                                        data-dob="<%= patient.getDateOfBirth() == null
                                                ? ""
                                                : patient.getDateOfBirth() %>"
                                        data-address="<%= patient.getAddress() == null
                                                ? ""
                                                : patient.getAddress() %>">

                                    <%= patient.getFullName() %>

                                    <%
                                        if (patient.getPhone() != null
                                                && !patient.getPhone().isBlank()) {
                                    %>

                                    -
                                    <%= patient.getPhone() %>

                                    <%
                                        }
                                    %>

                                </option>

                                <%
                                        }
                                    }
                                %>

                            </select>


                            <div class="appointment-add-patient">

                                <a class="btn-link"
                                   href="${pageContext.request.contextPath}/patients/new">

                                    <i class="bi bi-person-plus"
                                       aria-hidden="true"></i>

                                    Add New Patient

                                </a>

                            </div>

                        </div>


                        <!-- DENTIST -->
                        <div class="form-group">

                            <label for="dentistId">

                                <i class="bi bi-person-badge"
                                   aria-hidden="true"></i>

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
                                    java.util.List<com.sunrisedental.model.Dentist>
                                            dentists =
                                            (java.util.List<com.sunrisedental.model.Dentist>)
                                                    request.getAttribute(
                                                            "dentists");

                                    if (dentists != null) {

                                        for (com.sunrisedental.model.Dentist dentist
                                                : dentists) {
                                %>

                                <option value="<%= dentist.getDentistId() %>">

                                    Dr. <%= dentist.getFullName() %>

                                    <%
                                        if (dentist.getSpecialization() != null
                                                && !dentist.getSpecialization().isBlank()) {
                                    %>

                                    -
                                    <%= dentist.getSpecialization() %>

                                    <%
                                        }
                                    %>

                                </option>

                                <%
                                        }
                                    }
                                %>

                            </select>

                        </div>

                    </div>


                    <!-- SELECTED PATIENT DETAILS -->
                    <div id="selectedPatientCard"
                         class="selected-patient-card"
                         style="display:none;">

                        <div class="selected-patient-header">

                            <div>

                                <span class="form-help-text">
                                    Selected Patient
                                </span>

                                <h3 id="selectedPatientName">
                                    -
                                </h3>

                            </div>

                            <i class="bi bi-person-vcard"
                               aria-hidden="true"></i>

                        </div>


                        <div class="selected-patient-grid">

                            <div class="selected-patient-item">

                                <span>
                                    Contact Number
                                </span>

                                <strong id="selectedPatientPhone">
                                    -
                                </strong>

                            </div>


                            <div class="selected-patient-item">

                                <span>
                                    Email
                                </span>

                                <strong id="selectedPatientEmail">
                                    -
                                </strong>

                            </div>


                            <div class="selected-patient-item">

                                <span>
                                    Date of Birth
                                </span>

                                <strong id="selectedPatientDob">
                                    -
                                </strong>

                            </div>


                            <div class="selected-patient-item">

                                <span>
                                    Address
                                </span>

                                <strong id="selectedPatientAddress">
                                    -
                                </strong>

                            </div>

                        </div>

                    </div>


                    <!-- DATE + TIME -->
                    <div class="grid grid-2">

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
                                   required>

                        </div>


                        <div class="form-group">

                            <label for="appointmentTime">

                                <i class="bi bi-clock"
                                   aria-hidden="true"></i>

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

                            <i class="bi bi-check-circle"
                               aria-hidden="true"></i>

                            Status

                        </label>

                        <select class="form-control"
                                id="status"
                                name="status"
                                required>

                            <option value="SCHEDULED"
                                    selected>
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


                    <!-- TREATMENTS -->
                    <div class="form-group treatment-selection-section">

                        <div class="treatment-selection-heading">

                            <div>

                                <label>
                                    Treatments
                                </label>

                                <p class="form-help-text">
                                    Select one or more treatments if they are
                                    already known.
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
                                                request.getAttribute(
                                                        "treatmentTypes");

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


                        <!-- ESTIMATED TOTAL -->
                        <div class="treatment-total-bar">

                            <span class="treatment-total-label">
                                Estimated Treatment Total
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

                            <i class="bi bi-journal-text"
                               aria-hidden="true"></i>

                            Notes

                        </label>

                        <textarea class="form-control"
                                  id="notes"
                                  name="notes"
                                  rows="4"
                                  maxlength="255"
                                  placeholder="Add any notes about this appointment"></textarea>

                    </div>


                    <!-- SAVE -->
                    <div class="appointment-form-actions">

                        <button class="btn btn-primary"
                                type="submit">

                            <i class="bi bi-calendar2-check"
                               aria-hidden="true"></i>

                            Save Appointment

                        </button>

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

            /*
             * ---------------------------------
             * PATIENT INFORMATION
             * ---------------------------------
             */

            const patientSelect =
                document.getElementById(
                    "patientId"
                );

            const patientCard =
                document.getElementById(
                    "selectedPatientCard"
                );

            const patientName =
                document.getElementById(
                    "selectedPatientName"
                );

            const patientPhone =
                document.getElementById(
                    "selectedPatientPhone"
                );

            const patientEmail =
                document.getElementById(
                    "selectedPatientEmail"
                );

            const patientDob =
                document.getElementById(
                    "selectedPatientDob"
                );

            const patientAddress =
                document.getElementById(
                    "selectedPatientAddress"
                );


            function showSelectedPatient() {

                const option =
                    patientSelect.options[
                        patientSelect.selectedIndex
                        ];

                if (!option
                    || !option.value) {

                    patientCard.style.display =
                        "none";

                    return;
                }

                patientName.textContent =
                    option.dataset.name
                    || "Not provided";

                patientPhone.textContent =
                    option.dataset.phone
                    || "Not provided";

                patientEmail.textContent =
                    option.dataset.email
                    || "Not provided";

                patientDob.textContent =
                    option.dataset.dob
                    || "Not provided";

                patientAddress.textContent =
                    option.dataset.address
                    || "Not provided";

                patientCard.style.display =
                    "block";
            }


            patientSelect.addEventListener(
                "change",
                showSelectedPatient
            );


            showSelectedPatient();


            /*
             * ---------------------------------
             * TREATMENT TOTAL
             * ---------------------------------
             */

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