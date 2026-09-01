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
      <div class="card no-print">

        <div class="appointment-form-header">

          <div>

            <h2>Generate Bill</h2>

            <p>
              Choose the appointment date and appointment number.
              The bill total is calculated automatically from
              the treatments selected during appointment creation.
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


          <!-- APPOINTMENT NUMBER -->
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
              Choose from the dropdown above or type
              the appointment number manually.
            </p>

          </div>


          <!-- HIDDEN DATABASE ID -->
          <input type="hidden"
                 id="appointmentId"
                 name="appointmentId"
                 value="${appointmentId}">


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


      <!-- PROFESSIONAL INVOICE -->
      <% if (request.getAttribute("calculatedTotal") != null) { %>

      <div class="card invoice-card">

        <div class="invoice-toolbar no-print">

          <button class="btn btn-secondary"
                  type="button"
                  onclick="window.print()">

            <i class="bi bi-printer"></i>

            Print Invoice

          </button>

        </div>


        <div class="invoice-sheet">


          <!-- HEADER -->
          <div class="invoice-header">

            <div class="invoice-brand">

              <div class="invoice-brand-row">

                <div class="invoice-logo">

                  <i class="bi bi-heart-pulse-fill"></i>

                </div>

                <div>

                  <h2>
                    Sunrise Dental Clinic
                  </h2>

                  <p>
                    Dental Care & Clinic Management
                  </p>

                </div>

              </div>

            </div>


            <div class="invoice-title-block">

              <h1>
                INVOICE
              </h1>

              <p>

                <strong>
                  Appointment No:
                </strong>

                ${generatedAppointmentNumber}

              </p>

              <p>

                <strong>
                  Appointment Date:
                </strong>

                ${generatedAppointmentDate}

              </p>

            </div>

          </div>


          <div class="invoice-divider"></div>


          <!-- GENERAL DETAILS -->
          <div class="invoice-details-grid invoice-details-grid-3">

            <div class="invoice-detail-box">

              <span class="invoice-label">
                Bill Type
              </span>

              <strong>
                Dental Treatment Invoice
              </strong>

            </div>


            <div class="invoice-detail-box">

              <span class="invoice-label">
                Clinic
              </span>

              <strong>
                Sunrise Dental Clinic
              </strong>

            </div>


            <div class="invoice-detail-box">

              <span class="invoice-label">
                Billing Source
              </span>

              <strong>
                Appointment Treatments
              </strong>

            </div>

          </div>


          <!-- TREATMENT TABLE -->
          <div class="table-wrapper invoice-table-wrapper">

            <table class="invoice-table">

              <thead>

              <tr>

                <th>
                  Description
                </th>

                <th>
                  Appointment
                </th>

                <th>
                  Date
                </th>

                <th>
                  Amount
                </th>

              </tr>

              </thead>


              <tbody>

              <%
                java.util.List<com.sunrisedental.model.Treatment>
                        billTreatments =
                        (java.util.List<com.sunrisedental.model.Treatment>)
                                request.getAttribute(
                                        "billTreatments");

                java.util.Map<Integer, com.sunrisedental.model.TreatmentType>
                        treatmentTypeMap =
                        (java.util.Map<Integer, com.sunrisedental.model.TreatmentType>)
                                request.getAttribute(
                                        "treatmentTypeMap");

                if (billTreatments != null
                        && !billTreatments.isEmpty()
                        && treatmentTypeMap != null) {

                  for (com.sunrisedental.model.Treatment treatment
                          : billTreatments) {

                    com.sunrisedental.model.TreatmentType treatmentType =
                            treatmentTypeMap.get(
                                    treatment
                                            .getTreatmentTypeId());

                    if (treatmentType != null) {
              %>


              <!-- TREATMENT ROW -->
              <tr>

                <td>

                  <strong>
                    <%= treatmentType.getTreatmentName() %>
                  </strong>

                </td>

                <td>
                  ${generatedAppointmentNumber}
                </td>

                <td>
                  ${generatedAppointmentDate}
                </td>

                <td>

                  <strong>
                    Rs.
                    <%= String.format(
                            "%,.2f",
                            treatmentType
                                    .getTreatmentFee()) %>
                  </strong>

                </td>

              </tr>


              <%
                  }
                }

              } else {
              %>


              <tr>

                <td colspan="4"
                    style="text-align:center;">

                  No treatment details available.

                </td>

              </tr>


              <%
                }
              %>


              <!-- CONSULTATION FEE -->
              <tr>

                <td>

                  <strong>
                    Consultation Fee
                  </strong>

                </td>

                <td>
                  ${generatedAppointmentNumber}
                </td>

                <td>
                  ${generatedAppointmentDate}
                </td>

                <td>

                  <strong>
                    Rs. ${consultationFee}
                  </strong>

                </td>

              </tr>


              </tbody>

            </table>

          </div>


          <!-- TOTAL -->
          <div class="invoice-summary">

            <div class="invoice-summary-box">

              <div class="summary-row">

                <span>
                  Subtotal
                </span>

                <strong>
                  Rs. ${calculatedTotal}
                </strong>

              </div>


              <div class="summary-row">

                <span>
                  Tax
                </span>

                <strong>
                  Rs. 0.00
                </strong>

              </div>


              <div class="summary-row total-row">

                <span>
                  Total Amount
                </span>

                <strong>
                  Rs. ${calculatedTotal}
                </strong>

              </div>

            </div>

          </div>


          <!-- FOOTER -->
          <div class="invoice-footer-section">

            <div>

              <strong>
                Payment Information
              </strong>

              <p>
                Please settle the payment at the clinic counter.
              </p>

            </div>


            <div>

              <strong>
                Thank You
              </strong>

              <p>
                Thank you for choosing Sunrise Dental Clinic.
              </p>

            </div>

          </div>


          <div class="invoice-footer-note">

            This invoice was generated from the treatment
            types selected for this appointment and includes
            the clinic consultation fee.

          </div>

        </div>

      </div>

      <% } %>


      <!-- SEARCH EXISTING BILL -->
      <div class="card no-print">

        <h2>
          Search Existing Bill
        </h2>

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


      <!-- SEARCHED BILL -->
      <% if (request.getAttribute("bill") != null) { %>

      <div class="card">

        <div class="appointment-form-header">

          <div>

            <h2>
              Bill Details
            </h2>

            <p>
              Stored billing record from the system.
            </p>

          </div>

          <div class="appointment-form-icon">

            <i class="bi bi-receipt"></i>

          </div>

        </div>


        <div class="table-wrapper">

          <table class="invoice-table">

            <thead>

            <tr>

              <th>
                Bill ID
              </th>

              <th>
                Appointment ID
              </th>

              <th>
                Total Amount
              </th>

            </tr>

            </thead>


            <tbody>

            <tr>

              <td>
                ${bill.billId}
              </td>

              <td>
                ${bill.appointmentId}
              </td>

              <td>

                <strong class="billing-total">
                  Rs. ${bill.totalAmount}
                </strong>

              </td>

            </tr>

            </tbody>

          </table>

        </div>

      </div>

      <% } %>

    </div>


    <div class="footer no-print">
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

            const form =
                    document.getElementById(
                            "billGenerationForm"
                    );


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
                                || option.dataset.date
                                === selectedDate) {

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


            function updateFromDropdown() {

              const option =
                      appointmentSelect
                              .options[
                              appointmentSelect.selectedIndex
                              ];

              if (!option
                      || !option.dataset.number) {

                appointmentIdInput.value =
                        "";

                return;
              }

              appointmentIdInput.value =
                      option.value;

              appointmentNumberInput.value =
                      option.dataset.number;

              dateInput.value =
                      option.dataset.date;
            }


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

              } else {

                appointmentIdInput.value =
                        "";
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