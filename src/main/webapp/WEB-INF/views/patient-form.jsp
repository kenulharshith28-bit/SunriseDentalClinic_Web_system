<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add New Patient</title>
</head>

<body>

<h2>Add New Patient</h2>

<form method="post"
      action="${pageContext.request.contextPath}/patients/new">

    <p>
        <label>First Name:</label><br>

        <input type="text"
               name="firstName"
               required>
    </p>

    <p>
        <label>Last Name:</label><br>

        <input type="text"
               name="lastName"
               required>
    </p>

    <p>
        <label>Phone:</label><br>

        <input type="text"
               name="phone">
    </p>

    <button type="submit">
        Save Patient
    </button>

</form>

<% if (request.getAttribute("errorMessage") != null) { %>

<p>
    ${errorMessage}
</p>

<% } %>

<p>
    <a href="${pageContext.request.contextPath}/appointments">
        Back to Appointments
    </a>
</p>

</body>
</html>