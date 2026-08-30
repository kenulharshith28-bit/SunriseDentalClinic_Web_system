<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Register Staff</title>
</head>

<body>

<h2>Register Staff User</h2>

<form method="post"
      action="${pageContext.request.contextPath}/users/register">

    <p>
        <label>Username:</label><br>

        <input type="text"
               name="username"
               required>
    </p>

    <p>
        <label>Password:</label><br>

        <input type="password"
               name="password"
               required>
    </p>

    <button type="submit">
        Register Staff
    </button>

</form>

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

<p>
    <a href="${pageContext.request.contextPath}/dashboard">
        Back to Dashboard
    </a>
</p>

</body>
</html>