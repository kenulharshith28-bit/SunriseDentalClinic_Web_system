<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Billing</title>
</head>

<body>

<h2>Billing</h2>

<h3>Search Bill</h3>

<form method="get"
      action="${pageContext.request.contextPath}/bills">

  <p>
    <label>Bill ID:</label><br>

    <input type="number"
           name="billId"
           required>
  </p>

  <button type="submit">
    Search Bill
  </button>

</form>

<hr>

<h3>Create Bill</h3>

<form method="post"
      action="${pageContext.request.contextPath}/bills">

  <p>
    <label>Appointment ID:</label><br>

    <input type="number"
           name="appointmentId"
           required>
  </p>

  <p>
    The total amount will be calculated automatically
    from the treatments assigned to the appointment.
  </p>

  <button type="submit">
    Generate Bill
  </button>

</form>

<hr>

<% if (request.getAttribute("bill") != null) { %>

<h3>Bill Found</h3>

<p>
  Bill ID:
  ${bill.billId}
</p>

<p>
  Appointment ID:
  ${bill.appointmentId}
</p>

<p>
  Total Amount:
  ${bill.totalAmount}
</p>

<% } %>

<% if (request.getAttribute("calculatedTotal") != null) { %>

<h3>Generated Bill</h3>

<p>
  Total Amount:
  ${calculatedTotal}
</p>

<% } %>

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