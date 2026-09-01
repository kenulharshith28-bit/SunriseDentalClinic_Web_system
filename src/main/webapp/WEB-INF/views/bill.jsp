<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Billing</title>

  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-body">

<%
  request.setAttribute(
          "activePage",
          "bills");
%>

<div class="dashboard-shell">

<%@ include file="includes/sidebar.jsp" %>

<main class="dashboard-main">

<div class="page-container main-content">

  <div class="page-title">

    <h1>Billing</h1>

    <p>
      Generate bills from treatments assigned to appointments.
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

  <div class="grid grid-2">

    <div class="card">

      <h2>Generate Bill</h2>

      <form method="post"
            action="${pageContext.request.contextPath}/bills">

        <div class="form-group">

          <label for="appointmentId">
            Appointment ID
          </label>

          <input class="form-control"
                 type="number"
                 id="appointmentId"
                 name="appointmentId"
                 value="${appointmentId}"
                 placeholder="Enter appointment ID"
                 min="1"
                 required>

        </div>

        <p>
          The total amount is calculated automatically
          using the treatments assigned to this appointment.
        </p>

        <button class="btn btn-primary"
                type="submit">

          Generate Bill

        </button>

      </form>

    </div>

    <div class="card">

      <h2>Search Bill</h2>

      <form method="get"
            action="${pageContext.request.contextPath}/bills">

        <div class="form-group">

          <label for="billId">
            Bill ID
          </label>

          <input class="form-control"
                 type="number"
                 id="billId"
                 name="billId"
                 placeholder="Enter bill ID"
                 required>

        </div>

        <button class="btn btn-primary"
                type="submit">

          Search Bill

        </button>

      </form>

    </div>

  </div>

  <% if (request.getAttribute("calculatedTotal") != null) { %>

  <div class="card">

    <h2>Generated Bill</h2>

    <table>

      <tr>
        <th>Total Amount</th>

        <td>
          Rs. ${calculatedTotal}
        </td>
      </tr>

    </table>

  </div>

  <% } %>

  <% if (request.getAttribute("bill") != null) { %>

  <div class="card">

    <h2>Bill Details</h2>

    <div class="table-wrapper">

      <table>

        <tr>
          <th>Bill ID</th>
          <td>${bill.billId}</td>
        </tr>

        <tr>
          <th>Appointment ID</th>
          <td>${bill.appointmentId}</td>
        </tr>

        <tr>
          <th>Total Amount</th>
          <td>Rs. ${bill.totalAmount}</td>
        </tr>

      </table>

    </div>

  </div>

  <% } %>

  <div class="card">

    <h3>Need to assign treatment first?</h3>

    <p>
      A bill can only be generated after at least one
      treatment has been assigned to the appointment.
    </p>

    <a class="btn btn-secondary"
       href="${pageContext.request.contextPath}/treatments">

      Assign Treatment

    </a>

  </div>

</div>

<div class="footer">
  Sunrise Dental Clinic Management System
</div>

</main>

</div>

</body>
</html>
