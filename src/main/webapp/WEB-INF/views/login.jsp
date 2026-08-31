<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Sunrise Dental Login</title>

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<div class="login-page">

  <div class="login-card">

    <h1>Sunrise Dental Clinic</h1>

    <p class="subtitle">
      Sign in to continue
    </p>

    <% if (request.getAttribute("errorMessage") != null) { %>

    <div class="alert alert-error">
      ${errorMessage}
    </div>

    <% } %>

    <form method="post"
          action="${pageContext.request.contextPath}/login">

      <div class="form-group">

        <label for="username">
          Username
        </label>

        <input class="form-control"
               type="text"
               id="username"
               name="username"
               placeholder="Enter username"
               required>

      </div>

      <div class="form-group">

        <label for="password">
          Password
        </label>

        <input class="form-control"
               type="password"
               id="password"
               name="password"
               placeholder="Enter password"
               required>

      </div>

      <button class="btn btn-primary"
              type="submit"
              style="width: 100%;">

        Login

      </button>

    </form>

    <div class="footer">
      Sunrise Dental Clinic Management System
    </div>

  </div>

</div>

</body>
</html>