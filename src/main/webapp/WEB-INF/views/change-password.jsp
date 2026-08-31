<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Change Password</title>

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

      <a href="${pageContext.request.contextPath}/appointments">
        Appointments
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

</body>
</html>