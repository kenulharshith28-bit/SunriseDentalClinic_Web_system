<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Sunrise Dental Dashboard</title>

  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css">

  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body class="dashboard-body">

<%
  request.setAttribute(
          "activePage",
          "dashboard");

  String role =
          (String) session.getAttribute("role");

  java.util.List<Integer> weeklyCounts =
          (java.util.List<Integer>)
                  request.getAttribute("weeklyAppointmentCounts");

  if (weeklyCounts == null
          || weeklyCounts.size() != 7) {

    weeklyCounts =
            java.util.Arrays.asList(
                    0, 0, 0, 0, 0, 0, 0);
  }
%>

<div class="dashboard-shell">

  <%@ include file="includes/sidebar.jsp" %>


  <!-- MAIN AREA -->
  <main class="dashboard-main">

    <!-- TOP BAR -->
    <header class="dashboard-topbar">

      <div>

        <h3>Dashboard</h3>

        <p>
          Clinic overview and management
        </p>

      </div>


      <div class="dashboard-user">

        <div class="user-avatar">
          ${sessionScope.username.substring(0,1).toUpperCase()}
        </div>

        <div>

          <strong>
            ${sessionScope.username}
          </strong>

          <span>
            ${sessionScope.role}
          </span>

        </div>

      </div>

    </header>


    <!-- WELCOME -->
    <section class="dashboard-welcome">

      <h1>
        Welcome back,
        <span>${sessionScope.username}</span>
      </h1>

      <p>
        Manage appointments, treatments, billing
        and clinic reports from one place.
      </p>

    </section>


    <!-- STATS -->
    <section class="stats-grid">

      <div class="stat-card">

        <div class="stat-icon">

          <i class="bi bi-people"
             aria-hidden="true"></i>

        </div>

        <div>

          <span>Patients</span>

          <strong>
            ${patientCount}
          </strong>

        </div>

      </div>


      <div class="stat-card">

        <div class="stat-icon">

          <i class="bi bi-calendar2-check"
             aria-hidden="true"></i>

        </div>

        <div>

          <span>Appointments</span>

          <strong>
            ${appointmentCount}
          </strong>

        </div>

      </div>


      <div class="stat-card">

        <div class="stat-icon">

          <i class="bi bi-clipboard2-pulse"
             aria-hidden="true"></i>

        </div>

        <div>

          <span>Treatments</span>

          <strong>
            ${treatmentCount}
          </strong>

        </div>

      </div>


      <div class="stat-card">

        <div class="stat-icon">

          <i class="bi bi-credit-card"
             aria-hidden="true"></i>

        </div>

        <div>

          <span>Billing</span>

          <strong>
            ${billCount}
          </strong>

        </div>

      </div>

    </section>


    <!-- WEEKLY APPOINTMENT CHART -->
    <section class="dashboard-panel chart-panel">

      <div class="panel-heading">

        <div>

          <h2>
            Appointments This Week
          </h2>

          <p>
            Appointment activity from Monday to Sunday
          </p>

        </div>

        <div class="chart-heading-icon">

          <i class="bi bi-bar-chart-line"
             aria-hidden="true"></i>

        </div>

      </div>


      <div class="dashboard-chart-wrapper">

        <canvas id="weeklyAppointmentsChart"></canvas>

      </div>

    </section>


    <!-- MAIN CONTENT -->
    <section class="dashboard-content-grid">

      <div class="dashboard-panel large-panel">

        <div class="panel-heading">

          <div>

            <h2>
              Clinic Management
            </h2>

            <p>
              Quick access to daily clinic operations
            </p>

          </div>

        </div>


        <div class="quick-action-grid">

          <a class="quick-action"
             href="${pageContext.request.contextPath}/appointments">

            <div class="quick-action-icon">

              <i class="bi bi-calendar2-check"
                 aria-hidden="true"></i>

            </div>

            <h3>
              Appointments
            </h3>

            <p>
              Search and create patient appointments.
            </p>

          </a>


          <a class="quick-action"
             href="${pageContext.request.contextPath}/patients/new">

            <div class="quick-action-icon">

              <i class="bi bi-person-plus"
                 aria-hidden="true"></i>

            </div>

            <h3>
              Add Patient
            </h3>

            <p>
              Register a new patient in the clinic.
            </p>

          </a>


          <a class="quick-action"
             href="${pageContext.request.contextPath}/bills">

            <div class="quick-action-icon">

              <i class="bi bi-receipt"
                 aria-hidden="true"></i>

            </div>

            <h3>
              Billing
            </h3>

            <p>
              Generate and search patient bills.
            </p>

          </a>

        </div>

      </div>


      <!-- QUICK LINKS -->
      <div class="dashboard-panel side-panel">

        <div class="panel-heading">

          <div>

            <h2>
              Quick Links
            </h2>

            <p>
              Common system actions
            </p>

          </div>

        </div>


        <a class="mini-action"
           href="${pageContext.request.contextPath}/reports">

                    <span class="mini-icon">

                        <i class="bi bi-file-earmark-bar-graph"
                           aria-hidden="true"></i>

                    </span>

          <div>

            <strong>
              Reports
            </strong>

            <small>
              View clinic reports
            </small>

          </div>

        </a>


        <a class="mini-action"
           href="${pageContext.request.contextPath}/appointments">

                    <span class="mini-icon">

                        <i class="bi bi-search"
                           aria-hidden="true"></i>

                    </span>

          <div>

            <strong>
              Search Appointment
            </strong>

            <small>
              Find an appointment
            </small>

          </div>

        </a>


        <a class="mini-action"
           href="${pageContext.request.contextPath}/bills">

                    <span class="mini-icon">

                        <i class="bi bi-receipt"
                           aria-hidden="true"></i>

                    </span>

          <div>

            <strong>
              Generate Bill
            </strong>

            <small>
              Create billing record
            </small>

          </div>

        </a>

      </div>

    </section>


    <!-- ADMIN SECTION -->
    <% if ("ADMIN".equals(role)) { %>

    <section class="dashboard-panel admin-panel">

      <div class="panel-heading">

        <div>

          <h2>
            Administration
          </h2>

          <p>
            Administrator-only system controls
          </p>

        </div>

      </div>


      <div class="admin-action-grid">

        <a class="admin-action-card"
           href="${pageContext.request.contextPath}/dentists">

          <div>

            <i class="bi bi-person-badge"
               aria-hidden="true"></i>

          </div>

          <h3>
            Manage Dentists
          </h3>

          <p>
            Add and manage clinic dentists.
          </p>

        </a>


        <a class="admin-action-card"
           href="${pageContext.request.contextPath}/treatment-types">

          <div>

            <i class="bi bi-clipboard2-pulse"
               aria-hidden="true"></i>

          </div>

          <h3>
            Treatment Types
          </h3>

          <p>
            Manage procedures and treatment fees.
          </p>

        </a>


        <a class="admin-action-card"
           href="${pageContext.request.contextPath}/users/register">

          <div>

            <i class="bi bi-people"
               aria-hidden="true"></i>

          </div>

          <h3>
            Staff Management
          </h3>

          <p>
            Register and manage receptionist accounts.
          </p>

        </a>


        <a class="admin-action-card"
           href="${pageContext.request.contextPath}/users/change-password">

          <div>

            <i class="bi bi-key"
               aria-hidden="true"></i>

          </div>

          <h3>
            Change Password
          </h3>

          <p>
            Update administrator credentials.
          </p>

        </a>

      </div>

    </section>

    <% } %>

  </main>

</div>


<script>

  const weeklyAppointmentData = [
    <%= weeklyCounts.get(0) %>,
    <%= weeklyCounts.get(1) %>,
    <%= weeklyCounts.get(2) %>,
    <%= weeklyCounts.get(3) %>,
    <%= weeklyCounts.get(4) %>,
    <%= weeklyCounts.get(5) %>,
    <%= weeklyCounts.get(6) %>
  ];


  const weeklyChartCanvas =
          document.getElementById(
                  "weeklyAppointmentsChart");


  if (weeklyChartCanvas) {

    new Chart(
            weeklyChartCanvas,
            {
              type: "bar",

              data: {

                labels: [
                  "Monday",
                  "Tuesday",
                  "Wednesday",
                  "Thursday",
                  "Friday",
                  "Saturday",
                  "Sunday"
                ],

                datasets: [
                  {
                    label: "Appointments",

                    data:
                    weeklyAppointmentData,

                    backgroundColor:
                            "rgba(15, 118, 110, 0.70)",

                    borderColor:
                            "rgba(15, 118, 110, 1)",

                    borderWidth: 1,

                    borderRadius: 8,

                    maxBarThickness: 65
                  }
                ]
              },

              options: {

                responsive: true,

                maintainAspectRatio: false,

                plugins: {

                  legend: {
                    display: false
                  },

                  tooltip: {

                    callbacks: {

                      label: function(context) {

                        return "Appointments: "
                                + context.parsed.y;
                      }
                    }
                  }
                },

                scales: {

                  y: {

                    beginAtZero: true,

                    ticks: {

                      precision: 0,

                      stepSize: 1
                    },

                    title: {

                      display: true,

                      text: "Appointments"
                    }
                  },

                  x: {

                    grid: {
                      display: false
                    }
                  }
                }
              }
            }
    );
  }

</script>

</body>
</html>
