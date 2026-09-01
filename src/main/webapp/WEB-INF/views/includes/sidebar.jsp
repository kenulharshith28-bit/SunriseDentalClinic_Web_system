<%
    final String activePage =
            (String) request.getAttribute("activePage");

    final String sidebarRole =
            (String) session.getAttribute("role");
%>

<aside class="sidebar">

    <div class="sidebar-brand">

        <div class="brand-icon">
            <i class="bi bi-heart-pulse-fill"
               aria-hidden="true"></i>
        </div>

        <div>
            <h2>Sunrise Dental</h2>
            <span>Clinic Management</span>
        </div>

    </div>

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


        <!-- APPOINTMENT MANAGEMENT -->
        <a class="sidebar-link <%= "appointments".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/appointments">

            <span>
                <i class="bi bi-calendar2-check"
                   aria-hidden="true"></i>
            </span>

            Appointments

        </a>


        <!-- ALL APPOINTMENTS -->
        <a class="sidebar-link <%= "all-appointments".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/appointments/all">

            <span>
                <i class="bi bi-list-check"
                   aria-hidden="true"></i>
            </span>

            All Appointments

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


        <!-- TREATMENTS -->
        <a class="sidebar-link <%= "treatments".equals(activePage) ? "active" : "" %>"
           href="${pageContext.request.contextPath}/treatments">

            <span>
                <i class="bi bi-clipboard2-pulse"
                   aria-hidden="true"></i>
            </span>

            Treatments

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


        <!-- ADMIN ONLY -->
        <% if ("ADMIN".equals(sidebarRole)) { %>

        <div class="sidebar-section-title">
            Admin
        </div>


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