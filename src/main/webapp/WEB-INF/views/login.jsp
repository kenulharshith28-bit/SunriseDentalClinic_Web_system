<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Sunrise Dental Login</title>
</head>

<body>

<h2>Sunrise Dental Clinic</h2>

<h3>Login</h3>

<form method="post"
      action="${pageContext.request.contextPath}/login">

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
    Login
  </button>

</form>

<% if (request.getAttribute("errorMessage") != null) { %>

<p>
  ${errorMessage}
</p>

<% } %>

</body>
</html>