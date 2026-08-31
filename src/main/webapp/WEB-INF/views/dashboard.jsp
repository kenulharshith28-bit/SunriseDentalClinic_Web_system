<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Sunrise Dental Dashboard</title>

  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="dashboard-body">

<div class="dashboard-shell">

  <!-- SIDEBAR -->
  <aside class="sidebar">

    <div class="sidebar-brand">

      <div class="brand-icon">
        🦷
      </div>

      <div>
        <h2>Sunrise Dental</h2>
        <span>Clinic Management</span>
      </div>

    </div>

    <nav class="sidebar-menu">

      <a class="sidebar-link active"
         href="${pageContext.request.contextPath}/dashboard">
        <span>▦</span>
        Dashboard
      </a>

      <a class="sidebar-link"
         href="${pageContext.request.contextPath}/appointments">
        <span>▤</span>
        Appointments
      </a>

      <a class="sidebar-link"
         href="${pageContext.request.contextPath}/patients/new">
        <span>♙</span>
        Add Patient
      </a>

      <a class="sidebar-link"
         href="${pageContext.request.contextPath}/treatments">
        <span>✚</span>
        Treatments
      </a>

      <a class="sidebar-link"
         href="${pageContext.request.contextPath}/bills">
        <span>▣</span>
        Billing
      </a>

      <a class="sidebar-link"
         href="${pageContext.request.contextPath}/reports">
        <span>◫</span>
        Reports
      </a>

      <%
        String role =
                (String) session.getAttribute("role");

        if ("ADMIN".equals(role)) {
      %>

      <div class="sidebar-section-title">
        Admin
      </div>

      <a class="sidebar-link"
         href="${pageContext.request.contextPath}/dentists">
        <span>🦷</span>
        Manage Dentists
      </a>

      <a class="sidebar-link"
         href="${pageContext.request.contextPath}/treatment-types">
        <span>🩺</span>
        Treatment Types
      </a>

      <a class="sidebar-link"
         href="${pageContext.request.contextPath}/users/register">
        <span>♚</span>
        Register Staff
      </a>

      <a class="sidebar-link"
         href="${pageContext.request.contextPath}/users/change-password">
        <span>⚙</span>
        Change Password
      </a>

      <%
        }
      %>

    </nav>

    <div class="sidebar-bottom">

      <a class="sidebar-link logout-link"
         href="${pageContext.request.contextPath}/logout">
        <span>↪</span>
        Logout
      </a>

    </div>

  </aside>


  <!-- MAIN AREA -->
  <main class="dashboard-main">

    <!-- TOP BAR -->
    <header class="dashboard-topbar">

      <div>
        <h3>Dashboard</h3>
        <p>Clinic overview and management</p>
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
          👥
        </div>

        <div>
          <span>Patients</span>
          <strong>Manage</strong>
        </div>

      </div>

      <div class="stat-card">

        <div class="stat-icon">
          📅
        </div>

        <div>
          <span>Appointments</span>
          <strong>Bookings</strong>
        </div>

      </div>

      <div class="stat-card">

        <div class="stat-icon">
          🦷
        </div>

        <div>
          <span>Treatments</span>
          <strong>Clinical</strong>
        </div>

      </div>

      <div class="stat-card">

        <div class="stat-icon">
          💳
        </div>

        <div>
          <span>Billing</span>
          <strong>Payments</strong>
        </div>

      </div>

    </section>


    <!-- MAIN CONTENT -->
    <section class="dashboard-content-grid">

      <div class="dashboard-panel large-panel">

        <div class="panel-heading">

          <div>
            <h2>Clinic Management</h2>

            <p>
              Quick access to daily clinic operations
            </p>
          </div>

        </div>

        <div class="quick-action-grid">

          <a class="quick-action"
             href="${pageContext.request.contextPath}/appointments">

            <div class="quick-action-icon">
              📅
            </div>

            <h3>Appointments</h3>

            <p>
              Search and create patient appointments.
            </p>

          </a>

          <a class="quick-action"
             href="${pageContext.request.contextPath}/patients/new">

            <div class="quick-action-icon">
              👤
            </div>

            <h3>Add Patient</h3>

            <p>
              Register a new patient in the clinic.
            </p>

          </a>

          <a class="quick-action"
             href="${pageContext.request.contextPath}/treatments">

            <div class="quick-action-icon">
              🦷
            </div>

            <h3>Treatments</h3>

            <p>
              Assign treatments to appointments.
            </p>

          </a>

          <a class="quick-action"
             href="${pageContext.request.contextPath}/bills">

            <div class="quick-action-icon">
              💰
            </div>

            <h3>Billing</h3>

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
            <h2>Quick Links</h2>

            <p>
              Common system actions
            </p>
          </div>

        </div>

        <a class="mini-action"
           href="${pageContext.request.contextPath}/reports">

                    <span class="mini-icon">
                        📊
                    </span>

          <div>
            <strong>Reports</strong>
            <small>View clinic reports</small>
          </div>

        </a>

        <a class="mini-action"
           href="${pageContext.request.contextPath}/appointments">

                    <span class="mini-icon">
                        🔍
                    </span>

          <div>
            <strong>Search Appointment</strong>
            <small>Find an appointment</small>
          </div>

        </a>

        <a class="mini-action"
           href="${pageContext.request.contextPath}/bills">

                    <span class="mini-icon">
                        🧾
                    </span>

          <div>
            <strong>Generate Bill</strong>
            <small>Create billing record</small>
          </div>

        </a>

      </div>

    </section>


    <!-- ADMIN SECTION -->
    <% if ("ADMIN".equals(role)) { %>

    <section class="dashboard-panel admin-panel">

      <div class="panel-heading">

        <div>

          <h2>Administration</h2>

          <p>
            Administrator-only system controls
          </p>

        </div>

      </div>

      <div class="admin-action-grid">

        <a class="admin-action-card"
           href="${pageContext.request.contextPath}/dentists">

          <div>
            🦷
          </div>

          <h3>Manage Dentists</h3>

          <p>
            Add and manage clinic dentists.
          </p>

        </a>

        <a class="admin-action-card"
           href="${pageContext.request.contextPath}/treatment-types">

          <div>
            🩺
          </div>

          <h3>Treatment Types</h3>

          <p>
            Manage procedures and treatment fees.
          </p>

        </a>

        <a class="admin-action-card"
           href="${pageContext.request.contextPath}/users/register">

          <div>
            👥
          </div>

          <h3>Staff Management</h3>

          <p>
            Register and manage receptionist accounts.
          </p>

        </a>

        <a class="admin-action-card"
           href="${pageContext.request.contextPath}/users/change-password">

          <div>
            🔐
          </div>

          <h3>Change Password</h3>

          <p>
            Update administrator credentials.
          </p>

        </a>

      </div>

    </section>

    <% } %>

  </main>

</div>

</body>
</html>