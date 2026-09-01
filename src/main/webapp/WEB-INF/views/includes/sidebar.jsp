<%
    final String activePage =
            (String) request.getAttribute("activePage");

    final String sidebarRole =
            (String) session.getAttribute("role");
%>

<aside class="sidebar">

    <!-- BRAND -->
    <div class="sidebar-brand">

        <div class="brand-icon">

            <i class="bi bi-heart-pulse-fill"
               aria-hidden="true"></i>

        </div>

        <div>

            <h2>
                Sunrise Dental
            </h2>

            <span>
                Clinic Management
            </span>

        </div>

    </div>


    <!-- MAIN NAVIGATION -->
    <nav class="sidebar-menu">


        <!-- DASHBOARD -->
        <a class="sidebar-link <%= "dashboard".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/dashboard">

            <span>
                <i class="bi bi-grid-1x2-fill"
                   aria-hidden="true"></i>
            </span>

            Dashboard

        </a>


        <!-- CREATE APPOINTMENT -->
        <a class="sidebar-link <%= "create-appointment".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/appointments">

            <span>
                <i class="bi bi-calendar-plus"
                   aria-hidden="true"></i>
            </span>

            Create Appointment

        </a>


        <!-- MANAGE APPOINTMENTS -->
        <a class="sidebar-link <%= "manage-appointments".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/appointments/all">

            <span>
                <i class="bi bi-calendar2-week"
                   aria-hidden="true"></i>
            </span>

            Manage Appointments

        </a>


        <!-- ADD PATIENT -->
        <a class="sidebar-link <%= "patients".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/patients/new">

            <span>
                <i class="bi bi-person-plus"
                   aria-hidden="true"></i>
            </span>

            Add Patient

        </a>


        <!-- BILLING -->
        <a class="sidebar-link <%= "bills".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/bills">

            <span>
                <i class="bi bi-receipt"
                   aria-hidden="true"></i>
            </span>

            Billing

        </a>


        <!-- REPORTS -->
        <a class="sidebar-link <%= "reports".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/reports">

            <span>
                <i class="bi bi-file-earmark-bar-graph"
                   aria-hidden="true"></i>
            </span>

            Reports

        </a>


        <!-- HELP -->
        <a class="sidebar-link <%= "help".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/help">

            <span>
                <i class="bi bi-question-circle"
                   aria-hidden="true"></i>
            </span>

            Help

        </a>


        <!-- ADMIN SECTION -->
        <% if ("ADMIN".equals(sidebarRole)) { %>

        <div class="sidebar-section-title">
            Admin
        </div>


        <!-- MANAGE PATIENTS -->
        <a class="sidebar-link <%= "manage-patients".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/patients/manage">

            <span>
                <i class="bi bi-person-vcard"
                   aria-hidden="true"></i>
            </span>

            Manage Patients

        </a>


        <!-- MANAGE DENTISTS -->
        <a class="sidebar-link <%= "dentists".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/dentists">

            <span>
                <i class="bi bi-person-badge"
                   aria-hidden="true"></i>
            </span>

            Manage Dentists

        </a>


        <!-- TREATMENT TYPES -->
        <a class="sidebar-link <%= "treatment-types".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/treatment-types">

            <span>
                <i class="bi bi-clipboard2-pulse"
                   aria-hidden="true"></i>
            </span>

            Treatment Types

        </a>


        <!-- REGISTER STAFF -->
        <a class="sidebar-link <%= "register-staff".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/users/register">

            <span>
                <i class="bi bi-people"
                   aria-hidden="true"></i>
            </span>

            Register Staff

        </a>


        <!-- MANAGE ADMINS -->
        <a class="sidebar-link <%= "admins".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/users/admins">

            <span>
                <i class="bi bi-shield-lock"
                   aria-hidden="true"></i>
            </span>

            Manage Admins

        </a>


        <!-- CHANGE PASSWORD -->
        <a class="sidebar-link <%= "change-password".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/users/change-password">

            <span>
                <i class="bi bi-key"
                   aria-hidden="true"></i>
            </span>

            Change Password

        </a>

        <% } %>

    </nav>


    <!-- LOGOUT -->
    <div class="sidebar-bottom">

        <a class="sidebar-link logout-link"
           href="${pageContext.request.contextPath}/logout">

            <span>
                <i class="bi bi-box-arrow-right"
                   aria-hidden="true"></i>
            </span>

            Logout

        </a>

    </div>

</aside>
