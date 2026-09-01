<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Manage Treatment Types</title>

  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-body">

<%
  request.setAttribute(
          "activePage",
          "treatment-types");
%>

<div class="dashboard-shell">

<%@ include file="includes/sidebar.jsp" %>

<main class="dashboard-main">

<div class="page-container main-content">

  <div class="page-title">

    <h1>Manage Treatment Types</h1>

    <p>
      Add clinic procedures and configure their fees.
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

  <div class="admin-management-grid">

    <div class="card">

      <h2>Add Treatment Type</h2>

      <form method="post"
            action="${pageContext.request.contextPath}/treatment-types">

        <div class="grid grid-2 admin-form-grid">

          <div class="form-group">

            <label for="treatmentName">
              Treatment / Procedure Name
            </label>

            <input class="form-control"
                   type="text"
                   id="treatmentName"
                   name="treatmentName"
                   placeholder="Example: Root Canal"
                   required>

          </div>

          <div class="form-group">

            <label for="treatmentFee">
              Treatment Fee
            </label>

            <input class="form-control"
                   type="number"
                   id="treatmentFee"
                   name="treatmentFee"
                   min="0"
                   step="0.01"
                   placeholder="Example: 15000.00"
                   required>

          </div>

        </div>

        <button class="btn btn-primary"
                type="submit">

          Add Treatment Type

        </button>

      </form>

    </div>

  </div>


  <div class="card">

    <h2>Available Treatment Types</h2>

    <div class="table-wrapper">

      <table>

        <thead>

        <tr>
          <th>ID</th>
          <th>Treatment / Procedure</th>
          <th>Fee</th>
          <th>Action</th>
        </tr>

        </thead>

        <tbody>

        <%
          java.util.List<com.sunrisedental.model.TreatmentType>
                  treatmentTypes =
                  (java.util.List<com.sunrisedental.model.TreatmentType>)
                          request.getAttribute("treatmentTypes");

          if (treatmentTypes != null
                  && !treatmentTypes.isEmpty()) {

            for (com.sunrisedental.model.TreatmentType treatmentType
                    : treatmentTypes) {
        %>

        <tr>

          <td>
            <%= treatmentType.getTreatmentTypeId() %>
          </td>

          <td>
            <%= treatmentType.getTreatmentName() %>
          </td>

          <td>
            Rs. <%= treatmentType.getTreatmentFee() %>
          </td>

          <td>

            <form method="post"
                  action="${pageContext.request.contextPath}/treatment-types">

              <input type="hidden"
                     name="action"
                     value="delete">

              <input type="hidden"
                     name="treatmentTypeId"
                     value="<%= treatmentType.getTreatmentTypeId() %>">

              <button class="btn btn-danger"
                      type="submit"
                      onclick="return confirm('Are you sure you want to remove this treatment type?');">

                Remove

              </button>

            </form>

          </td>

        </tr>

        <%
          }

        } else {
        %>

        <tr>

          <td colspan="4">
            No treatment types have been added yet.
          </td>

        </tr>

        <%
          }
        %>

        </tbody>

      </table>

    </div>

  </div>

</div>

<div class="footer">
  Sunrise Dental Clinic Management System
</div>

</main>

</div>

</body>
</html>
