<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

  <title>Help & Staff Guide</title>

  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css">

</head>

<body class="dashboard-body">

<%
  request.setAttribute(
          "activePage",
          "help");
%>

<div class="dashboard-shell">

  <%@ include file="includes/sidebar.jsp" %>

  <main class="dashboard-main">

    <div class="page-container main-content">


      <!-- PAGE TITLE -->
      <div class="page-title">

        <h1>Help & Staff Guide</h1>

        <p>
          Step-by-step instructions for using the
          Sunrise Dental Clinic Management System.
        </p>

      </div>


      <!-- INTRODUCTION -->
      <div class="card">

        <div class="appointment-form-header">

          <div>

            <h2>Getting Started</h2>

            <p>
              This guide explains the main workflow
              used by clinic staff.
            </p>

          </div>

          <div class="appointment-form-icon">

            <i class="bi bi-question-circle"></i>

          </div>

        </div>

        <p>
          Staff members must sign in using an authorized
          username and password before accessing the system.
          Available options depend on the user's role.
        </p>

      </div>


      <!-- STEP 1 -->
      <div class="card help-step-card">

        <div class="help-step-header">

                    <span class="help-step-number">
                        1
                    </span>

          <div>

            <h2>Login to the System</h2>

            <p>
              Access the clinic system securely.
            </p>

          </div>

        </div>

        <ol class="help-instructions">

          <li>
            Open the Sunrise Dental Clinic login page.
          </li>

          <li>
            Enter your authorized username and password.
          </li>

          <li>
            Select Login.
          </li>

          <li>
            After successful authentication,
            the Dashboard will be displayed.
          </li>

        </ol>

      </div>


      <!-- STEP 2 -->
      <div class="card help-step-card">

        <div class="help-step-header">

                    <span class="help-step-number">
                        2
                    </span>

          <div>

            <h2>Register a New Patient</h2>

            <p>
              Add patient information before creating
              an appointment.
            </p>

          </div>

        </div>

        <ol class="help-instructions">

          <li>
            Select <strong>Add Patient</strong>
            from the sidebar.
          </li>

          <li>
            Enter the patient's first name,
            last name, contact number and address.
          </li>

          <li>
            Enter the email address and date of birth
            when available.
          </li>

          <li>
            Select <strong>Save Patient</strong>.
          </li>

          <li>
            After saving, return to Create Appointment.
          </li>

        </ol>

      </div>


      <!-- STEP 3 -->
      <div class="card help-step-card">

        <div class="help-step-header">

                    <span class="help-step-number">
                        3
                    </span>

          <div>

            <h2>Create an Appointment</h2>

            <p>
              Schedule a dental visit for a patient.
            </p>

          </div>

        </div>

        <ol class="help-instructions">

          <li>
            Select <strong>Create Appointment</strong>.
          </li>

          <li>
            Select the required patient.
          </li>

          <li>
            Verify the displayed patient contact,
            email and address information.
          </li>

          <li>
            Select the dentist.
          </li>

          <li>
            Choose the appointment date and time.
          </li>

          <li>
            Select one or more treatment types.
          </li>

          <li>
            Add appointment notes when required.
          </li>

          <li>
            Select <strong>Save Appointment</strong>.
          </li>

          <li>
            The system generates the daily appointment
            number automatically.
          </li>

          <li>
            If the patient has a valid email address,
            an appointment confirmation email is sent.
          </li>

        </ol>

      </div>


      <!-- STEP 4 -->
      <div class="card help-step-card">

        <div class="help-step-header">

                    <span class="help-step-number">
                        4
                    </span>

          <div>

            <h2>Generate a Patient Bill</h2>

            <p>
              Calculate and print the treatment invoice.
            </p>

          </div>

        </div>

        <ol class="help-instructions">

          <li>
            After appointment creation,
            the system redirects to Billing.
          </li>

          <li>
            Confirm the appointment date
            and appointment number.
          </li>

          <li>
            Select <strong>Generate Bill</strong>.
          </li>

          <li>
            The system calculates the selected treatment
            fees and consultation fee automatically.
          </li>

          <li>
            Review the treatment names,
            individual fees and total amount.
          </li>

          <li>
            Select <strong>Print Invoice</strong>
            when a printed copy is required.
          </li>

        </ol>

      </div>


      <!-- STEP 5 -->
      <div class="card help-step-card">

        <div class="help-step-header">

                    <span class="help-step-number">
                        5
                    </span>

          <div>

            <h2>Manage Appointments</h2>

            <p>
              View and manage existing appointment records.
            </p>

          </div>

        </div>

        <ol class="help-instructions">

          <li>
            Select <strong>Manage Appointments</strong>.
          </li>

          <li>
            Use the date and status filters
            to find appointments.
          </li>

          <li>
            Review the patient's contact details,
            dentist, date, time, status and notes.
          </li>

          <li>
            A scheduled appointment can be cancelled
            using the Cancel button.
          </li>

          <li>
            Expired scheduled appointments are
            automatically marked as cancelled.
          </li>

        </ol>

      </div>


      <!-- STEP 6 -->
      <div class="card help-step-card">

        <div class="help-step-header">

                    <span class="help-step-number">
                        6
                    </span>

          <div>

            <h2>View Reports</h2>

            <p>
              Review clinic information and generated reports.
            </p>

          </div>

        </div>

        <ol class="help-instructions">

          <li>
            Select <strong>Reports</strong>
            from the sidebar.
          </li>

          <li>
            Select the required report type.
          </li>

          <li>
            Review appointment or billing information.
          </li>

          <li>
            Print the report when required.
          </li>

        </ol>

      </div>


      <!-- ADMIN GUIDE -->
      <%
        String helpRole =
                (String) session.getAttribute(
                        "role");

        if ("ADMIN".equals(helpRole)) {
      %>

      <div class="card help-step-card">

        <div class="help-step-header">

                    <span class="help-step-number">
                        A
                    </span>

          <div>

            <h2>Administrator Functions</h2>

            <p>
              Additional options available to administrators.
            </p>

          </div>

        </div>

        <ul class="help-instructions">

          <li>
            <strong>Manage Patients</strong> —
            edit and remove patient records.
          </li>

          <li>
            <strong>Manage Dentists</strong> —
            add and manage dentist records.
          </li>

          <li>
            <strong>Treatment Types</strong> —
            manage treatment names and fees.
          </li>

          <li>
            <strong>Register Staff</strong> —
            create receptionist accounts.
          </li>

          <li>
            <strong>Manage Admins</strong> —
            create and manage administrator accounts.
          </li>

          <li>
            <strong>Change Password</strong> —
            update account credentials.
          </li>

        </ul>

      </div>

      <%
        }
      %>


      <!-- LOGOUT -->
      <div class="card help-step-card">

        <div class="help-step-header">

                    <span class="help-step-number">
                        7
                    </span>

          <div>

            <h2>Logout Safely</h2>

            <p>
              End the current system session.
            </p>

          </div>

        </div>

        <ol class="help-instructions">

          <li>
            Select <strong>Logout</strong>
            at the bottom of the sidebar.
          </li>

          <li>
            The current session will end
            and the Login page will be displayed.
          </li>

          <li>
            Always log out when leaving
            the clinic workstation unattended.
          </li>

        </ol>

      </div>


      <!-- QUICK WORKFLOW -->
      <div class="card">

        <div class="appointment-form-header">

          <div>

            <h2>Quick Daily Workflow</h2>

            <p>
              Recommended workflow for reception staff.
            </p>

          </div>

          <div class="appointment-form-icon">

            <i class="bi bi-list-check"></i>

          </div>

        </div>


        <div class="help-workflow">

          <span>Login</span>

          <i class="bi bi-arrow-right"></i>

          <span>Add / Select Patient</span>

          <i class="bi bi-arrow-right"></i>

          <span>Create Appointment</span>

          <i class="bi bi-arrow-right"></i>

          <span>Generate Bill</span>

          <i class="bi bi-arrow-right"></i>

          <span>Print Invoice</span>

          <i class="bi bi-arrow-right"></i>

          <span>Logout</span>

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