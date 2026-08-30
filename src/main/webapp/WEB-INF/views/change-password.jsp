<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Change Password</title>
</head>

<body>

<h2>Change Admin Password</h2>

<form method="post"
      action="${pageContext.request.contextPath}/users/change-password">

  <p>
    <label>New Password:</label><br>

    <input type="password"
           name="newPassword"
           required>
  </p>

  <p>
    <label>Confirm Password:</label><br>

    <input type="password"
           name="confirmPassword"
           required>
  </p>

  <button type="submit">
    Change Password
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