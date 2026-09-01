<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

    <title>Add Patient</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>

<body class="dashboard-body">

<%
    request.setAttribute(
            "activePage",
            "patients");
%>

<div class="dashboard-shell">

    <%@ include file="includes/sidebar.jsp" %>

    <main class="dashboard-main">

        <div class="page-container main-content">


            <!-- PAGE TITLE -->
            <div class="page-title">

                <h1>Add Patient</h1>

                <p>
                    Register a new patient before creating an appointment.
                </p>

            </div>


            <!-- ERROR MESSAGE -->
            <% if (request.getAttribute("errorMessage") != null) { %>

            <div class="alert alert-error">

                <i class="bi bi-exclamation-circle"
                   aria-hidden="true"></i>

                ${errorMessage}

            </div>

            <% } %>


            <!-- PATIENT FORM -->
            <div class="card">

                <div class="appointment-form-header">

                    <div>

                        <h2>
                            Patient Information
                        </h2>

                        <p>
                            Enter the patient's personal and contact details.
                        </p>

                    </div>

                    <div class="appointment-form-icon">

                        <i class="bi bi-person-plus"
                           aria-hidden="true"></i>

                    </div>

                </div>


                <form method="post"
                      action="${pageContext.request.contextPath}/patients/new">


                    <!-- FIRST + LAST NAME -->
                    <div class="grid grid-2">

                        <div class="form-group">

                            <label for="firstName">

                                <i class="bi bi-person"
                                   aria-hidden="true"></i>

                                First Name

                            </label>

                            <input class="form-control"
                                   type="text"
                                   id="firstName"
                                   name="firstName"
                                   value="${firstNameValue}"
                                   maxlength="50"
                                   placeholder="Enter first name"
                                   required>

                        </div>


                        <div class="form-group">

                            <label for="lastName">

                                <i class="bi bi-person"
                                   aria-hidden="true"></i>

                                Last Name

                            </label>

                            <input class="form-control"
                                   type="text"
                                   id="lastName"
                                   name="lastName"
                                   value="${lastNameValue}"
                                   maxlength="50"
                                   placeholder="Enter last name"
                                   required>

                        </div>

                    </div>


                    <!-- PHONE + EMAIL -->
                    <div class="grid grid-2">

                        <div class="form-group">

                            <label for="phone">

                                <i class="bi bi-telephone"
                                   aria-hidden="true"></i>

                                Contact Number

                            </label>

                            <input class="form-control"
                                   type="tel"
                                   id="phone"
                                   name="phone"
                                   value="${phoneValue}"
                                   maxlength="20"
                                   placeholder="Example: 0771234567"
                                   required>

                        </div>


                        <div class="form-group">

                            <label for="email">

                                <i class="bi bi-envelope"
                                   aria-hidden="true"></i>

                                Email

                            </label>

                            <input class="form-control"
                                   type="email"
                                   id="email"
                                   name="email"
                                   value="${emailValue}"
                                   maxlength="100"
                                   placeholder="Example: patient@email.com">

                        </div>

                    </div>


                    <!-- DOB -->
                    <div class="form-group">

                        <label for="dateOfBirth">

                            <i class="bi bi-calendar3"
                               aria-hidden="true"></i>

                            Date of Birth

                        </label>

                        <input class="form-control"
                               type="date"
                               id="dateOfBirth"
                               name="dateOfBirth"
                               value="${dateOfBirthValue}">

                        <p class="form-help-text">
                            Date of birth is optional, but cannot be in the future.
                        </p>

                    </div>


                    <!-- ADDRESS -->
                    <div class="form-group">

                        <label for="address">

                            <i class="bi bi-geo-alt"
                               aria-hidden="true"></i>

                            Address

                        </label>

                        <textarea class="form-control"
                                  id="address"
                                  name="address"
                                  rows="4"
                                  maxlength="255"
                                  placeholder="Enter patient address"
                                  required>${addressValue}</textarea>

                    </div>


                    <!-- ACTIONS -->
                    <div class="appointment-form-actions">

                        <button class="btn btn-primary"
                                type="submit">

                            <i class="bi bi-person-check"
                               aria-hidden="true"></i>

                            Save Patient

                        </button>


                        <a class="btn btn-secondary"
                           href="${pageContext.request.contextPath}/appointments">

                            <i class="bi bi-arrow-left"
                               aria-hidden="true"></i>

                            Back to Appointment

                        </a>

                    </div>

                </form>

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

            const dateOfBirth =
                document.getElementById(
                    "dateOfBirth"
                );

            if (dateOfBirth) {

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

                dateOfBirth.max =
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