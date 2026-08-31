<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Sunrise Dental Dashboard</title>

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<h2>Sunrise Dental Clinic</h2>

<p>
  Welcome, ${sessionScope.username}
</p>

<p>
  Role: ${sessionScope.role}
</p>

<hr>

<h3>Menu</h3>

<p>
  <a href="${pageContext.request.contextPath}/appointments">
    Manage Appointments
  </a>
</p>

<p>
  <a href="${pageContext.request.contextPath}/treatments">
    Assign Treatment
  </a>
</p>

<p>
  <a href="${pageContext.request.contextPath}/bills">
    Billing
  </a>
</p>

<p>
  <a href="${pageContext.request.contextPath}/reports">
    Reports
  </a>
</p>

<%
  String role =
          (String) session.getAttribute("role");

  if ("ADMIN".equals(role)) {
%>

<hr>

<h3>Admin Options</h3>

<p>
  <a href="${pageContext.request.contextPath}/dentists">
    Manage Dentists
  </a>
</p>

<p>
  <a href="${pageContext.request.contextPath}/treatment-types">
    Manage Treatment Types
  </a>
</p>

<p>
  <a href="${pageContext.request.contextPath}/users/register">
    Register Staff
  </a>
</p>

<p>
  <a href="${pageContext.request.contextPath}/users/change-password">
    Change Password
  </a>
</p>

<%
  }
%>

<hr>

<p>
  <a href="${pageContext.request.contextPath}/logout">
    Logout
  </a>
</p>

</body>
</html>