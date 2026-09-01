<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Change Password</title>

  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-body">

<%
  request.setAttribute(
          "activePage",
          "change-password");
%>

<div class="dashboard-shell">

<%@ include file="includes/sidebar.jsp" %>

<main class="dashboard-main">

<div class="page-container main-content">

  <div class="page-title">

    <h1>Change Password</h1>

    <p>
      Update your administrator account password.
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

    <h2>Password Details</h2>

    <form method="post"
          action="${pageContext.request.contextPath}/users/change-password">

      <div class="form-group">

        <label for="newPassword">
          New Password
        </label>

        <input class="form-control"
               type="password"
               id="newPassword"
               name="newPassword"
               placeholder="Enter new password"
               required>

      </div>

      <div class="form-group">

        <label for="confirmPassword">
          Confirm Password
        </label>

        <input class="form-control"
               type="password"
               id="confirmPassword"
               name="confirmPassword"
               placeholder="Re-enter new password"
               required>

      </div>

      <button class="btn btn-primary"
              type="submit">

        Change Password

      </button>

      <a class="btn btn-secondary"
         href="${pageContext.request.contextPath}/dashboard">

        Cancel

      </a>

    </form>

  </div>

</div>

<div class="footer">
  Sunrise Dental Clinic Management System
</div>

</main>

</div>

</body>
</html>
