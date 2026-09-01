<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

  <title>Edit Patient</title>

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

  com.sunrisedental.model.Patient patient =
          (com.sunrisedental.model.Patient)
                  request.getAttribute(
                          "patient");
%>


<div class="dashboard-shell">

  <%@ include file="includes/sidebar.jsp" %>

  <main class="dashboard-main">

    <div class="page-container main-content">


      <!-- PAGE TITLE -->
      <div class="page-title">

        <h1>Edit Patient</h1>

        <p>
          Update the selected patient's personal information.
        </p>

      </div>


      <!-- ERROR -->
      <% if (request.getAttribute("errorMessage") != null) { %>

      <div class="alert alert-error">

        <i class="bi bi-exclamation-circle"></i>

        ${errorMessage}

      </div>

      <% } %>


      <% if (patient != null) { %>


      <div class="card">

        <div class="appointment-form-header">

          <div>

            <h2>
              Patient Information
            </h2>

            <p>
              Edit the patient's details and save the changes.
            </p>

          </div>

          <div class="appointment-form-icon">

            <i class="bi bi-person-gear"></i>

          </div>

        </div>


        <form method="post"
              action="${pageContext.request.contextPath}/patients/edit">


          <input type="hidden"
                 name="patientId"
                 value="<%= patient.getPatientId() %>">


          <!-- NAME -->
          <div class="grid grid-2">


            <div class="form-group">

              <label for="firstName">

                First Name

              </label>

              <input class="form-control"
                     type="text"
                     id="firstName"
                     name="firstName"
                     maxlength="50"
                     value="<%= patient.getFirstName() == null
                                           ? ""
                                           : patient.getFirstName() %>"
                     required>

            </div>


            <div class="form-group">

              <label for="lastName">

                Last Name

              </label>

              <input class="form-control"
                     type="text"
                     id="lastName"
                     name="lastName"
                     maxlength="50"
                     value="<%= patient.getLastName() == null
                                           ? ""
                                           : patient.getLastName() %>"
                     required>

            </div>

          </div>


          <!-- CONTACT + EMAIL -->
          <div class="grid grid-2">


            <div class="form-group">

              <label for="phone">

                Contact Number

              </label>

              <input class="form-control"
                     type="tel"
                     id="phone"
                     name="phone"
                     maxlength="20"
                     value="<%= patient.getPhone() == null
                                           ? ""
                                           : patient.getPhone() %>"
                     required>

            </div>


            <div class="form-group">

              <label for="email">

                Email

              </label>

              <input class="form-control"
                     type="email"
                     id="email"
                     name="email"
                     maxlength="100"
                     value="<%= patient.getEmail() == null
                                           ? ""
                                           : patient.getEmail() %>">

            </div>

          </div>


          <!-- DOB -->
          <div class="form-group">

            <label for="dateOfBirth">

              Date of Birth

            </label>

            <input class="form-control"
                   type="date"
                   id="dateOfBirth"
                   name="dateOfBirth"
                   value="<%= patient.getDateOfBirth() == null
                                       ? ""
                                       : patient.getDateOfBirth() %>">

          </div>


          <!-- ADDRESS -->
          <div class="form-group">

            <label for="address">

              Address

            </label>

            <textarea class="form-control"
                      id="address"
                      name="address"
                      rows="4"
                      maxlength="255"
                      required><%= patient.getAddress() == null
                    ? ""
                    : patient.getAddress() %></textarea>

          </div>


          <!-- ACTIONS -->
          <div class="appointment-form-actions">

            <button class="btn btn-primary"
                    type="submit">

              <i class="bi bi-check-circle"></i>

              Save Changes

            </button>


            <a class="btn btn-secondary"
               href="${pageContext.request.contextPath}/patients/manage">

              <i class="bi bi-arrow-left"></i>

              Back

            </a>

          </div>

        </form>

      </div>


      <% } %>

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

            const dateInput =
                    document.getElementById(
                            "dateOfBirth"
                    );

            if (dateInput) {

              const today =
                      new Date();

              const year =
                      today.getFullYear();

              const month =
                      String(
                              today.getMonth() + 1
                      ).padStart(
                              2,
                              "0"
                      );

              const day =
                      String(
                              today.getDate()
                      ).padStart(
                              2,
                              "0"
                      );

              dateInput.max =
                      year
                      + "-"
                      + month
                      + "-"
                      + day;
            }
          }
  );

</script>

</body>

</html>