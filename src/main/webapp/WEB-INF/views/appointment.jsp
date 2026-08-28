<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Appointment Management</title>
</head>
<body>

<h2>Appointment Management</h2>

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

<% } %>

<% if (request.getAttribute("errorMessage") != null) { %>

<p>
    ${errorMessage}
</p>

<% } %>

<% if (request.getAttribute("successMessage") != null) { %>

<p>
    ${successMessage}
</p>

<% } %>

</body>
</html>