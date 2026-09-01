<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

  <title>Manage Patients</title>

  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css">

</head>

<body class="dashboard-body">

<%
  request.setAttribute(
          "activePage",
          "manage-patients");
%>

<div class="dashboard-shell">

  <%@ include file="includes/sidebar.jsp" %>

  <main class="dashboard-main">

    <div class="page-container main-content">


      <!-- PAGE TITLE -->
      <div class="page-title">

        <h1>Manage Patients</h1>

        <p>
          View, edit and manage registered patient records.
        </p>

      </div>


      <!-- SUCCESS -->
      <% if (request.getAttribute("successMessage") != null) { %>

      <div class="alert alert-success">

        <i class="bi bi-check-circle"></i>

        ${successMessage}

      </div>

      <% } %>


      <!-- ERROR -->
      <% if (request.getAttribute("errorMessage") != null) { %>

      <div class="alert alert-error">

        <i class="bi bi-exclamation-circle"></i>

        ${errorMessage}

      </div>

      <% } %>


      <!-- PATIENT TABLE -->
      <div class="card">

        <div class="appointment-form-header">

          <div>

            <h2>Registered Patients</h2>

            <p>
              Edit patient information or remove records
              that are not linked to appointments.
            </p>

          </div>

          <div class="appointment-form-icon">

            <i class="bi bi-people"></i>

          </div>

        </div>


        <div class="table-wrapper">

          <table class="patient-management-table">

            <thead>

            <tr>

              <th>Patient</th>
              <th>Contact</th>
              <th>Email</th>
              <th>Date of Birth</th>
              <th>Address</th>
              <th>Actions</th>

            </tr>

            </thead>


            <tbody>

            <%
              java.util.List<com.sunrisedental.model.Patient>
                      patients =
                      (java.util.List<com.sunrisedental.model.Patient>)
                              request.getAttribute(
                                      "patients");

              if (patients != null
                      && !patients.isEmpty()) {

                for (com.sunrisedental.model.Patient patient
                        : patients) {
            %>


            <tr>

              <td>

                <div class="patient-name-cell">

                  <i class="bi bi-person-circle"></i>

                  <div>

                    <strong>
                      <%= patient.getFullName() %>
                    </strong>

                    <span>
                                            ID:
                                            <%= patient.getPatientId() %>
                                        </span>

                  </div>

                </div>

              </td>


              <td>

                <%
                  if (patient.getPhone() != null
                          && !patient.getPhone().isBlank()) {
                %>

                <%= patient.getPhone() %>

                <%
                } else {
                %>

                -

                <%
                  }
                %>

              </td>


              <td>

                <%
                  if (patient.getEmail() != null
                          && !patient.getEmail().isBlank()) {
                %>

                <%= patient.getEmail() %>

                <%
                } else {
                %>

                -

                <%
                  }
                %>

              </td>


              <td>

                <%
                  if (patient.getDateOfBirth() != null) {
                %>

                <%= patient.getDateOfBirth() %>

                <%
                } else {
                %>

                -

                <%
                  }
                %>

              </td>


              <td class="patient-address-cell">

                <%
                  if (patient.getAddress() != null
                          && !patient.getAddress().isBlank()) {
                %>

                <%= patient.getAddress() %>

                <%
                } else {
                %>

                -

                <%
                  }
                %>

              </td>


              <td>

                <div class="patient-action-buttons">


                  <!-- EDIT -->
                  <a class="btn btn-secondary btn-small"
                     href="${pageContext.request.contextPath}/patients/edit?patientId=<%= patient.getPatientId() %>">

                    <i class="bi bi-pencil-square"></i>

                    Edit

                  </a>


                  <!-- DELETE -->
                  <form method="post"
                        action="${pageContext.request.contextPath}/patients/delete"
                        class="patient-delete-form">

                    <input type="hidden"
                           name="patientId"
                           value="<%= patient.getPatientId() %>">


                    <button class="btn btn-danger btn-small"
                            type="submit">

                      <i class="bi bi-trash"></i>

                      Delete

                    </button>

                  </form>

                </div>

              </td>

            </tr>


            <%
              }

            } else {
            %>


            <tr>

              <td colspan="6"
                  class="appointment-empty-table">

                <i class="bi bi-person-x"></i>

                <p>
                  No patients found.
                </p>

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


<script>

  document.addEventListener(
          "DOMContentLoaded",
          function () {

            const deleteForms =
                    document.querySelectorAll(
                            ".patient-delete-form"
                    );


            deleteForms.forEach(
                    function (form) {

                      form.addEventListener(
                              "submit",
                              function (event) {

                                const confirmed =
                                        window.confirm(
                                                "Are you sure you want to delete this patient?"
                                        );

                                if (!confirmed) {

                                  event.preventDefault();
                                }
                              }
                      );
                    }
            );
          }
  );

</script>

</body>

</html>