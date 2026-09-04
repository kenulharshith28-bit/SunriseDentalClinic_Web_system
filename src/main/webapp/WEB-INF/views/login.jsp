<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login | Sunrise Dental Clinic</title>

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/login.css">
</head>

<body>

<c:if test="${not empty successMessage}">
  <div class="success-toast" role="status" aria-live="polite">
    <span class="success-icon" aria-hidden="true"></span>
    <span><c:out value="${successMessage}" /></span>
  </div>
</c:if>

<div class="login-container">

  <div class="login-left">

    <div class="brand">
      <div class="logo-icon" aria-hidden="true"></div>
      <h2>Sunrise Dental</h2>
      <p>Clinic Management System</p>
    </div>

    <div class="login-content">

      <h1>Welcome Back</h1>
      <p class="subtitle">
        Sign in to access the clinic management system.
      </p>

      <c:if test="${not empty errorMessage}">
        <div class="error-message" role="alert">
          <c:out value="${errorMessage}" />
        </div>
      </c:if>

      <form action="${pageContext.request.contextPath}/login"
            method="post">

        <div class="input-group">
          <label for="username">Username</label>

          <div class="input-box">
            <span class="input-icon input-icon-user" aria-hidden="true"></span>
            <input
                    id="username"
                    type="text"
                    name="username"
                    placeholder="Enter your username"
                    autocomplete="username"
                    required>
          </div>
        </div>

        <div class="input-group">
          <label for="password">Password</label>

          <div class="input-box">
            <span class="input-icon input-icon-lock" aria-hidden="true"></span>
            <input
                    id="password"
                    type="password"
                    name="password"
                    placeholder="Enter your password"
                    autocomplete="current-password"
                    required>
          </div>
        </div>

        <button type="submit" class="login-btn">
          Login
        </button>

      </form>

    </div>

    <div class="footer">
      Sunrise Dental Clinic
    </div>

  </div>

  <div class="login-right">

    <img
            src="${pageContext.request.contextPath}/images/dental-login.jpg"
            alt="Sunrise Dental Clinic">

    <div class="image-overlay">
      <h2>Smiles Start Here</h2>
      <p>
        Simple and efficient dental appointment management.
      </p>
    </div>

  </div>

</div>

<c:if test="${not empty successMessage}">
  <script>
    window.setTimeout(function () {
      window.location.href = '<c:out value="${dashboardPath}" />';
    }, 1800);
  </script>
</c:if>

</body>
</html>
