<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Assign Treatment</title>

  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-body">

<%
  request.setAttribute(
          "activePage",
          "treatments");
%>

<div class="dashboard-shell">

<%@ include file="includes/sidebar.jsp" %>

<main class="dashboard-main">


<div class="page-container main-content">

  <div class="page-title">

    <h1>Assign Treatment</h1>

    <p>
      Select an appointment and assign the required
      treatment or procedure.
    </p>

  </div>


  <% if (request.getAttribute("successMessage") != null) { %>

  <div class="alert alert-success">
    ${successMessage}
  </div>

  <% } %>

  <% if (request.getAttribute("appointmentId") != null
          && request.getAttribute("successMessage") != null) { %>

  <div class="card">

    <h2>Next Step</h2>

    <p>
      Treatment assigned successfully.
      You can now generate the bill for this appointment.
    </p>

    <a class="btn btn-primary"
       href="${pageContext.request.contextPath}/bills?appointmentId=${appointmentId}">

      Generate Bill

    </a>

  </div>

  <% } %>


  <% if (request.getAttribute("errorMessage") != null) { %>

  <div class="alert alert-error">
    ${errorMessage}
  </div>

  <% } %>


  <!-- MAIN TREATMENT CARD -->
  <div class="card treatment-form-card">

    <div class="treatment-card-header">

      <div>

        <h2>Treatment Details</h2>

        <p>
          Enter the appointment and select the treatment performed.
        </p>

      </div>

      <div class="treatment-header-icon">
        <i class="bi bi-clipboard2-pulse" aria-hidden="true"></i>
      </div>

    </div>


    <form method="post"
          action="${pageContext.request.contextPath}/treatments">

      <div class="treatment-form-grid">

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


        <div class="form-group">

          <label for="treatmentTypeId">
            Treatment Type
          </label>

          <select class="form-control"
                  id="treatmentTypeId"
                  name="treatmentTypeId"
                  required>

            <option value="">
              Select treatment
            </option>

            <%
              java.util.List<com.sunrisedental.model.TreatmentType>
                      treatmentTypes =
                      (java.util.List<com.sunrisedental.model.TreatmentType>)
                              request.getAttribute("treatmentTypes");

              if (treatmentTypes != null) {

                for (com.sunrisedental.model.TreatmentType treatmentType
                        : treatmentTypes) {
            %>

            <option value="<%= treatmentType.getTreatmentTypeId() %>">

              <%= treatmentType.getTreatmentName() %>
              - Rs. <%= treatmentType.getTreatmentFee() %>

            </option>

            <%
                }
              }
            %>

          </select>

        </div>

      </div>


      <div class="form-group">

        <label for="description">
          Description
        </label>

        <textarea class="form-control"
                  id="description"
                  name="description"
                  rows="5"
                  placeholder="Enter treatment notes or additional details"></textarea>

      </div>


      <div class="treatment-form-actions">

        <button class="btn btn-primary"
                type="submit">

          Assign Treatment

        </button>

        <a class="btn btn-secondary"
           href="${pageContext.request.contextPath}/appointments">

          View Appointments

        </a>

      </div>

    </form>

  </div>


  <!-- QUICK ACTIONS -->
  <div class="treatment-quick-grid">

    <a class="treatment-quick-card"
       href="${pageContext.request.contextPath}/appointments">

      <div class="treatment-quick-icon">
        <i class="bi bi-calendar2-check" aria-hidden="true"></i>
      </div>

      <div>

        <h3>Appointments</h3>

        <p>
          Search or create appointments.
        </p>

      </div>

    </a>


    <a class="treatment-quick-card"
       href="${pageContext.request.contextPath}/bills">

      <div class="treatment-quick-icon">
        <i class="bi bi-credit-card" aria-hidden="true"></i>
      </div>

      <div>

        <h3>Billing</h3>

        <p>
          Generate a bill after treatment.
        </p>

      </div>

    </a>


    <a class="treatment-quick-card"
       href="${pageContext.request.contextPath}/dashboard">

      <div class="treatment-quick-icon">
        <i class="bi bi-grid-1x2-fill" aria-hidden="true"></i>
      </div>

      <div>

        <h3>Dashboard</h3>

        <p>
          Return to the clinic dashboard.
        </p>

      </div>

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
