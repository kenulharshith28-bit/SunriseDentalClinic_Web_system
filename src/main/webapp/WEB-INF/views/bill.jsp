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


      <!-- PAGE TITLE -->
      <div class="page-title">

        <h1>Billing</h1>

        <p>
          Select an appointment and generate its treatment bill.
        </p>

      </div>


      <!-- INFO -->
      <% if (request.getAttribute("infoMessage") != null) { %>

      <div class="alert alert-success">

        <i class="bi bi-info-circle"></i>

        ${infoMessage}

      </div>

      <% } %>


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


      <!-- GENERATE BILL -->
      <div class="card">

        <div class="appointment-form-header">

          <div>

            <h2>Generate Bill</h2>

            <p>
              Choose the appointment date and appointment number.
              The total is calculated automatically from the
              treatments selected during appointment creation.
            </p>

          </div>

          <div class="appointment-form-icon">

            <i class="bi bi-receipt"></i>

          </div>

        </div>


        <form method="post"
              action="${pageContext.request.contextPath}/bills"
              id="billGenerationForm">


          <!-- DATE -->
          <div class="form-group">

            <label for="appointmentDate">

              <i class="bi bi-calendar3"></i>

              Appointment Date

            </label>

            <input class="form-control"
                   type="date"
                   id="appointmentDate"
                   name="appointmentDate"
                   value="${selectedAppointmentDate}"
                   required>

          </div>


          <!-- APPOINTMENT DROPDOWN -->
          <div class="form-group">

            <label for="appointmentSelect">

              <i class="bi bi-calendar-check"></i>

              Select Appointment

            </label>

            <select class="form-control"
                    id="appointmentSelect">

              <option value="">
                Select appointment number
              </option>

              <%
                java.util.List<com.sunrisedental.model.Appointment>
                        appointments =
                        (java.util.List<com.sunrisedental.model.Appointment>)
                                request.getAttribute("appointments");

                String selectedAppointmentNumber =
                        request.getAttribute(
                                "selectedAppointmentNumber") == null
                                ? ""
                                : request.getAttribute(
                                        "selectedAppointmentNumber")
                                .toString();

                String selectedAppointmentId =
                        request.getAttribute(
                                "appointmentId") == null
                                ? ""
                                : request.getAttribute(
                                        "appointmentId")
                                .toString();

                if (appointments != null) {

                  for (com.sunrisedental.model.Appointment appointment
                          : appointments) {

                    boolean selected =
                            selectedAppointmentId.equals(
                                    String.valueOf(
                                            appointment
                                                    .getAppointmentId()));
              %>

              <option
                      value="<%= appointment.getAppointmentId() %>"
                      data-number="<%= appointment.getAppointmentNumber() %>"
                      data-date="<%= appointment.getAppointmentDate() %>"
                      <%= selected ? "selected" : "" %>>

                <%= appointment.getAppointmentNumber() %>
                -
                <%= appointment.getAppointmentDate() %>
                -
                <%= appointment.getAppointmentTime() %>

              </option>

              <%
                  }
                }
              %>

            </select>

            <p class="form-help-text">
              Appointments are filtered automatically using
              the selected date.
            </p>

          </div>


          <!-- TYPE APPOINTMENT NUMBER -->
          <div class="form-group">

            <label for="appointmentNumber">

              <i class="bi bi-search"></i>

              Appointment Number

            </label>

            <input class="form-control"
                   type="text"
                   id="appointmentNumber"
                   name="appointmentNumber"
                   value="${selectedAppointmentNumber}"
                   placeholder="Example: A-001"
                   autocomplete="off"
                   required>

            <p class="form-help-text">
              You can choose from the dropdown above or type
              the appointment number manually.
            </p>

          </div>


          <!-- HIDDEN DATABASE ID -->
          <input type="hidden"
                 id="appointmentId"
                 name="appointmentId"
                 value="${appointmentId}">


          <!-- SELECTED APPOINTMENT SUMMARY -->
          <div class="billing-selection-summary"
               id="appointmentSummary">

            <div>

                            <span>
                                Selected Appointment
                            </span>

              <strong id="summaryAppointmentNumber">
                ${selectedAppointmentNumber}
              </strong>

            </div>

            <div>

                            <span>
                                Appointment Date
                            </span>

              <strong id="summaryAppointmentDate">
                ${selectedAppointmentDate}
              </strong>

            </div>

          </div>


          <!-- GENERATE -->
          <div class="appointment-form-actions">

            <button class="btn btn-primary"
                    type="submit">

              <i class="bi bi-receipt-cutoff"></i>

              Generate Bill

            </button>

          </div>

        </form>

      </div>


      <!-- GENERATED BILL -->
      <% if (request.getAttribute("calculatedTotal") != null) { %>

      <div class="card generated-bill-card">

        <div class="appointment-form-header">

          <div>

            <h2>Generated Bill</h2>

            <p>
              Bill generated from the treatments attached
              to this appointment.
            </p>

          </div>

          <div class="appointment-form-icon">

            <i class="bi bi-check-circle"></i>

          </div>

        </div>


        <div class="table-wrapper">

          <table>

            <tr>

              <th>
                Appointment Number
              </th>

              <td>
                ${generatedAppointmentNumber}
              </td>

            </tr>

            <tr>

              <th>
                Appointment Date
              </th>

              <td>
                ${generatedAppointmentDate}
              </td>

            </tr>

            <tr>

              <th>
                Total Amount
              </th>

              <td>

                <strong class="billing-total">
                  Rs. ${calculatedTotal}
                </strong>

              </td>

            </tr>

          </table>

        </div>

      </div>

      <% } %>


      <!-- SEARCH EXISTING BILL -->
      <div class="card">

        <h2>Search Existing Bill</h2>

        <p class="form-help-text">
          Search an already generated bill using its bill ID.
        </p>

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
                   min="1"
                   required>

          </div>

          <button class="btn btn-secondary"
                  type="submit">

            <i class="bi bi-search"></i>

            Search Bill

          </button>

        </form>

      </div>


      <!-- BILL SEARCH RESULT -->
      <% if (request.getAttribute("bill") != null) { %>

      <div class="card">

        <h2>Bill Details</h2>

        <div class="table-wrapper">

          <table>

            <tr>

              <th>
                Bill ID
              </th>

              <td>
                ${bill.billId}
              </td>

            </tr>

            <tr>

              <th>
                Appointment ID
              </th>

              <td>
                ${bill.appointmentId}
              </td>

            </tr>

            <tr>

              <th>
                Total Amount
              </th>

              <td>

                <strong class="billing-total">
                  Rs. ${bill.totalAmount}
                </strong>

              </td>

            </tr>

          </table>

        </div>

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
                            "appointmentDate"
                    );

            const appointmentSelect =
                    document.getElementById(
                            "appointmentSelect"
                    );

            const appointmentNumberInput =
                    document.getElementById(
                            "appointmentNumber"
                    );

            const appointmentIdInput =
                    document.getElementById(
                            "appointmentId"
                    );

            const summaryNumber =
                    document.getElementById(
                            "summaryAppointmentNumber"
                    );

            const summaryDate =
                    document.getElementById(
                            "summaryAppointmentDate"
                    );

            const form =
                    document.getElementById(
                            "billGenerationForm"
                    );


            /*
             * Filter dropdown according to selected date.
             */
            function filterAppointmentsByDate() {

              const selectedDate =
                      dateInput.value;

              const options =
                      appointmentSelect
                              .querySelectorAll(
                                      "option[data-date]"
                              );

              options.forEach(
                      function (option) {

                        if (!selectedDate
                                || option.dataset.date === selectedDate) {

                          option.hidden =
                                  false;

                        } else {

                          option.hidden =
                                  true;

                          if (option.selected) {

                            option.selected =
                                    false;
                          }
                        }
                      }
              );
            }


            /*
             * When dropdown changes, copy its number and ID.
             */
            function updateFromDropdown() {

              const option =
                      appointmentSelect
                              .options[
                              appointmentSelect.selectedIndex
                              ];

              if (!option
                      || !option.dataset.number) {

                return;
              }

              appointmentIdInput.value =
                      option.value;

              appointmentNumberInput.value =
                      option.dataset.number;

              dateInput.value =
                      option.dataset.date;

              summaryNumber.textContent =
                      option.dataset.number;

              summaryDate.textContent =
                      option.dataset.date;
            }


            /*
             * Match manually typed number with dropdown data.
             */
            function resolveTypedAppointment() {

              const number =
                      appointmentNumberInput
                              .value
                              .trim()
                              .toUpperCase();

              const date =
                      dateInput.value;

              let match =
                      null;

              const options =
                      appointmentSelect
                              .querySelectorAll(
                                      "option[data-number]"
                              );

              options.forEach(
                      function (option) {

                        if (option.dataset.number
                                        .toUpperCase()
                                === number
                                && option.dataset.date
                                === date) {

                          match =
                                  option;
                        }
                      }
              );

              if (match) {

                appointmentSelect.value =
                        match.value;

                appointmentIdInput.value =
                        match.value;

                summaryNumber.textContent =
                        match.dataset.number;

                summaryDate.textContent =
                        match.dataset.date;

              } else {

                appointmentIdInput.value =
                        "";

                summaryNumber.textContent =
                        number || "-";

                summaryDate.textContent =
                        date || "-";
              }
            }


            dateInput.addEventListener(
                    "change",
                    function () {

                      filterAppointmentsByDate();

                      appointmentSelect.value =
                              "";

                      appointmentIdInput.value =
                              "";

                      summaryDate.textContent =
                              dateInput.value || "-";

                      resolveTypedAppointment();
                    }
            );


            appointmentSelect.addEventListener(
                    "change",
                    updateFromDropdown
            );


            appointmentNumberInput.addEventListener(
                    "input",
                    resolveTypedAppointment
            );


            form.addEventListener(
                    "submit",
                    function () {

                      resolveTypedAppointment();
                    }
            );


            filterAppointmentsByDate();

            if (appointmentSelect.value) {

              updateFromDropdown();

            } else {

              resolveTypedAppointment();
            }
          }
  );

</script>

</body>

</html>