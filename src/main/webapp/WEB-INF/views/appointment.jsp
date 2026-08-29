<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Appointment Management</title>
</head>

<body>

<h2>Appointment Management</h2>

<h3>Search Appointment</h3>

<form method="get"
      action="${pageContext.request.contextPath}/appointments">

    <label>Appointment Number:</label>

    <input type="text"
           name="appointmentNumber"
           required>

    <button type="submit">
        Search
    </button>
</form>

<hr>

<h3>Create Appointment</h3>

<form method="post"
      action="${pageContext.request.contextPath}/appointments">

    <p>
        <label>Appointment Number:</label><br>

        <input type="text"
               name="appointmentNumber"
               placeholder="A-002"
               required>
    </p>

    <p>
        <label>Patient:</label><br>

        <select name="patientId" required>
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

        <a href="${pageContext.request.contextPath}/patients/new">
            + Add New Patient
        </a>
    </p>

    <p>
        <label>Dentist:</label><br>

        <select name="dentistId" required>
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
    </p>

    <p>
        <label>Appointment Date:</label><br>

        <input type="date"
               name="appointmentDate"
               required>
    </p>

    <p>
        <label>Appointment Time:</label><br>

        <input type="time"
               name="appointmentTime"
               required>
    </p>

    <p>
        <label>Status:</label><br>

        <select name="status" required>
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
    </p>

    <p>
        <label>Notes:</label><br>

        <textarea name="notes"
                  rows="4"
                  cols="40"></textarea>
    </p>

    <button type="submit">
        Save Appointment
    </button>

</form>

<hr>

<% if (request.getAttribute("appointment") != null) { %>

<h3>Appointment Found</h3>

<p>
    Appointment Number:
    ${appointment.appointmentNumber}
</p>

<p>
    Patient ID:
    ${appointment.patientId}
</p>

<p>
    Dentist ID:
    ${appointment.dentistId}
</p>

<p>
    Date:
    ${appointment.appointmentDate}
</p>

<p>
    Time:
    ${appointment.appointmentTime}
</p>

<p>
    Status:
    ${appointment.status}
</p>

<p>
    Notes:
    ${appointment.notes}
</p>

<% } %>

<% if (request.getAttribute("successMessage") != null) { %>

<p>
    ${successMessage}
</p>

<% } %>

<% if (request.getAttribute("errorMessage") != null) { %>

<p>
    ${errorMessage}
</p>

<% } %>

</body>
</html>